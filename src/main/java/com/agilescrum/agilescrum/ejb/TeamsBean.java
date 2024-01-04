package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.entities.Teams;
import com.agilescrum.agilescrum.entities.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class TeamsBean {

    @Inject
    UserBean userBean;

    private static final Logger LOG = Logger.getLogger(TeamsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<TeamsDto> findTeamsForCurrentUser(String email) {
        /*User currentUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();*/
        List<Teams> teams = entityManager.createQuery(
                        "SELECT t FROM Teams t WHERE t.master.email = :email OR :email MEMBER OF t.members", Teams.class)
                .setParameter("email", email)
                .getResultList();
        return copyTeamsToDto(teams);
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

}
