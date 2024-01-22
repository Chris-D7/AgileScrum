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

// User roles allowed to access this servlet
@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
// Security constraints for HTTP methods and user roles
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
        {"ADMIN", "RESEARCH"})})
// Enable handling of multipart/form-data requests for file uploads
@MultipartConfig
@WebServlet(name = "News", value = "/News")
public class News extends HttpServlet {

    @Inject
    NewsBean newsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve a list of news items in descending order
        List<NewsDto> newsList = newsBean.findAllNewsDesc();
        // Format the datePosted values for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        newsList.forEach(x -> {
            x.setDatePostedFormatted(x.getDatePosted().format(formatter));
        });
        // Set newsList and activePage attributes for display in the JSP page
        request.setAttribute("newsList", newsList);
        request.setAttribute("activePage", "News");
        // Forward the request to the news.jsp page for rendering
        request.getRequestDispatcher("/WEB-INF/pages/news.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve information from the request parameters
        String title = request.getParameter("newsTitle");
        String body = request.getParameter("newsBody");
        String email = request.getParameter("newsAuthor");
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Retrieve the file part from the multipart request
        Part filePart = request.getPart("file");
        // Extract information about the uploaded file
        String fileName = filePart.getSubmittedFileName();
        String fileType = filePart.getContentType();
        long fileSize = filePart.getSize();
        byte[] fileContent = new byte[(int) fileSize];
        filePart.getInputStream().read(fileContent);

        // Create a new news item and associate it with the uploaded file
        com.agilescrum.agilescrum.entities.News createdNews = newsBean.createNewsReturn(title, body, email, currentDateTime);
        newsBean.addPhotoToNews(createdNews.getId(), fileName, fileType, fileContent);

        // Redirect the user to the news page after successfully creating news
        response.sendRedirect(request.getContextPath() + "/News");
    }
}
