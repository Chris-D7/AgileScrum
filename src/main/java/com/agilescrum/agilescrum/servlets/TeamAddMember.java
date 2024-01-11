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
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        String memberEmail = request.getParameter("email");

        teamsBean.addMemberToTeam(teamId, memberEmail);

        response.sendRedirect(request.getContextPath() + "/TeamPage?id=" + teamId);
    }
}
