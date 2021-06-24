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

import com.google.gson.Gson;

import dao.AlumnoDAO;
import dao.PaisDAO;
import models.Pais;
import models.User;


@WebServlet("/paises")
public class PaisesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PaisesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PaisDAO dao = new PaisDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            
            List<Pais> listaPaises = dao.paisesBD();
            request.setAttribute("listaPaises", listaPaises);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("paises.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		PaisDAO dao = new PaisDAO();
        
        String option = request.getParameter("option");
        String nombre;
        String nombre_a;
        String id;
        
        try {
        	
        	switch(option) {
               case "c_nombre":
            	   nombre = request.getParameter("nombre");
            	   nombre_a = request.getParameter("old");
            	   
            	   
            	   dao.cambioNombre(nombre_a,nombre);
            	   
            	   break;
               
               case "c_id":
            	   id = request.getParameter("id");
            	   nombre = request.getParameter("nombre");
            	   dao.cambioID(id,nombre);
            	   break;
            	
               case "delete":
            	   nombre = request.getParameter("nombre");
            	   dao.borrarPais(nombre);
            	   
            	   break;
            	   
               case "create":
            	   dao.crearPais();  	   
            	   break;
               
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			return;
		}
         
    }

}
