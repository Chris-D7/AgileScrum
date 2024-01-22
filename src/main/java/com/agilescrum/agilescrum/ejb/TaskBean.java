package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.TaskDto;
import com.agilescrum.agilescrum.entities.Sprint;
import com.agilescrum.agilescrum.entities.Task;
import com.agilescrum.agilescrum.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskBean {

    private static final Logger LOG = Logger.getLogger(TaskBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    // Creates a new task with the specified description, assigned user, and sprint
    @Transactional
    public void createTask(String description, Long sprintId, Long userId) {
        LOG.info("createTask");
        try {
            Sprint sprint = entityManager.find(Sprint.class, sprintId);
            User user = entityManager.find(User.class, userId);

            if (sprint != null && user != null) {
                Task task = new Task();
                task.setDescription(description);
                task.setAssignedUser(user);
                task.setSprint(sprint);
                task.setStatus(Boolean.FALSE);

                entityManager.persist(task);
                LOG.info("Task created: " + task.getId() + " for Sprint: " + sprint.getId());
                LOG.info("Task created successfully!");
            } else {
                LOG.warning("Sprint or User not found!");
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error creating task", ex);
        }
    }

    // Retrieves a list of TaskDto objects for a given sprint
    public List<TaskDto> findTasksBySprint(Long sprintId) {
        LOG.info("findTasksBySprint");
        try {
            // Retrieve tasks based on the sprintId
            List<Task> tasks = entityManager.createQuery(
                            "SELECT t FROM Task t WHERE t.sprint.id = :sprintId", Task.class)
                    .setParameter("sprintId", sprintId)
                    .getResultList();

            LOG.info("Tasks found for Sprint: " + sprintId);
            return copyTasksToDto(tasks);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error finding tasks by sprint", ex);
            throw new RuntimeException("Error finding tasks by sprint", ex);
        }
    }

    // Converts a list of Task entities to a list of TaskDto objects
    private List<TaskDto> copyTasksToDto(List<Task> tasks) {
        LOG.info("copyTasksToDTO");
        List<TaskDto> taskDtos = new ArrayList<>();
        try {
            tasks.forEach(x -> {
                taskDtos.add(new TaskDto(x.getId(), x.getDescription(), x.getStatus(), x.getSprint().getId(), x.getAssignedUser().getEmail(), x.getAssignedUser().getUsername()));
            });

            LOG.info("Tasks copied to DTO successfully");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error copying tasks to DTO", ex);
        }
        return taskDtos;
    }

    // Updates the status of a specific task (toggles between true and false)
    @Transactional
    public void updateTaskStatus(Long taskId) {
        LOG.info("updateTaskStatus");
        try {
            Task task = entityManager.find(Task.class, taskId);
            if (task != null) {
                if(task.getStatus()==Boolean.FALSE){
                    task.setStatus(Boolean.TRUE);
                } else {
                    task.setStatus(Boolean.FALSE);
                }
                entityManager.persist(task);
                LOG.info("Task status updated: " + taskId);
            } else {
                LOG.warning("Task not found!");
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error updating task status", ex);
        }
    }

}
