package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.NewsPhotoDto;
import com.agilescrum.agilescrum.ejb.NewsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "NewsPhotos", value = "/NewsPhotos")
public class NewsPhotos extends HttpServlet {

    @Inject
    NewsBean newsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the newsid parameter from the request
        Long newsid = Long.parseLong(request.getParameter("newsid"));

        // Call the NewsBean to find the photo by news ID
        NewsPhotoDto photo = newsBean.findPhotoByNewsId(newsid);

        // Check if the photo exists
        if (photo != null) {
            // Set response content type, length, and write the photo content to the output stream
            response.setContentType(photo.getFileType());
            response.setContentLength(photo.getFileContent().length);
            response.getOutputStream().write(photo.getFileContent());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
