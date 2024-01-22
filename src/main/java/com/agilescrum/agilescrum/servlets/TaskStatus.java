package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.TaskBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TaskStatus", value = "/TaskStatus")
public class TaskStatus extends HttpServlet {

    @Inject
    TaskBean taskBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirects to the Teams page
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form submission
        String taskIdParam = request.getParameter("taskId");
        Long teamId = Long.parseLong(request.getParameter("teamId"));

        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            try {
                // Parse task ID from the parameter
                Long taskId = Long.parseLong(taskIdParam);

                // Call TaskBean to update the task status
                taskBean.updateTaskStatus(taskId);

                // Redirect back to the SprintPage with the updated task information
                response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
            } catch (NumberFormatException e) {
                // Redirect to Teams page if task ID is not valid
                response.sendRedirect(request.getContextPath() + "/Teams");
            }
        } else {
            // Redirect to Teams page if task ID parameter is missing
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }
}
