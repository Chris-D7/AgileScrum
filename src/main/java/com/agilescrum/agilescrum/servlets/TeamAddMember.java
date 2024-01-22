package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.TeamsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TeamAddMember", value = "/TeamAddMember")
public class TeamAddMember extends HttpServlet {

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirects to the Teams page
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form submission
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        String memberEmail = request.getParameter("email");

        // Call TeamsBean to add the member to the team
        teamsBean.addMemberToTeam(teamId, memberEmail);

        // Redirect back to the TeamPage with the updated team information
        response.sendRedirect(request.getContextPath() + "/TeamPage?id=" + teamId);
    }
}
