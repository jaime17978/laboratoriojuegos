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

/**
 * Servlet que maneja las peticiones de la URL de restablecer contrase�a.
 * 
 * Esta pagina es solo temporal ya que la forma de restablecer la contrase�a
 * es simplemente para probar la aplicacion. En una version final de la aplicacion
 * esta pagina deberia ser modificada para cambiar las contrase�as de manera diferente.
 */
@WebServlet("/rest_contra")
public class ContraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ContraServlet() {
        super();
    }
    
    /**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("rest_contra.jsp");
        dispatcher.forward(req, resp);
    }
    
    /**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String passwordVerify = request.getParameter("password1");

        /**
         * La unica verificacion que se hace es que el email exista y
         * que las nueva contrase�a introducida coincide en los dos
         * input del formulario. No necesita la contrase�a existente 
         * del usuario porque es un metodo temporal de pruebas que deberia ser
         * modificad en una version final de la aplicacion.
         */
        if (password.equals(passwordVerify)) {
	        UserDAO userDao = new UserDAO();
	
	        try {
	            User user = userDao.userChangePassword(email, password);
	            String destPage = "rest_contra.jsp";
	
	            if (user != null) {
	            	String message = "Cambio de contrase�a correcto";
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
