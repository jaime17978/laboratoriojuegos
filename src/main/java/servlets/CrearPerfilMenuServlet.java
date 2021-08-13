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

import dao.CategoriaDAO;
import dao.PerfilMenuDAO;
import models.Categoria;
import models.PerfilMenu;

@WebServlet("/crear_perfil_menu")
public class CrearPerfilMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo que maneja las peticiones POST. En este caso crea un perfil_menu con los valores
	 * introducidos por el usuario en el formulario de creacion de perfil_menu.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CategoriaDAO dao = new CategoriaDAO();
        PerfilMenuDAO daoPM = new PerfilMenuDAO();
        
		String nombre = request.getParameter("nombre");
		int perfil = Integer.parseInt(request.getParameter("perfiles"));
		int menu = Integer.parseInt(request.getParameter("menus"));
		
		PerfilMenuDAO perfilMenuDAO = new PerfilMenuDAO();
		
		/**
		 * Crea el perfil_menu con los parametros del formulario y redirecciona de nuevo a la pagina.
		 */
		try {
			if(!perfilMenuDAO.crearPerfilMenu(perfil, menu, nombre)) {
				request.setAttribute("message", "El permiso que se ha intentado crear ya existe.");
			}
			
			List<Categoria> listaPerfiles = dao.perfilesBD();
            request.setAttribute("listaPerfiles", listaPerfiles);
            
            List<Categoria> listaMenus = dao.menusBD();
            request.setAttribute("listaMenus", listaMenus);
            
            List<PerfilMenu> listaPerfilesMenus = daoPM.perfilesMenusBD();
            request.setAttribute("listaPerfilesMenus", listaPerfilesMenus);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/perfiles_menus.jsp");
            dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			doGet(request,response);
		}
	}
}
