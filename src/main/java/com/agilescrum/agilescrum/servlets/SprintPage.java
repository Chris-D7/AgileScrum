package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.SprintDto;
import com.agilescrum.agilescrum.common.TaskDto;
import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.ejb.SprintBean;
import com.agilescrum.agilescrum.ejb.TaskBean;
import com.agilescrum.agilescrum.ejb.TeamsBean;
import com.agilescrum.agilescrum.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SprintPage", value = "/SprintPage")
public class SprintPage extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    SprintBean sprintBean;

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long teamId = Long.parseLong(request.getParameter("id"));
        TeamsDto team = teamsBean.findTeamById(teamId);

        boolean isTeamMember = checkIfTeamMember(request, team);

        if (isTeamMember) {
            List<SprintDto> sprints = sprintBean.findSprintsByTeam(teamId);
            request.setAttribute("sprints", sprints);
            request.setAttribute("currentSprint", sprintBean.findCurrentSprint(sprints));
            request.setAttribute("team", team);
            request.getRequestDispatcher("/WEB-INF/pages/sprintpage.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Long teamId = Long.parseLong(request.getParameter("id"));
            if (!checkIfUserIsMaster(request, teamId)) {
            response.sendRedirect(request.getContextPath() + "/Teams");
            } else {

                LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate"));

                sprintBean.createSprint(teamId, endDate);

                response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
            }
    }

    private boolean checkIfTeamMember(HttpServletRequest request, TeamsDto teamDto) {
        String currentUserEmail = request.getRemoteUser();

        return currentUserEmail.equals(teamDto.getMaster().getEmail()) || teamDto.getMembers().stream()
                .anyMatch(member -> currentUserEmail.equals(member.getEmail()));
    }

    private boolean checkIfUserIsMaster(HttpServletRequest request, Long teamId) {
        String currentUserEmail = request.getRemoteUser();
        TeamsDto teamDto = teamsBean.findTeamById(teamId);

        return currentUserEmail.equals(teamDto.getMaster().getEmail());
    }
}
