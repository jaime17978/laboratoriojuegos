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

/**
 * Clase servlet de manejo de las peticiones enviadas
 * a la url de la pagina de años.
 */
@WebServlet("/anhos")
public class AnhosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnhoDAO dao = new AnhoDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        /**
         * Dado que la pantalla de años es solo para administradores o
         * desarrolladores es necesario comprobar los permisos del usuario.
         * Si no tiene los permisos necesarios se le redireccionara a la pagina
         * de error.
         */
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            /**
             * Se accede a la base de datos mediante el DAO
             * y este devuelve una lista de los años que se le pasa
             * a la pagina.
             */
            List<Categoria> listaAnhos = dao.anhosBD();
            request.setAttribute("listaAnhos", listaAnhos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("anhos.jsp");
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
    	
		AnhoDAO dao = new AnhoDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String nombre;
        int id;
        int idNueva;
        
        try {
        	/**
        	 * En funcion del valor del parametro "option" este metodo
        	 * realizara diferentes cambios en la tabla de años (Ej: cambiar
        	 * el nombre, borrar o crear).
        	 */
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
            	   /**
            	    * Cuando se crea un año nuevo en la tabla de la base de datos
            	    * es necesario pasarle la id del nuevo año creado a la pagina
            	    * mediante la respuesta ya que esta peticion POST se hace de 
            	    * manera asincrona mediante AJAX (Ver: anhos.js).
            	    */
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
