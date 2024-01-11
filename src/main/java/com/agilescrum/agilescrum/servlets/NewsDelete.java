package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.NewsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "NewsDelete", value = "/NewsDelete")
public class NewsDelete extends HttpServlet {

    @Inject
    NewsBean newsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/News");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newsIdString = request.getParameter("newsId");

        if (newsIdString != null && !newsIdString.isEmpty()) {
            try {
                Long newsId = Long.parseLong(newsIdString);

                newsBean.deleteNews(newsId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/News");
    }
}
