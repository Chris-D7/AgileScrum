package com.agilescrum.agilescrum.servlets;

import com.agilescrum.agilescrum.ejb.AboutBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@DeclareRoles({"ADMIN", "RESEARCH", "COMMON"})
@ServletSecurity(httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed =
                {"ADMIN"})})
@WebServlet(name = "About", value = "/About")
public class About extends HttpServlet {

    @Inject
    AboutBean aboutBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage", "About");
        request.setAttribute("aboutText", aboutBean.getAboutText());
        request.getRequestDispatcher("/WEB-INF/pages/about.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String editedAboutText = request.getParameter("editedAboutText");
        aboutBean.updateAboutText(editedAboutText);
        response.sendRedirect(request.getContextPath() + "/About");
    }
}
