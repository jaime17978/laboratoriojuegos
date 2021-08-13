package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CuestionarioDAO;
import models.User;

/**
 * Clase servlet que maneja las peticiones POST de la pagina de cuestionarios.
 */
@WebServlet("/cuestionarios")
public class CuestionariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CuestionariosServlet() {
        super();
    }

    /**
	 * Metodo que maneja las peticiones POST que se envian al marcar/desmarcar
	 * checkboxes en los cuestionarios.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Mediante el DAO de cuestionarios se accede a la base de datos
         * para crear/modificar el cuestionario que corresponde.
         */
		try {
        	CuestionarioDAO dao = new CuestionarioDAO();
    		HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
    		int idAlumno = Integer.parseInt(request.getParameter("idAlumno"));
    		int idJuego = Integer.parseInt(request.getParameter("idJuego"));
    		boolean favorito = Boolean.parseBoolean(request.getParameter("fav"));
    		boolean barrio = Boolean.parseBoolean(request.getParameter("bar"));
    		boolean colegio = Boolean.parseBoolean(request.getParameter("col"));
            
    		dao.creaModificaCuest(user.getId(), idAlumno, idJuego, favorito, barrio, colegio, user.getLanguage());
    		
    		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/cuestionarios.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
		
	}

}