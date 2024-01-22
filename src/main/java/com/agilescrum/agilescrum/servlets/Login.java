package com.agilescrum.agilescrum.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the login page JSP
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set an attribute to display a message about incorrect email or password
        request.setAttribute("message", "Email or password incorrect");
        // Forward the request back to the login page JSP with the error message
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
