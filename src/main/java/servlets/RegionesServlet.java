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

import dao.PaisDAO;
import dao.RegionDAO;
import models.Pais;
import models.Region;
import models.User;

@WebServlet("/regiones")
public class RegionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegionesServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RegionDAO dao = new RegionDAO();
        
        String option = request.getParameter("option");
        String nombre;
        String pais;
        String id;
        String id_a;
        
        try {
        	
        	switch(option) {
               case "c_id":
            	   id = request.getParameter("id");
            	   id_a = request.getParameter("old");
            	   
            	   dao.cambioID(id_a,id);
            	   break;
            	   
               case "c_nombre":
            	   id = request.getParameter("id");
            	   nombre = request.getParameter("nombre");
            	   
            	   dao.cambioNombre(id,nombre);
            	   break;
            	   
               case "c_pais":
            	   id = request.getParameter("id");
            	   pais = request.getParameter("pais");
            	   
            	   dao.cambioPais(id,pais);
            	   break;
            	   
               case "delete":
            	   id = request.getParameter("id");
            	   
            	   dao.borrarRegion(id);
            	   break;
               
               case "create":
            	   dao.crearRegion();
            	   break;
            	   
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			return;
		}
        
	}

}
