package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.SprintDto;
import com.agilescrum.agilescrum.common.TaskDto;
import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.entities.Sprint;
import com.agilescrum.agilescrum.entities.Task;
import com.agilescrum.agilescrum.entities.Teams;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class SprintBean {

    @Inject
    TaskBean taskBean;

    private static final Logger LOG = Logger.getLogger(SprintBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<SprintDto> findSprintsByTeam(Long team) {
        LOG.info("findSprintsByTeam");

        try {
            List<Sprint> sprints = entityManager.createQuery(
                            "SELECT s FROM Sprint s WHERE s.team.id = :team ORDER BY s.endDate DESC", Sprint.class)
                    .setParameter("team", team)
                    .getResultList();
            return copySprintToDto(sprints);
        } catch (Exception ex) {
            LOG.warning("Error finding sprints by team: " + ex.getMessage());
            return null;
        }
    }

    private List<SprintDto> copySprintToDto(List<Sprint> sprints){
        LOG.info("copySprintToDto");
        List<SprintDto> sprintDtos = new ArrayList<>();
        sprints.forEach(x -> {
            try {
                SprintDto sprintDto = new SprintDto(x.getId(), x.getEndDate(), x.getTeam().getId(), x.getNumber(), x.getReview(), new ArrayList<>());
                sprintDto.setTasks(taskBean.findTasksBySprint(sprintDto.getId()));
                sprintDto.getTasks().forEach(y -> {
                    if(y.getStatus()){
                        sprintDto.addDoneTasks();
                    }
                });
                sprintDto.setTotalTasks(sprintDto.getTasks().size());
                sprintDtos.add(sprintDto);
            } catch (NumberFormatException e) {
                LOG.warning("Error converting values: " + e.getMessage());
            }
        });
        return sprintDtos;
    }

    public void createSprint(Long teamId, LocalDateTime endDate) {
        LOG.info("createSprint");

        try {
            Teams team = entityManager.find(Teams.class, teamId);
            if (team != null) {
                // Count existing sprints for the team
                Long numberOfSprints = entityManager.createQuery(
                                "SELECT COUNT(s) FROM Sprint s WHERE s.team.id = :teamId", Long.class)
                        .setParameter("teamId", teamId)
                        .getSingleResult();

                Sprint sprint = new Sprint();

                sprint.setEndDate(endDate);

                // Increment the number if numberOfSprints is not null
                sprint.setNumber((numberOfSprints.intValue() + 1));

                sprint.setTeam(team);

                entityManager.persist(sprint);
            } else {
                LOG.warning("Team with id " + teamId + " not found.");
            }
        } catch (Exception ex) {
            LOG.warning("Error adding sprint: " + ex.getMessage());
        }
    }

    public SprintDto findCurrentSprint(List<SprintDto> sprints) {
        LOG.info("findCurrentSprint");
        LocalDateTime currentTime = LocalDateTime.now();

        for (SprintDto sprint : sprints) {
            if (sprint.getEndDate().isAfter(currentTime)) {
                return sprint;
            }
        }
        return null;
    }

    public void updateReview(Long sprintId, String updatedReview) {
        LOG.info("updateReview");
        Sprint sprint = entityManager.find(Sprint.class, sprintId);

        if (sprint != null) {
            sprint.setReview(updatedReview);
            entityManager.merge(sprint);
        } else {
            LOG.warning("Sprint with id " + sprintId + " not found.");
        }
    }
}
