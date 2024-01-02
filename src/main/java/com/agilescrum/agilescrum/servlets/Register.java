package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.UserBean;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match. Please try again.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }
        userBean.createUser(username, email, password);
        response.sendRedirect(request.getContextPath() + "/Login");
    }

}
