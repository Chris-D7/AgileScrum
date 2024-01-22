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
        // Redirect to the main News page
        response.sendRedirect(request.getContextPath() + "/News");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the newsId parameter from the request
        String newsIdString = request.getParameter("newsId");

        // Check if the newsId is provided and not empty
        if (newsIdString != null && !newsIdString.isEmpty()) {
            try {
                // Parse the newsId to a Long
                Long newsId = Long.parseLong(newsIdString);

                // Call the NewsBean to delete the news item with the specified ID
                newsBean.deleteNews(newsId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Redirect to the main News page after deleting the news item
        response.sendRedirect(request.getContextPath() + "/News");
    }
}
