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
import dao.PracticaDAO;
import dao.RegionDAO;
import dao.UserDAO;
import models.Categoria;
import models.Colegio;
import models.Practica;
import models.Region;
import models.User;

@WebServlet("/practicas")
public class PracticasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PracticaDAO daoPractica = new PracticaDAO();
		CategoriaDAO daoCategoria = new CategoriaDAO();
		ColegioDAO daoColegio = new ColegioDAO();
		UserDAO daoUsuario = new UserDAO();
		AnhoDAO daoAnho = new AnhoDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
        try {
            
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
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("practicas.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	} 


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
			System.out.print(e);
			return;
		}
        
	}

}
