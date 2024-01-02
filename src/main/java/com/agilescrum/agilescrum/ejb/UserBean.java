package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.entities.User;
import com.agilescrum.agilescrum.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jdk.jpackage.internal.Log;

import java.util.Collection;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    @Inject
    PasswordBean passwordBean;

    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public UserDto findCurrentUser(Long id) {
        Log.info("findCurrentUser");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
            typedQuery.setParameter("id", id);
            User user = typedQuery.getSingleResult();
            return copyUserToDto(user);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private UserDto copyUserToDto(User user){
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail());
        return userDto;
    }

    public void createUser(String username, String email, String password) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
    }

    /*private void assignGroupsToUser(String email, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(email);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }*/
}
