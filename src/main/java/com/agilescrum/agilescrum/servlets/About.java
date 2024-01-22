package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.AboutBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

// Security roles required for this servlet
@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
// Security constraints for HTTP methods
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "About", value = "/About")
public class About extends HttpServlet {

    @Inject
    AboutBean aboutBean;

    // Handle HTTP GET requests to fetch and display the About page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set attributes for JSP rendering
        request.setAttribute("activePage", "About");
        request.setAttribute("aboutText", aboutBean.getAboutText());
        // Forward the request to the About page JSP
        request.getRequestDispatcher("/WEB-INF/pages/about.jsp").forward(request,response);
    }

    // Handle HTTP POST requests to update the About text
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the edited About text from the request parameter
        String editedAboutText = request.getParameter("editedAboutText");
        // Update the About text using the AboutBean
        aboutBean.updateAboutText(editedAboutText);
        // Redirect the user back to the About page after updating
        response.sendRedirect(request.getContextPath() + "/About");
    }
}
