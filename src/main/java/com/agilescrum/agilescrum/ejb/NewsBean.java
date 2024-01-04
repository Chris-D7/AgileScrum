package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.NewsDto;
import com.agilescrum.agilescrum.entities.News;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class NewsBean {

    private static final Logger LOG = Logger.getLogger(NewsBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<NewsDto> findAllNews() {
        LOG.info("findAllNews");
        try {
            TypedQuery<News> typedQuery = entityManager.createQuery(
                    "SELECT n FROM News n ORDER BY n.datePosted DESC", News.class);
            List<News> newsList = typedQuery.getResultList();
            return copyNewsToDto(newsList);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public List<NewsDto> copyNewsToDto(List<News> newsList) {
        List<NewsDto> newsDtos = new ArrayList<>();
        newsList.forEach(x -> {
            NewsDto newsDto = new NewsDto(
                    x.getId(),
                    x.getTitle(),
                    x.getBody(),
                    x.getAuthor(),
                    x.getDatePosted(),
                    x.getImage()
            );
            newsDtos.add(newsDto);
        });
        return newsDtos;
    }

    public void createNews(String title, String body, String author, LocalDateTime datePosted) {
        LOG.info("createNews");
        try {
            News newNews = new News();
            newNews.setTitle(title);
            newNews.setBody(body);
            newNews.setAuthor(author);
            newNews.setDatePosted(datePosted);
            entityManager.persist(newNews);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
