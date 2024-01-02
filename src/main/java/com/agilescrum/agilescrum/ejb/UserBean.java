package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jdk.jpackage.internal.Log;

import java.util.logging.Logger;

@Stateless
public class UserBean {

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
}
