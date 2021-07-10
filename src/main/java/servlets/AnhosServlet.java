package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AnhoDAO;
import models.Categoria;
import models.User;

@WebServlet("/anhos")
public class AnhosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnhoDAO dao = new AnhoDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            
            List<Categoria> listaAnhos = dao.anhosBD();
            request.setAttribute("listaAnhos", listaAnhos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("anhos.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		AnhoDAO dao = new AnhoDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String nombre;
        int id;
        int idNueva;
        
        try {
        	
        	switch(option) {
               case "c_nombre":
            	   nombre = request.getParameter("nombre");
            	   id = Integer.parseInt(request.getParameter("id"));
            	   
            	   dao.cambioNombre(id, nombre);
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarAnho(id);
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearAnho(user.getId());  	
            	   
            	   response.setContentType("text/plain");
                   response.setCharacterEncoding("UTF-8"); 
                   response.getWriter().write(Integer.toString(idNueva));
            	   break;
               
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			return;
		}
         
    }
}
