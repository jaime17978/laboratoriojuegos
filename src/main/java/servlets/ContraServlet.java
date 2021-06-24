package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;

import dao.UserDAO;
import models.User;

import java.io.*;
import java.sql.SQLException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/rest_contra")
public class ContraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ContraServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("rest_contra.jsp");
        dispatcher.forward(req, resp);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String passwordVerify = request.getParameter("password1");

        if (password.equals(passwordVerify)) {
	        UserDAO userDao = new UserDAO();
	
	        try {
	            User user = userDao.userChangePassword(email, password);
	            String destPage = "rest_contra.jsp";
	
	            if (user != null) {
	            	String message = "Cambio de contraseña correcto";
	                request.setAttribute("message", message);
	                destPage = "login.jsp";
	            } else {
	                String message = "Email incorrecto";
	                request.setAttribute("message", message);
	            }
	
	            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
	            dispatcher.forward(request, response);
	
	        } catch (SQLException | ClassNotFoundException ex) {
	            throw new ServletException(ex);
	        }
        }   
    }
    
}
