package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import models.User;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Clase servlet que maneja las peticiones get y post a la URL de login.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase LoginServlet.
     */
    public LoginServlet() {
        super();
    }
    
    /**
     * Metodo que maneja las peticiones POST a la URL de login.
     * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        /**
         * Se verifica si el usuario ha introducido los valores de
         * correo y contraseña correctos mediante el DAO de usuarios.
         * 
         * Si se ha logeado correctamente redireccionamos a la pagina home.
         * Si el correo o contraseña son incorrectos volvemos a la pagina
         * de login con un mensaje de error.
         */
        try {
            User user = userDao.userLogin(email, password);
            String destPage = "login.jsp";

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                destPage = "home.jsp";
            } else {
                String message = "Usuario o contraseña incorrectos";
                request.setAttribute("message", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }
    
    /**
     * Metodo que maneja las peticiones GET a la URL de login
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, resp);
    }
    
}
