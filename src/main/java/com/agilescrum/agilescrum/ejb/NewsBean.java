package com.agilescrum.agilescrum.ejb;

import com.agilescrum.agilescrum.common.NewsDto;
import com.agilescrum.agilescrum.common.NewsPhotoDto;
import com.agilescrum.agilescrum.entities.News;
import com.agilescrum.agilescrum.entities.NewsPhoto;
import com.agilescrum.agilescrum.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    // Retrieves all news articles in descending order of date posted
    public List<NewsDto> findAllNewsDesc() {
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

    // Converts a list of News entities to a list of NewsDto objects
    public List<NewsDto> copyNewsToDto(List<News> newsList) {
        List<NewsDto> newsDtos = new ArrayList<>();
        newsList.forEach(x -> {
            User author = x.getAuthor();
            NewsDto newsDto = new NewsDto(
                    x.getId(),
                    x.getTitle(),
                    x.getBody(),
                    author.getUsername(),
                    x.getEmail(),
                    x.getDatePosted(),
                    x.getImage()
            );
            newsDtos.add(newsDto);
        });
        return newsDtos;
    }

    // Creates a new news article in the database
    public void createNews(String title, String body, String email, LocalDateTime datePosted) {
        LOG.info("createNews");
        User author = getUserByEmail(email);
        try {
            News newNews = new News();
            newNews.setTitle(title);
            newNews.setBody(body);
            newNews.setAuthor(author);
            newNews.setEmail(email);
            newNews.setDatePosted(datePosted);
            entityManager.persist(newNews);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Creates a new news article and returns the entity
    public News createNewsReturn(String title, String body, String email, LocalDateTime datePosted) {
        LOG.info("createNewsReturn");
        News newNews = new News();
        try {
            User author = getUserByEmail(email);
            if (author != null) {
                newNews.setTitle(title);
                newNews.setBody(body);
                newNews.setAuthor(author);
                newNews.setEmail(email);
                newNews.setDatePosted(datePosted);
                author.getNews().add(newNews);
                entityManager.persist(author);
                entityManager.persist(newNews);
            } else {
                LOG.warning("User not found for email: " + email);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return newNews;
    }

    // Retrieves a user entity based on the provided email
    private User getUserByEmail(String email) {
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            typedQuery.setParameter("email", email);
            return typedQuery.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    // Adds a photo to a news article in the database
    public void addPhotoToNews(Long newsId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        NewsPhoto photo = new NewsPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);
        News news = entityManager.find(News.class, newsId);
        news.setImage(photo);
        photo.setNews(news);
        entityManager.persist(photo);
    }

    // Finds a photo associated with a news article and returns the DTO
    public NewsPhotoDto findPhotoByNewsId(Long newsId) {
        LOG.info("findPhotoByNewsId");
        List<NewsPhoto> photos = entityManager
                .createQuery("SELECT p FROM NewsPhoto p where p.news.id = :id", NewsPhoto.class)
                .setParameter("id", newsId)
                .getResultList();
        if (photos.isEmpty()) {
            return null;
        }
        NewsPhoto photo = photos.get(0);
        return new NewsPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(),
                photo.getFileContent());
    }

    // Finds a photo entity associated with a news article
    public NewsPhoto findPhotoEntityByNewsId(Long newsId) {
        LOG.info("findPhotoByNewsId");
        NewsPhoto photo = entityManager
                .createQuery("SELECT p FROM NewsPhoto p where p.news.id = :id", NewsPhoto.class)
                .setParameter("id", newsId)
                .getSingleResult();
        return photo;
    }

    // Deletes a news article and its associated photo
    public void deleteNews(Long newsId) {
        try {
            deleteNewsPhoto(newsId);
            News news = entityManager.find(News.class, newsId);
            if (news != null) {
                entityManager.remove(news);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    // Deletes the photo associated with a news article
    private void deleteNewsPhoto(Long newsId) {
        try {
            NewsPhoto newsPhoto = findPhotoEntityByNewsId(newsId);
            if (newsPhoto != null) {
                entityManager.remove(newsPhoto);
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
}
