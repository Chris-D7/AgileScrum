package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.SprintDto;
import com.agilescrum.agilescrum.common.TeamsDto;
import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.ejb.SprintBean;
import com.agilescrum.agilescrum.ejb.TeamsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "TeamPage", value = "/TeamPage")
public class TeamPage extends HttpServlet {

    @Inject
    TeamsBean teamsBean;

    @Inject
    SprintBean sprintBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve team ID from the request parameters
        Long teamsId = Long.parseLong(request.getParameter("id"));

        // Find the TeamsDto object for the specified team ID
        TeamsDto teamsDto = teamsBean.findTeamById(teamsId);

        // Variable to determine if the user is a true team member
        boolean trueMember = false;

        // Retrieve the members of the team
        List<UserDto> members = teamsDto.getMembers();

        // Check if the user is the team master or a member
        if (checkIfTeamMember(request, teamsDto)) {
            // If the user is a team member, retrieve sprints for the team
            List<SprintDto> sprints = sprintBean.findSprintsByTeam(teamsId);

            // Set attributes for the JSP page
            request.setAttribute("team", teamsDto);
            request.setAttribute("sprints", sprints);
            request.setAttribute("currentSprint", sprintBean.findCurrentSprint(sprints));

            // Set trueMember to true
            trueMember = true;
        } else {
            // Check if the user is a member by email
            for (UserDto member : members) {
                if (request.getRemoteUser().equals(member.getEmail())) {
                    trueMember = true;
                    break;
                }
            }
        }

        // Forward to the JSP page if the user is a true team member, otherwise redirect to the Teams page
        if (trueMember) {
            request.getRequestDispatcher("/WEB-INF/pages/teampage.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    // Check if the user is the team master or a regular member
    private boolean checkIfTeamMember(HttpServletRequest request, TeamsDto teamDto) {
        String currentUserEmail = request.getRemoteUser();

        return currentUserEmail.equals(teamDto.getMaster().getEmail()) || teamDto.getMembers().stream()
                .anyMatch(member -> currentUserEmail.equals(member.getEmail()));
    }
}
