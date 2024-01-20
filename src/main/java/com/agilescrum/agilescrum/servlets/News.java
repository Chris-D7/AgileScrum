package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.NewsDto;
import com.agilescrum.agilescrum.ejb.NewsBean;
import com.agilescrum.agilescrum.ejb.UserBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
        {"ADMIN", "RESEARCH"})})
@MultipartConfig
@WebServlet(name = "News", value = "/News")
public class News extends HttpServlet {

    @Inject
    NewsBean newsBean;

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NewsDto> newsList = newsBean.findAllNewsDesc();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        newsList.forEach(x -> {
            x.setDatePostedFormatted(x.getDatePosted().format(formatter));
        });
        request.setAttribute("newsList", newsList);
        request.setAttribute("activePage", "News");
        request.getRequestDispatcher("/WEB-INF/pages/news.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("newsTitle");
        String body = request.getParameter("newsBody");
        String email = request.getParameter("newsAuthor");
        //String author = request.getParameter("newsAuthor");
        LocalDateTime currentDateTime = LocalDateTime.now();

        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();
        long fileSize = filePart.getSize();
        byte[] fileContent = new byte[(int) fileSize];
        filePart.getInputStream().read(fileContent);

        com.agilescrum.agilescrum.entities.News createdNews = newsBean.createNewsReturn(title, body, email, currentDateTime);
        newsBean.addPhotoToNews(createdNews.getId(), fileName, fileType, fileContent);
        response.sendRedirect(request.getContextPath() + "/News");
    }
}
