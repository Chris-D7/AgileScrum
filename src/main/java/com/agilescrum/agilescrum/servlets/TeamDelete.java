package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.TeamsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TeamDelete", value = "/TeamDelete")
public class TeamDelete extends HttpServlet {

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirects to the Teams page
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Call TeamsBean to delete the team with the specified ID
        teamsBean.deleteTeam(Long.parseLong(request.getParameter("teamId")));

        // Redirect back to the Teams page after team deletion
        response.sendRedirect(request.getContextPath() + "/Teams");
    }
}
