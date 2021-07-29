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

/**
 * Clase que maneja las peticiones enviadas a la url de la pagina de juegos.
 */
@WebServlet("/juegos")
public class JuegosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * Constructor de la clase JuegosServlet.
	 */
    public JuegosServlet() {
        super();
    }

    /**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoriaDAO dao = new CategoriaDAO();
		 
        try {
            /**
             * Obtiene los tipos de actividad e idiomas que necesita para el formulario
             * a traves de los DAO y luego redirecciona a la pagina de juegos.
             */
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

	/**
	 * Metodo que maneja las peticiones POST. En este caso crea un juego con los valores
	 * introducidos por el usuario en el formulario de creacion de juego.
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
		/**
		 * Crea el juego con los parametros del formulario y redirecciona de nuevo a la pagina de juegos.
		 */
		try {
			juegoDAO.crearJuegoDB(nombre, user.getId(), tipo, user.getLanguage());
			
			List<Categoria> listaTipos = dao.tiposActividadBD();
            request.setAttribute("listaTipos", listaTipos);
            
            List<Categoria> listaIdiomas = dao.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            request.setAttribute("message", "El juego ha sido creado correctamente.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("juegos.jsp");
            dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			doGet(request,response);
		}
	}

}
