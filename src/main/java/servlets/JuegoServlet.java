package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.JuegoDAO;
import models.Juego;
import models.User;

@WebServlet("/g_juego")
public class JuegoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public JuegoServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
    		JuegoDAO dao = new JuegoDAO();
        	
        	String nombre = request.getParameter("nombre");

        	Juego juego = dao.juegoBD(nombre);
    		
        	response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8"); 
            
            if (juego != null) {
            	response.getWriter().write(Integer.toString(juego.getId()));
            }
            else {
            	response.getWriter().write("0");
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}