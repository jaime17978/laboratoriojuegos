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
import dao.PermisosDAO;
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
		PermisosDAO daoPermisos = new PermisosDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
		try {
			
			/**
	         * Al ser una pagina a la que solo puede acceder un administrador
	         * o desarrollador hay que comprobar los permisos del usuario.
	         * Si el usuario no tiene permisos para entrar se le redirecciona
	         * a la pagina de error. 
	         */
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 3)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	            return;
	        }
	        
			
            /**
             * Se accede a la base de datos mediante el DAO
             * y este devuelve una lista de los años que se le pasa
             * a la pagina.
             */
            List<Categoria> listaAnhos = dao.anhosBD();
            request.setAttribute("listaAnhos", listaAnhos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/anhos.jsp");
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
