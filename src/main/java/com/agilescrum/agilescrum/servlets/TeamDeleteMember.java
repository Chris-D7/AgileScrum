package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.TeamsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TeamDeleteMember", value = "/TeamDeleteMember")
public class TeamDeleteMember extends HttpServlet {

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirects to the Teams page
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve team and member IDs from the request parameters
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        Long memberId = Long.parseLong(request.getParameter("memberId"));

        // Call TeamsBean to delete the specified member from the team
        teamsBean.deleteMemberFromTeam(teamId, memberId);

        // Redirect back to the TeamPage after member deletion
        response.sendRedirect(request.getContextPath() + "/TeamPage?id=" + teamId);
    }
}
