package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.UserBean;
import com.agilescrum.agilescrum.ejb.UsergroupsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    @Inject
    UserBean userBean;

    @Inject
    UsergroupsBean usergroupsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the registration page
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user registration details from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate that the entered passwords match
        if (!password.equals(confirmPassword)) {
            // If passwords do not match, set an error message and forward to the registration page
            request.setAttribute("message", "Passwords do not match. Please try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Create a new user and user group
        userBean.createUser(username, email, password);
        usergroupsBean.createUsergroup(email);

        // Redirect to the login page after successful registration
        response.sendRedirect(request.getContextPath() + "/Login");
    }

}
