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
import dao.PermisosDAO;
import dao.UniversidadDAO;
import dao.UserDAO;
import models.Categoria;
import models.Universidad;
import models.User;

/**
 * Clase servlet que maneja las peticiones a la url de usuarios.
 */
@WebServlet("/usuarios")
public class UsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UniversidadDAO daoUni = new UniversidadDAO();
		UserDAO daoUsuarios = new UserDAO();
		PermisosDAO daoPermisos = new PermisosDAO();
		CategoriaDAO daoIdiomas = new CategoriaDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int idSim;
        
        try {
        	/**
	         * Al ser una pagina a la que solo puede acceder un administrador
	         * o desarrollador hay que comprobar los permisos del usuario.
	         * Si el usuario no tiene permisos para entrar se le redirecciona
	         * a la pagina de error. 
	         */
	        if (!daoPermisos.verificarPermisos(user.getPermissions(), 5)) {
	        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
	            
	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
	            dispatcher.forward(request, response);
	        }
	        
        	/**
        	 * Mediante los diferentes DAO, se obtiene la informacion de universidades,
        	 * idiomas, perfiles y usuarios que necesitamos para mostrar en la pagina.
        	 */
            List<Universidad> listaUnis = daoUni.universidadesBD();
            request.setAttribute("listaUniversidades", listaUnis);
            
            List<Categoria> listaIdiomas = daoIdiomas.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            List<Categoria> listaPerfiles = daoIdiomas.perfilesBD();
            request.setAttribute("listaPerfiles", listaPerfiles);
            
            if (user.getPermissions() == 2) {
                List<User> listaUsuarios = daoUsuarios.usuariosBD(user.getId());
                request.setAttribute("listaUsuarios", listaUsuarios);
            	
                idSim = user.getSimulado();
                if (idSim != -1) {
                	request.setAttribute("idSimulado", idSim);
                }
                
            	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/usuarios_profesor.jsp");
                dispatcher.forward(request, response);
	        }
            else {
            	List<User> listaUsuarios = daoUsuarios.usuariosBD();
                request.setAttribute("listaUsuarios", listaUsuarios);

            	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/usuarios.jsp");
                dispatcher.forward(request, response);
            }
            
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
		UserDAO dao = new UserDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String email;
        int valor;
        boolean activo;
        int id;
        int idNueva;
        
        try {
        	/**
        	 * En el javascript de la pagina de usuarios se envia un parametro llamado "option".
        	 * Este parametro determina que accion debe tomar esta funcion (Ej: modificar el idioma,
        	 * borrar al usuario, etc.).
        	 * 
        	 * Los cambios en la base de datos se realizan mediante los DAO.
        	 */
        	switch(option) {
               case "c_email":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   email = request.getParameter("email");
            	   
            	   dao.cambioEmail(id, email, user.getId());
            	   
            	   break;
            	   
               case "c_perfil":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = Integer.parseInt(request.getParameter("perfil"));
            	   
            	   dao.cambioPerfil(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_idioma":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = Integer.parseInt(request.getParameter("idioma"));
            	   
            	   dao.cambioIdioma(id, valor, user.getId());
            	   
            	   break;
            	   
               case "c_uni":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = Integer.parseInt(request.getParameter("universidad"));
            	   
            	   dao.cambioUniversidad(id, valor, user.getId());
            	   
            	   break;
            	
               case "c_activo":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   activo = Boolean.parseBoolean(request.getParameter("activo"));
            	   
            	   dao.cambioActivo(id, activo, user.getId());
            	   
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarUsuario(id, user.getId());
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearUsuario(user.getId());  	
            	   
            	   response.setContentType("text/plain");
                   response.setCharacterEncoding("UTF-8"); 
                   response.getWriter().write(Integer.toString(idNueva));
            	   break;
               
               case "simulate":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   user.setSimulado(id);
            	   session.setAttribute("user", user);
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
