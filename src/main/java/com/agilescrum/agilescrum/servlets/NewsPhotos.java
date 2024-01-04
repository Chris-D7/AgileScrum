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
        Long newsid = Long.parseLong(request.getParameter("newsid"));
        NewsPhotoDto photo = newsBean.findPhotoByNewsId(newsid);
        if(photo != null){
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
