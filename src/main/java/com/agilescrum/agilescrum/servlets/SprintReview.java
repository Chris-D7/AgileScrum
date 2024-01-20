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
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long sprintId = Long.parseLong(request.getParameter("sprintId"));
        String updatedReview = request.getParameter("review");
        Long teamId = Long.parseLong(request.getParameter("id"));

        sprintBean.updateReview(sprintId, updatedReview);

        response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
    }
}
