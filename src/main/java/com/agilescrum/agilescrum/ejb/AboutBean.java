package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.entities.About;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.logging.Logger;

@Stateless
public class AboutBean {
    private static final Logger LOG = Logger.getLogger(AboutBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public String getAboutText() {
        LOG.info("getAboutText");
        try {
            TypedQuery<String> query = entityManager.createQuery("SELECT a.aboutText FROM About a", String.class);
            return query.getSingleResult();
        } catch (Exception ex) {
            LOG.severe("Error retrieving about text: " + ex.getMessage());
            return null;
        }
    }

    public void updateAboutText(String newAboutText) {
        LOG.info("updateAboutText");
        try {
            About aboutEntity = entityManager.find(About.class, Long.valueOf(-1));

            if (aboutEntity != null) {
                aboutEntity.setAboutText(newAboutText);
                entityManager.merge(aboutEntity);
                entityManager.flush();
                LOG.info("About text updated successfully");
            } else {
                LOG.warning("No AboutEntity found with Id: " + -1);
            }
        } catch (Exception ex) {
            LOG.severe("Error updating about text: " + ex.getMessage());
        }
    }
}
