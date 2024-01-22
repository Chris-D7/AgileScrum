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
    SprintBean sprintBean;

    @Inject
    TeamsBean teamsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve team ID from the request parameter
        Long teamId = Long.parseLong(request.getParameter("id"));

        // Retrieve team details based on the team ID
        TeamsDto team = teamsBean.findTeamById(teamId);

        // Check if the current user is a team member
        boolean isTeamMember = checkIfTeamMember(request, team);

        // If the user is a team member, proceed to retrieve and display sprint information
        if (isTeamMember) {
            // Retrieve sprints for the team
            List<SprintDto> sprints = sprintBean.findSprintsByTeam(teamId);

            // Set attributes for the JSP page
            request.setAttribute("sprints", sprints);
            request.setAttribute("currentSprint", sprintBean.findCurrentSprint(sprints));
            request.setAttribute("team", team);

            // Forward the request to the sprint page
            request.getRequestDispatcher("/WEB-INF/pages/sprintpage.jsp").forward(request, response);
        } else {
            // If the user is not a team member, redirect to the Teams page
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve team ID from the request parameter
        Long teamId = Long.parseLong(request.getParameter("id"));

        // Check if the current user is the master of the team
        if (!checkIfUserIsMaster(request, teamId)) {
            // If the user is not the master, redirect to the Teams page
            response.sendRedirect(request.getContextPath() + "/Teams");
        } else {
            // If the user is the master, proceed to create a new sprint
            LocalDateTime endDate = LocalDateTime.parse(request.getParameter("endDate"));

            // Create a new sprint for the team
            sprintBean.createSprint(teamId, endDate);

            // Redirect back to the SprintPage with the updated sprint information
            response.sendRedirect(request.getContextPath() + "/SprintPage?id=" + teamId);
        }
    }

    // Check if the current user is the master or a member of the team
    private boolean checkIfTeamMember(HttpServletRequest request, TeamsDto teamDto) {
        String currentUserEmail = request.getRemoteUser();

        return currentUserEmail.equals(teamDto.getMaster().getEmail()) || teamDto.getMembers().stream()
                .anyMatch(member -> currentUserEmail.equals(member.getEmail()));
    }

    // Checks if the current user is the master of the team
    private boolean checkIfUserIsMaster(HttpServletRequest request, Long teamId) {
        String currentUserEmail = request.getRemoteUser();
        TeamsDto teamDto = teamsBean.findTeamById(teamId);

        return currentUserEmail.equals(teamDto.getMaster().getEmail());
    }
}
