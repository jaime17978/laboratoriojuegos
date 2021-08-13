package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegionDAO;

/**
 * Clase servlet que maneja las peticiones POST de la pagina de regiones.
 */
@WebServlet("/regiones")
public class RegionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegionesServlet() {
        super();
    }

    /**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RegionDAO dao = new RegionDAO();
        
        String option = request.getParameter("option");
        String nombre;
        String pais;
        String id;
        String id_a;
        
        try {
        	/**
        	 * En funcion del parametro "option" de la peticion
        	 * el metodo realizara diferentes acciones en la base de datos
        	 * (Ej: crear, cambiar id, borrar, etc.).
        	 */
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
