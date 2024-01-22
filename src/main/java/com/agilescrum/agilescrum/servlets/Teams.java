package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.ejb.TeamsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Teams", value = "/Teams")
public class Teams extends HttpServlet {

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the list of teams for the current user
        List<TeamsDto> teams = teamsBean.findTeamsForCurrentUser(request.getRemoteUser());

        // Set attributes for the JSP page
        request.setAttribute("teamsList", teams);
        request.setAttribute("activePage", "Teams");

        // Forward to the JSP page
        request.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form submission
        String subject = request.getParameter("subject");
        String masterEmail = request.getParameter("master");

        // Create a new team using TeamsBean
        teamsBean.createTeam(subject, masterEmail);

        // Redirect to the Teams page after creating the team
        response.sendRedirect(request.getContextPath() + "/Teams");
    }
}
