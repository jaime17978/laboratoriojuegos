package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;

/**
 * Clase servlet que maneja las peticiones de modificacion de alumnos
 * desde la pagina de alumnos de la aplicacion.
 */
@WebServlet("/m_alumno")
public class ModificarAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
	 * Metodo que maneja las peticiones POST. En funcion de los parametros de la
	 * peticion realizara los cambios que correspondan en el alumno en la base de datos.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
    		AlumnoDAO dao = new AlumnoDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            //Si la peticion tiene parametro "nombre", modificara el nombre.
        	int id = Integer.parseInt(request.getParameter("id"));
        	if (request.getParameter("nombre") != null) {
        		String nombre = request.getParameter("nombre");

        		dao.modificarAlumnoNombre(user.getId(), id, nombre);
        	}
        	//Si la peticion tiene parametro "genero" modificara el genero.
        	else if (request.getParameter("genero") != null) {
        		String genero = request.getParameter("genero");
        		
        		dao.modificarAlumnoGenero(user.getId(), id, genero);
        		
        	}
        	//Si la peticion tiene parametro "edad" modificara la edad.
        	else if (request.getParameter("edad") != null) {
        		int edad = Integer.parseInt(request.getParameter("edad"));
        		System.out.println(id);
        		System.out.println(edad);
        		dao.modificarAlumnoEdad(user.getId(), id, edad);
        	}
        	//Si la peticion tiene parametro "curso" modificara el curso.
        	else if (request.getParameter("curso") != null) {
        		int curso = Integer.parseInt(request.getParameter("curso"));
        		
        		dao.modificarAlumnoCurso(user.getId(), id, curso);
        	}
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}