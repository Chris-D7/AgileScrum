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

// Security roles for the servlet
@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
// Security constraints for the servlet
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
        // Set attribute for the active page in the navigation menu
        request.setAttribute("activePage", "Usergroups");

        // Retrieve all users and their roles
        List<UserDto> users = userBean.findAllUsers();
        for (UserDto user : users) {
            // Find and set the user's role
            String usergroup = usergroupsBean.findUsergroupRole(user.getEmail());
            user.setUsergroup(usergroup);
        }

        // Set attributes for the JSP page
        request.setAttribute("users", users);
        request.setAttribute("rolesList", Arrays.asList("ADMIN", "RESEARCH", "COMMON"));

        // Forward to the JSP page
        request.getRequestDispatcher("/WEB-INF/pages/usergroups.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameter names from the request
        Enumeration<String> parameterNames = request.getParameterNames();

        // Iterate through parameter names to find user role updates
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            // Check if the parameter name indicates a user role update
            if (paramName.startsWith("userRole=-=")) {
                String email = paramName.substring("userRole=-=".length());
                String role = request.getParameter(paramName);

                // Update user role using UsergroupsBean
                usergroupsBean.updateUsergroup(email, role);
            }
        }

        // Redirect to the Usergroups page after processing the updates
        response.sendRedirect(request.getContextPath() + "/Usergroups");
    }
}
