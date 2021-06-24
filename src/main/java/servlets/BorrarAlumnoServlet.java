package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AlumnoDAO;
import models.User;

@WebServlet("/b_alumno")
public class BorrarAlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BorrarAlumnoServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
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