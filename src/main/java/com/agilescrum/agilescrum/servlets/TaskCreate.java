package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.TaskBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TaskCreate", value = "/TaskCreate")
public class TaskCreate extends HttpServlet {

    @Inject
    TaskBean taskBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve parameters from the form submission
            Long teamId = Long.parseLong(request.getParameter("teamId"));
            Long sprintId = Long.parseLong(request.getParameter("sprintId"));
            String description = request.getParameter("description");
            Long userId = Long.parseLong(request.getParameter("userId"));

            taskBean.createTask(description, sprintId, userId);

            response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log and display an error message)
            e.printStackTrace(); // Log the exception for debugging purposes
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating task.");
        }
    }
}
