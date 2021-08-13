package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.JuegoDAO;
import models.Juego;

/**
 * Clase servlet utilizada en la pantalla de cuestionarios.
 */
@WebServlet("/g_juego")
public class JuegoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public JuegoServlet() {
        super();
    }
    
    /**
	 * Metodo que maneja las peticiones POST. Esta funcion busca un juego en
	 * la base de datos por su nombre. Si existe, escribe su ID en la respuesta.
	 * Si no existe, escribe 0 en la respuesta.
	 * 
	 * La pagina cuestionarios tiene el siguiente funcionamiento: Cuando se crea una
	 * fila nueva en la pagina y se introduce el nombre de un juego, este se pasa a esta funcion
	 * mediante una peticion post (utilizando AJAX). Luego, la pagina evalua la respuesta.
	 * Si la ID que se ha escrito en la respuesta es de un juego que no existe, se preguntara
	 * al usuario si quiere crearlo, abriendo el formulario de creacion de juegos.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			
    		JuegoDAO dao = new JuegoDAO();
        	
        	String nombre = request.getParameter("nombre");

        	Juego juego = dao.juegoBD(nombre);
    		
        	response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8"); 
            
            if (juego != null) {
            	response.getWriter().write(Integer.toString(juego.getId()));
            }
            else {
            	response.getWriter().write("0");
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
    	 
    }
}