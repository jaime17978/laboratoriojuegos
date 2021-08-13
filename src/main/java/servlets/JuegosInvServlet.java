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

import dao.CategoriaDAO;
import dao.JuegoDAO;
import models.Categoria;
import models.Juego;

/**
 * Clase que maneja las peticiones de busqueda de juegos de la pantalla
 * de juegos del investigador.
 */
@WebServlet("/juegos_inv")
public class JuegosInvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * Constructor de la clase JuegosInvServlet.
	 */
    public JuegosInvServlet() {
        super();
    }

	/**
	 * Metodo que maneja las peticiones POST. En este caso crea un juego con los valores
	 * introducidos por el usuario en el formulario de creacion de juego.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JuegoDAO dao = new JuegoDAO();
		CategoriaDAO daoCat = new CategoriaDAO();
		String nombre = request.getParameter("busc_juegos");

        try { 
        	List<Juego> listaJuegos = dao.juegosNombreBD(nombre);
            request.setAttribute("listaJuegos", listaJuegos);
            
            List<Categoria> listaCategorias = daoCat.juegosInvBD();
            request.setAttribute("listaJuegosInv", listaCategorias);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/juegos_investigador.jsp");
            dispatcher.forward(request, response);
        }
        catch (ClassNotFoundException | SQLException e){
        	return;
        }
	}

}
