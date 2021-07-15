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
import dao.UniversidadDAO;
import dao.UserDAO;
import models.Categoria;
import models.Universidad;
import models.User;


@WebServlet("/usuarios")
public class UsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UniversidadDAO daoUni = new UniversidadDAO();
		UserDAO daoUsuarios = new UserDAO();
		CategoriaDAO daoIdiomas = new CategoriaDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
        try {
            
            List<Universidad> listaUnis = daoUni.universidadesBD();
            request.setAttribute("listaUniversidades", listaUnis);
            
            List<Categoria> listaIdiomas = daoIdiomas.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            List<Categoria> listaPerfiles = daoIdiomas.perfilesBD();
            request.setAttribute("listaPerfiles", listaPerfiles);
            
            List<User> listaUsuarios = daoUsuarios.usuariosBD();
            request.setAttribute("listaUsuarios", listaUsuarios);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("usuarios.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}


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
            	   System.out.println("EEEOOOOO");
            	   idNueva = dao.crearUsuario(user.getId());  	
            	   System.out.println(idNueva);
            	   response.setContentType("text/plain");
                   response.setCharacterEncoding("UTF-8"); 
                   response.getWriter().write(Integer.toString(idNueva));
            	   break;
               
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
			return;
		}
        
	}

}
