package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.entities.Teams;
import com.agilescrum.agilescrum.entities.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class TeamsBean {

    private static final Logger LOG = Logger.getLogger(TeamsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<TeamsDto> findTeamsForCurrentUser(String email) {
        /*User currentUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();*/
        List<Teams> teams = entityManager.createQuery(
                        "SELECT DISTINCT t FROM Teams t LEFT JOIN t.members m WHERE t.master.email = :email OR m.email = :email", Teams.class)
                .setParameter("email", email)
                .getResultList();
        return copyTeamsToDto(teams);
    }

    public TeamsDto findTeamById(Long id) {
        Teams team = entityManager.find(Teams.class, id);
        User master = team.getMaster();
        UserDto masterDto = new UserDto(master.getId(), master.getUsername(), master.getEmail());
        Collection<User> users = team.getMembers();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(x -> {
            UserDto userDto = new UserDto(x.getId(), x.getUsername(), x.getEmail());
            userDtos.add(userDto);
        });
        return new TeamsDto(team.getId(), team.getSubject(), masterDto, userDtos);
    }

    private List<TeamsDto> copyTeamsToDto(List<Teams> teams) {
        List<TeamsDto> teamsDto = new ArrayList<>();
        teams.forEach(team -> {
            User master = team.getMaster();
            UserDto masterDto = new UserDto(
                    master.getId(),
                    master.getUsername(),
                    master.getEmail());

            List<UserDto> memberDtos = new ArrayList<>();
            team.getMembers().forEach(x -> {
                memberDtos.add(new UserDto(x.getId(), x.getUsername(), x.getEmail()));
            });
            teamsDto.add(new TeamsDto(team.getId(), team.getSubject(), masterDto, memberDtos));
        });
        return teamsDto;
    }

    public void createTeam(String subject, String masterEmail) {
        try {
            User master = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", masterEmail)
                    .getSingleResult();

            Teams newTeam = new Teams();
            newTeam.setSubject(subject);
            newTeam.setMaster(master);

            entityManager.persist(newTeam);
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTeam(Long teamId) {
        try {
            Teams teamToDelete = entityManager.find(Teams.class, teamId);
            teamToDelete.getMembers().forEach(x -> {
                x.getTeams().remove(teamToDelete);
            });

            if (teamToDelete != null) {
                entityManager.createQuery("DELETE FROM Task t WHERE t.sprint.team = :team")
                        .setParameter("team", teamToDelete)
                        .executeUpdate();

                entityManager.createQuery("DELETE FROM Sprint s WHERE s.team = :team")
                        .setParameter("team", teamToDelete)
                        .executeUpdate();
                entityManager.remove(teamToDelete);
            } else {
                LOG.warning("Team with ID " + teamId + " not found. Unable to delete.");
            }
        } catch (Exception ex) {
            LOG.severe("Error deleting team with ID " + teamId + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addMemberToTeam(Long teamId, String memberEmail) {
        try {
            Teams team = entityManager.find(Teams.class, teamId);
            User member = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", memberEmail)
                    .getSingleResult();

            if (team != null && member != null) {
                member.getTeams().add(team);
                team.getMembers().add(member);
                entityManager.merge(team);
                entityManager.merge(member);
            } else {
                LOG.warning("Team or member not found. Unable to add member to the team.");
            }
        } catch (Exception ex) {
            LOG.severe("Error adding member to team: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void deleteMemberFromTeam(Long teamId, Long memberId) {
        try {
            Teams team = entityManager.find(Teams.class, teamId);
            User member = entityManager.find(User.class, memberId);

            if (team != null && member != null) {
                entityManager.createQuery("DELETE FROM Task t WHERE t.assignedUser = :user AND t.sprint.team = :team")
                        .setParameter("user", member)
                        .setParameter("team", team)
                        .executeUpdate();

                team.getMembers().remove(member);
                member.getTeams().remove(team);
                entityManager.merge(team);
                entityManager.merge(member);
            } else {
                LOG.warning("Team or member not found. Unable to delete member from the team.");
            }
        } catch (Exception ex) {
            LOG.severe("Error deleting member from team: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
