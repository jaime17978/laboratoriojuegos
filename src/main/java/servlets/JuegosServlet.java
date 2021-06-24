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

import dao.AlumnoDAO;
import dao.CategoriaDAO;
import dao.JuegoDAO;
import models.Categoria;
import models.User;

@WebServlet("/juegos")
public class JuegosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JuegosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoriaDAO dao = new CategoriaDAO();
		 
        try {
            
            List<Categoria> listaTipos = dao.tiposActividadBD();
            request.setAttribute("listaTipos", listaTipos);
            
            List<Categoria> listaIdiomas = dao.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("juegos.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CategoriaDAO dao = new CategoriaDAO();
		
		String nombre = request.getParameter("nombre");
		int tipo = Integer.parseInt(request.getParameter("tipos"));
		//int idioma = Integer.parseInt(request.getParameter("idiomas"));
		
		JuegoDAO juegoDAO = new JuegoDAO();
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
		
		try {
			juegoDAO.crearJuegoDB(nombre, user.getId(), tipo, user.getLanguage());
			
			List<Categoria> listaTipos = dao.tiposActividadBD();
            request.setAttribute("listaTipos", listaTipos);
            
            List<Categoria> listaIdiomas = dao.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("juegos.jsp");
            dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			doGet(request,response);
		}
	}

}
