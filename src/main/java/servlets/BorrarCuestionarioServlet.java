package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CuestionarioDAO;
import models.User;

/**
 * Clase servlet de borrado de cuestionarios.
 */
@WebServlet("/b_cuest")
public class BorrarCuestionarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			/**
			 * Obtiene la id del cuestionario (tabla alumnos_juegos) desde
			 * la peticion y se la pasa al DAO para que acceda a la base de
			 * datos y lo borre.
			 */
    		CuestionarioDAO dao = new CuestionarioDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        	
        	int id = Integer.parseInt(request.getParameter("id"));

        	dao.bajaCuestionario(user.getId(), id);
    		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }

}
