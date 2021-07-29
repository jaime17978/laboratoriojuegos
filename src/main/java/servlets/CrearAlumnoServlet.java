package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;

/**
 * Clase servlet que maneja las peticiones de creacion de alumnos
 * de la pagina de alumnos.
 */
@WebServlet("/c_alumno")
public class CrearAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CrearAlumnoServlet() {
        super();
    }
    /**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			/**
			 * Crea un alumno con datos por defecto. Se le pasa la id del
			 * usuario que hace la creacion para que se guarde en la base de datos.
			 */
    		AlumnoDAO dao = new AlumnoDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        	
        	String id = dao.crearAlumnoDB(user.getId(), "", 1, "niño", 0, user.getLanguage(), 0);
        	response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8"); 
            response.getWriter().write(id);
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}