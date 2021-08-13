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

import dao.ColegioDAO;
import dao.PermisosDAO;
import dao.RegionDAO;
import models.Colegio;
import models.Region;
import models.User;

/**
 * Clase servlet que se encarga del manejo de peticiones a la
 * URL de la pagina de colegios.
 */
@WebServlet("/colegios")
public class ColegiosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ColegioDAO daoColegio = new ColegioDAO();
		RegionDAO daoRegion = new RegionDAO();
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
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 2)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	        }
        	
            /**
             * Se obtienen los datos necesarios para la pagina
             * mediante los DAO.
             */
            List<Colegio> listaColegios = daoColegio.colegiosBD();
            request.setAttribute("listaColegios", listaColegios);
            
            List<Region> listaRegiones = daoRegion.regionesBD();
            request.setAttribute("listaRegiones", listaRegiones);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/colegios.jsp");
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
		ColegioDAO dao = new ColegioDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String valor;
        String tipo;
        int id;
        int idNueva;
        int cp;
        
        try {
        	/**
        	 * En funcion del parametro "option" de la peticion
        	 * esta funcion llevara a cabo diferentes acciones (Ej: cambiar el
        	 * nombre, crear un colegio, borrar un colegio, etc.).
        	 */
        	switch(option) {
               case "c_nombre":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("nombre");
            	   
            	   dao.cambioNombre(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_direccion":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("direccion");
            	   
            	   dao.cambioDireccion(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_localidad":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("localidad");
            	   
            	   dao.cambioLocalidad(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_region":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("region");
            	   
            	   dao.cambioRegion(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_tipo":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   tipo = request.getParameter("tipo");
            	   
            	   dao.cambioTipo(id, tipo, user.getId());
            	   
            	   break;
            	   
               case "c_cp":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   cp = Integer.parseInt(request.getParameter("cp"));
            	   
            	   dao.cambioCodigoPostal(id, cp, user.getId());
            	   
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarColegio(id, user.getId());
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearColegio(user.getId(), user.getLanguage());  	
            	   
            	   response.setContentType("text/plain");
                   response.setCharacterEncoding("UTF-8"); 
                   response.getWriter().write(Integer.toString(idNueva));
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
