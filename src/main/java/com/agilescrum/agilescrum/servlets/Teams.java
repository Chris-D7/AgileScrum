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
        List<TeamsDto> teams = teamsBean.findTeamsForCurrentUser(request.getRemoteUser());
        request.setAttribute("teamsList", teams);
        request.setAttribute("activePage", "Teams");
        request.getRequestDispatcher("/WEB-INF/pages/teams.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
