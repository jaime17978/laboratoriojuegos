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

import com.google.gson.Gson;

import dao.CategoriaDAO;
import dao.JuegoDAO;
import models.Categoria;
import models.Juego;
import models.User;

/**
 * Clase servlet que maneja las peticiones POST de creacion
 * de un juego que se envian desde la pagina de cuestionarios.
 */
@WebServlet("/c_juegoCuest")
public class CreaJuegoCuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoriaDAO dao = new CategoriaDAO();
				
		String nombre = request.getParameter("nombre");
		int tipo = Integer.parseInt(request.getParameter("tipos"));
		
		JuegoDAO juegoDAO = new JuegoDAO();
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
		
		try {
			Juego j = juegoDAO.crearJuegoCuestDB(nombre, user.getId(), tipo, user.getLanguage());
			if (j == null) {
				return;
			}
			
			 String json = new Gson().toJson(j);
			 response.setContentType("application/json");
			 response.setCharacterEncoding("UTF-8");
			 response.getWriter().write(json);
			 
			return;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
            throw new ServletException(e);
		}		
	}

}
