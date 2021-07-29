package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;
/**
 * Clase servlet que maneja las peticiones de borrado de alumnos.
 */
@WebServlet("/b_alumno")
public class BorrarAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
    		/**
    		 * Obtiene la ID del alumno mediante la peticion y se la pasa al DAO para que
    		 * acceda a la base de datos y la borre. 
    		 */
    		AlumnoDAO dao = new AlumnoDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        	
        	int id = Integer.parseInt(request.getParameter("id"));

        	dao.bajaAlumno(user.getId(), id);
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}