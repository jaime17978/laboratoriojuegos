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

import dao.PerfilDAO;
import dao.PermisosDAO;
import models.Perfil;
import models.User;

/**
 * Clase servlet que maneja las peticiones a la url de perfiles.
 */

@WebServlet("/perfiles")
public class PerfilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PerfilDAO daoPerfil = new PerfilDAO();
		HttpSession session = request.getSession();
		PermisosDAO daoPermisos = new PermisosDAO();
        User user = (User) session.getAttribute("user");
        
        try {
            
        	/**
	         * Al ser una pagina a la que solo puede acceder un administrador
	         * o desarrollador hay que comprobar los permisos del usuario.
	         * Si el usuario no tiene permisos para entrar se le redirecciona
	         * a la pagina de error. 
	         */
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 1)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	        }
        	
        	/**
        	 * Mediante el DAO, obtenemos la informacion de la tabla perfiles
        	 */
            List<Perfil> listaPerfiles = daoPerfil.perfilesBD();
            request.setAttribute("listaPerfiles", listaPerfiles);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/perfiles.jsp");
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
    	
		PerfilDAO dao = new PerfilDAO();
		
        String option = request.getParameter("option");
        String nombre;
        boolean activo;
        int id;
        int idNueva;
        
        try {
        	/**
        	 * En funcion del parametro "option" de la peticion
        	 * se realizaran acciones diferentes (Ej: cambiar nombre,
        	 * borrar, crear, etc.).
        	 */
        	switch(option) {
               case "c_nombre":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   nombre = request.getParameter("nombre");

            	   dao.cambioNombre(id, nombre);
            	   
            	   break;
               
               case "c_activo":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   activo = Boolean.parseBoolean(request.getParameter("activo"));
            	   dao.cambioActivo(id,activo);
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarPerfil(id);
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearPerfil();  	
            	   
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
