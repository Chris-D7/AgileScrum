package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.common.UserDto;
import com.agilescrum.agilescrum.ejb.UserBean;
import com.agilescrum.agilescrum.ejb.UsergroupsBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"ADMIN", "RESEARCH"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "Usergroups", value = "/Usergroups")
public class Usergroups extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    UsergroupsBean usergroupsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage", "Usergroups");
        List<UserDto> users = userBean.findAllUsers();
        for (UserDto user : users) {
            String usergroup = usergroupsBean.findUsergroupRole(user.getEmail());
            user.setUsergroup(usergroup);
        }
        request.setAttribute("users", users);
        request.setAttribute("rolesList", Arrays.asList("ADMIN", "RESEARCH", "COMMON"));
        request.getRequestDispatcher("/WEB-INF/pages/usergroups.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            if (paramName.startsWith("userRole=-=")) {
                String email = paramName.substring("userRole=-=".length());
                String role = request.getParameter(paramName);

                usergroupsBean.updateUsergroup(email, role);
            }
        }

        response.sendRedirect(request.getContextPath() + "/Usergroups");
    }
}
