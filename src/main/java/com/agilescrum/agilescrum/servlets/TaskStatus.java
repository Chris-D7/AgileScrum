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
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskIdParam = request.getParameter("taskId");
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            try {
                Long taskId = Long.parseLong(taskIdParam);
                taskBean.updateTaskStatus(taskId);


                response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/Teams");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }
}
