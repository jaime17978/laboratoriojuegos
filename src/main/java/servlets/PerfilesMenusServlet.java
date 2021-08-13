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

import dao.CategoriaDAO;
import dao.PerfilMenuDAO;
import dao.PermisosDAO;
import models.Categoria;
import models.PerfilMenu;
import models.User;

/**
 * Clase servlet que maneja las peticiones a la url de perfiles menus.
 */
@WebServlet("/perfiles_menus")
public class PerfilesMenusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriaDAO dao = new CategoriaDAO();
		PerfilMenuDAO daoPM = new PerfilMenuDAO();
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
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 1)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	        }
            
        	/**
        	 * Mediante el DAO, obtenemos la informacion de perfiles y menus.
        	 */
            List<Categoria> listaPerfiles = dao.perfilesBD();
            request.setAttribute("listaPerfiles", listaPerfiles);
            
            List<Categoria> listaMenus = dao.menusBD();
            request.setAttribute("listaMenus", listaMenus);
            
            List<PerfilMenu> listaPerfilesMenus = daoPM.perfilesMenusBD();
            request.setAttribute("listaPerfilesMenus", listaPerfilesMenus);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/perfiles_menus.jsp");
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
		PerfilMenuDAO dao = new PerfilMenuDAO();
		String option = request.getParameter("option");
		int perfil;
		int menu;
		String nombre;
		
		try {
        	/**
        	 * En el javascript de la pagina se envia un parametro llamado "option".
        	 * Este parametro determina que accion debe tomar esta funcion (modificar el nombre
        	 * o borrar el permiso).
        	 * 
        	 * Los cambios en la base de datos se realizan mediante los DAO.
        	 */
        	switch(option) {
            	   
               case "c_nombre":
            	   perfil = Integer.parseInt(request.getParameter("perfil"));
            	   menu = Integer.parseInt(request.getParameter("menu"));
            	   nombre = request.getParameter("nombre");
            	   dao.cambioNombre(perfil, menu, nombre);
            	   
            	   break;
            	
               case "delete":
            	   perfil = Integer.parseInt(request.getParameter("perfil"));
            	   menu = Integer.parseInt(request.getParameter("menu"));
            	   dao.borrarPerfilMenu(perfil, menu);
            	   
            	   break;
               
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
	}

}
