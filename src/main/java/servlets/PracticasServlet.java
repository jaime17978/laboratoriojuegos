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
import dao.CategoriaDAO;
import dao.ColegioDAO;
import dao.PermisosDAO;
import dao.PracticaDAO;
import dao.UserDAO;
import models.Categoria;
import models.Colegio;
import models.Practica;
import models.User;
/**
 * Servlet de manejo de las peticiones enviadas a la url de practicas.
 */
@WebServlet("/practicas")
public class PracticasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PracticaDAO daoPractica = new PracticaDAO();
		CategoriaDAO daoCategoria = new CategoriaDAO();
		ColegioDAO daoColegio = new ColegioDAO();
		UserDAO daoUsuario = new UserDAO();
		AnhoDAO daoAnho = new AnhoDAO();
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
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 6)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	        }
        	
            /**
             * Mediante los diferentes DAO se accede a la base de datos para
             * obtener las practicas, colegios, tipos de actividad y a?os que hacen
             * falta para utilizarse en la pagina.
             */
        	List<Practica> listaPracticas = daoPractica.practicasBD();
            request.setAttribute("listaPracticas", listaPracticas);
        	
            List<Colegio> listaColegios = daoColegio.colegiosBD();
            request.setAttribute("listaColegios", listaColegios);
            
            List<Categoria> listaTiposAct = daoCategoria.tiposActividadBD();
            request.setAttribute("listaTiposAct", listaTiposAct);
            
            List<User> listaUsuarios = daoUsuario.usuariosBD();
            request.setAttribute("listaUsuarios", listaUsuarios);
            
            List<Categoria> listaAnhos = daoAnho.anhosBD();
            request.setAttribute("listaAnhos", listaAnhos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/practicas.jsp");
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
		PracticaDAO dao = new PracticaDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        int idNueva;
        int id;
        String nombre;
        int fk;
        
        try {
        	/**
        	 * En funcion de los parametros de la peticion se
        	 * realizaran unos cambios u otros (Ej: cambiar el nombre,
        	 * cambiar el colegio, borrar, crear, etc.).
        	 */
        	switch(option) {
               case "c_nombre":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   nombre = request.getParameter("nombre");
            	   
            	   dao.cambioNombre(id, nombre, user.getId());
            	   
            	   break;
            	   
               case "c_tipo":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   fk = Integer.parseInt(request.getParameter("tipo"));
            	   
            	   dao.cambioTipo(id, fk, user.getId());
            	   
            	   break;
            	
               case "c_colegio":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   fk = Integer.parseInt(request.getParameter("colegio"));
            	   
            	   dao.cambioColegio(id, fk, user.getId());
            	   
            	   break;
            	   
               case "c_anho":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   fk = Integer.parseInt(request.getParameter("anho"));
            	   
            	   dao.cambioAnho(id, fk, user.getId());
            	   
            	   break;
            	   
               case "c_alumno":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   fk = Integer.parseInt(request.getParameter("alumno"));
            	   
            	   dao.cambioAlumno(id, fk, user.getId());
            	   
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarPractica(id, user.getId());
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearPractica(user.getId(), user.getLanguage());  	
            	   
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
