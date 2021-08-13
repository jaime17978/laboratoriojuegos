package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Clase servlet que maneja las peticiones get y post a la URL base.
 */
@WebServlet("/base")
public class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase BaseServlet.
     */
    public BaseServlet() {
        super();
    }
      
    /**
     * Metodo que maneja las peticiones GET a la URL principal.
     * Si el usuario esta logeado se le redirecciona a la pagina de inicio,
     * en caso contrario a la pagina de login.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
     */ 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
        	RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/login.jsp");
            dispatcher.forward(req, resp);
        } else {
        	RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/home.jsp");
        	dispatcher.forward(req, resp);
        }
    }
    
}
	  