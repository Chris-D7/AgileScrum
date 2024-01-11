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
        response.sendRedirect(request.getContextPath() + "/Teams");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long teamId = Long.parseLong(request.getParameter("teamId"));
        Long memberId = Long.parseLong(request.getParameter("memberId"));

        teamsBean.deleteMemberFromTeam(teamId, memberId);

        response.sendRedirect(request.getContextPath() + "/TeamPage?id=" + teamId);
    }
}
