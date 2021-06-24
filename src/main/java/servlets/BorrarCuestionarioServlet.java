package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CuestionarioDAO;
import models.User;

/**
 * Servlet implementation class BorrarCuestionarioServlet
 */
@WebServlet("/b_cuest")
public class BorrarCuestionarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
    		CuestionarioDAO dao = new CuestionarioDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        	
        	int id = Integer.parseInt(request.getParameter("id"));

        	dao.bajaCuestionario(user.getId(), id);
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }

}
