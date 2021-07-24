package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;

@WebServlet("/m_alumno")
public class ModificarAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModificarAlumnoServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
    		AlumnoDAO dao = new AlumnoDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	if (request.getParameter("nombre") != null) {
        		String nombre = request.getParameter("nombre");

        		dao.modificarAlumnoNombre(user.getId(), id, nombre);
        	}
        	else if (request.getParameter("genero") != null) {
        		String genero = request.getParameter("genero");
        		
        		dao.modificarAlumnoGenero(user.getId(), id, genero);
        		
        	}
        	else if (request.getParameter("edad") != null) {
        		int edad = Integer.parseInt(request.getParameter("edad"));
        		System.out.println(id);
        		System.out.println(edad);
        		dao.modificarAlumnoEdad(user.getId(), id, edad);
        	}
        	else if (request.getParameter("curso") != null) {
        		int curso = Integer.parseInt(request.getParameter("curso"));
        		
        		dao.modificarAlumnoCurso(user.getId(), id, curso);
        	}
        	/*else if (request.getParameter("idioma") != null) {
        		int idioma = Integer.parseInt(request.getParameter("idioma"));
        		
        		dao.modificarAlumnoIdioma(user.getId(), id, idioma);
        	}*/
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}