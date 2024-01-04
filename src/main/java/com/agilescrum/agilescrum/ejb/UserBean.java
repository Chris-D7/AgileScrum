package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jdk.jfr.Frequency;
import jdk.jpackage.internal.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    @Inject
    PasswordBean passwordBean;

    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public UserDto findCurrentUser(Long id) {
        LOG.info("findCurrentUser");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
            typedQuery.setParameter("id", id);
            User user = typedQuery.getSingleResult();
            return copySingleUserToDto(user);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public String findUsernameByEmail(String email) {
        LOG.info("findUsernameByEmail");
        try {
            TypedQuery<String> typedQuery = entityManager.createQuery("SELECT u.username FROM User u WHERE u.email = :email", String.class);
            typedQuery.setParameter("email", email);
            return typedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public Long findIdByEmail(String email) {
        LOG.info("findIdByEmail");
        try {
            TypedQuery<String> typedQuery = entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :email", String.class);
            typedQuery.setParameter("email", email);
            return Long.parseLong(typedQuery.getSingleResult());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public List<UserDto> findAllUsers(){
        LOG.info("findAllUsers");
        try{
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch(Exception ex) {
            throw new EJBException(ex);
        }
    }

    private UserDto copySingleUserToDto(User user){
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail());
        return userDto;
    }

    private List<UserDto> copyUsersToDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(x -> {
            UserDto userDto = new UserDto(x.getId(), x.getUsername(), x.getEmail());
            userDtos.add(userDto);
        });
        return userDtos;
    }

    public void createUser(String username, String email, String password) {
        LOG.info("createUser");
        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(passwordBean.convertToSha256(password));
            entityManager.persist(newUser);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
