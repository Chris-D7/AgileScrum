package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.entities.UserGroup;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.logging.Logger;

@Stateless
public class UsergroupsBean {

    private static final Logger LOG = Logger.getLogger(UsergroupsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public String findUsergroupRole(String email){
        LOG.info("findUsergroupRole");
        try {
            return (String) entityManager.createQuery(
                            "SELECT u.userGroup FROM UserGroup u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOG.warning("No user found with email: " + email);
            return null;
        } catch (Exception e) {
            LOG.severe("Error retrieving user group: " + e.getMessage());
            return null;
        }
    }

    public Long findUsergroupId(String email){
        LOG.info("findUsergroupId");
        try {
            Long usergroupId = (Long) entityManager.createQuery(
                            "SELECT u.id FROM UserGroup u WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
            return usergroupId;
        } catch (Exception e) {
            LOG.warning("Error finding user group ID: " + e.getMessage());
            return null;
        }
    }

    public void updateUsergroup(String email, String role){
        LOG.info("updateUsergroup");
        UserGroup userGroup = entityManager.find(UserGroup.class, findUsergroupId(email));
        if (userGroup != null) {
            userGroup.setUserGroup(role);
            entityManager.merge(userGroup);
            entityManager.flush();

            LOG.info("Usergroup updated successfully");
        } else {
            LOG.warning("No UserGroup found for email: " + email);
        }
    }

    public void createUsergroup(String email){
        LOG.info("createUsergroup");
        try {
            UserGroup userGroup = new UserGroup();
            userGroup.setEmail(email);
            userGroup.setUserGroup("COMMON");
            entityManager.persist(userGroup);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
