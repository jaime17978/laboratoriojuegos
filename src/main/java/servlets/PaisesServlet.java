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
import models.Pais;
import models.User;

/**
 * Clase servlet que maneja las peticiones de la pagina de paises.
 */
@WebServlet("/paises")
public class PaisesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PaisesServlet() {
        super();
    }
    
    /**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PaisDAO dao = new PaisDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        /**
         * Al ser una pagina a la que solo puede acceder un administrador
         * o desarrollador hay que comprobar los permisos del usuario.
         * Si el usuario no tiene permisos para entrar se le redirecciona
         * a la pagina de error. 
         */
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            /**
             * Se accede a la base de datos mediante el DAO
             * para obtener la informacion que necesita la pagina.
             */
            List<Pais> listaPaises = dao.paisesBD();
            request.setAttribute("listaPaises", listaPaises);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("paises.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}
	
	/**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		PaisDAO dao = new PaisDAO();
        
        String option = request.getParameter("option");
        String nombre;
        String nombre_a;
        String id;
        
        try {
        	/**
        	 * En funcion del parametro "option" de la peticion
        	 * se realizaran acciones diferentes (Ej: cambiar nombre,
        	 * borrar, crear, etc.).
        	 */
        	switch(option) {
        	  /**
    		   * La tabla de paises es un poco distinta a las demas ya que su
    		   * clave primaria es el nombre y no una ID numerica. Por eso es necesario
    		   * obtener el nombre del pais para cada modificacion, en lugar de la ID.
    		   */
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
