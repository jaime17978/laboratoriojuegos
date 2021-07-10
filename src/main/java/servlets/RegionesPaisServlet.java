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

import dao.PaisDAO;
import dao.RegionDAO;
import models.Pais;
import models.Region;
import models.User;

/**
 * Servlet implementation class RegionesPaisServlet
 */
@WebServlet("/regiones_pais")
public class RegionesPaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PaisDAO daoPais = new PaisDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            
            List<Pais> listaPaises = daoPais.paisesBD();
            request.setAttribute("listaPaises", listaPaises);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("regiones_pais.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegionDAO dao = new RegionDAO();
		PaisDAO daoPais = new PaisDAO();
		
        String pais = request.getParameter("regiones_pais");
        
        try {
            
        	List<Region> listaRegiones = dao.regionesBD(pais);
            request.setAttribute("listaRegiones", listaRegiones);
            
            List<Pais> listaPaises = daoPais.paisesBD();
            request.setAttribute("listaPaises", listaPaises);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("regiones.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

}
