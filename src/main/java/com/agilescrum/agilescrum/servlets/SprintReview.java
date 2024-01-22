package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.SprintBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SprintReview", value = "/SprintReview")
public class SprintReview extends HttpServlet {

    @Inject
    SprintBean sprintBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirects to the Teams page
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve sprint ID, updated review, and team ID from request parameters
        Long sprintId = Long.parseLong(request.getParameter("sprintId"));
        String updatedReview = request.getParameter("review");
        Long teamId = Long.parseLong(request.getParameter("id"));

        // Update the sprint review using SprintBean
        sprintBean.updateReview(sprintId, updatedReview);

        // Redirect back to the SprintPage with the updated sprint information
        response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
    }
}
