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

import dao.RegionDAO;
import dao.UniversidadDAO;
import models.Region;
import models.Universidad;
import models.User;

@WebServlet("/universidades")
public class UniversidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UniversidadesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UniversidadDAO daoUni = new UniversidadDAO();
		RegionDAO daoRegion = new RegionDAO();
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
            
            List<Region> listaRegiones = daoRegion.regionesBD();
            request.setAttribute("listaRegiones", listaRegiones);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("universidades.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UniversidadDAO dao = new UniversidadDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String valor;
        int id;
        int idNueva;
        
        try {
        	
        	switch(option) {
               case "c_nombre":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("nombre");
            	   
            	   dao.cambioNombre(id, valor);
            	   
            	   break;
            	   
               case "c_direccion":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("direccion");
            	   
            	   dao.cambioDireccion(id, valor);
            	   
            	   break;
            	   
               case "c_localidad":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("localidad");
            	   
            	   dao.cambioLocalidad(id, valor);
            	   
            	   break;
            	   
               case "c_region":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   valor = request.getParameter("region");
            	   
            	   dao.cambioRegion(id, valor);
            	   
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarUniversidad(id);
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearUniversidad(user.getId(), user.getLanguage());  	
            	   
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
