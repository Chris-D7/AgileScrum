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
        Long teamsId = Long.parseLong(request.getParameter("id"));
        TeamsDto teamsDto = teamsBean.findTeamById(teamsId);
        boolean trueMember = false;
        List<UserDto> members = teamsDto.getMembers();
        if(checkIfTeamMember(request, teamsDto)){
            List<SprintDto> sprints = sprintBean.findSprintsByTeam(teamsId);
            request.setAttribute("team", teamsDto);
            request.setAttribute("sprints", sprints);
            request.setAttribute("currentSprint", sprintBean.findCurrentSprint(sprints));
            trueMember = true;
        } else {
            for(UserDto member : members){
                if(request.getRemoteUser().equals(member.getEmail())){
                    trueMember = true;
                    break;
                }
            }
        }
        if(trueMember){
            request.getRequestDispatcher("/WEB-INF/pages/teampage.jsp").forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/Teams");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean checkIfTeamMember(HttpServletRequest request, TeamsDto teamDto) {
        String currentUserEmail = request.getRemoteUser();

        return currentUserEmail.equals(teamDto.getMaster().getEmail()) || teamDto.getMembers().stream()
                .anyMatch(member -> currentUserEmail.equals(member.getEmail()));
    }
}
