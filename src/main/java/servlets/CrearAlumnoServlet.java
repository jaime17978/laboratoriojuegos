package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;

@WebServlet("/c_alumno")
public class CrearAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CrearAlumnoServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
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