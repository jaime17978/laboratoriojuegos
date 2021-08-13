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

import dao.CategoriaDAO;
import dao.JuegoDAO;
import models.Categoria;
import models.Juego;
import models.User;

import com.google.gson.Gson;

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
		
		JuegoDAO dao = new JuegoDAO();
		CategoriaDAO daoCat = new CategoriaDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int sim; 
        
        try {
        	//Si es un profesor se comprueba si esta simulando a un usuario.
        	//Si lo esta simulando se muestran sus juegos, si no, se le envia a la pagina de error.
        	if (user.getPermissions() == 2) {
        		sim = user.getSimulado();
        		if (sim != -1) {
        			List<Juego> listaJuegos = dao.juegosUsuarioBD(sim);
                    request.setAttribute("listaJuegos", listaJuegos);
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/juegos_profesor.jsp");
                    dispatcher.forward(request, response);
                    return;
        		}
        		else {
        			request.setAttribute("msg", "No estas simulando a ningun usuario. Para hacerlo, debes acceder a la pestaña 'usuarios'.");
    	            
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/error.jsp");
    	            dispatcher.forward(request, response);
    	            return;
        		}
            }
        	if (user.getPermissions() == 4) {
        		
        		List<Categoria> listaCategorias = daoCat.juegosInvBD();
                request.setAttribute("listaJuegosInv", listaCategorias);

                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/juegos_investigador.jsp");
	            dispatcher.forward(request, response);
	            return;
            }
        	
            /**
             * Obtiene los juegos de la base de datos mediante el DAO.
             */
            List<Juego> listaJuegos = dao.juegosUsuarioBD(user.getId());
            request.setAttribute("listaJuegos", listaJuegos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/juegos.jsp");
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
		
		JuegoDAO dao = new JuegoDAO();
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String option = request.getParameter("option");
        String nombre;
        int id;
        int idNueva;
        try {
        	/**
        	 * En funcion del valor del parametro "option" este metodo
        	 * realizara diferentes cambios en la tabla de años (Ej: cambiar
        	 * el nombre, borrar o crear).
        	 */
        	switch(option) {
               case "c_nombre":
            	   nombre = request.getParameter("nombre");
            	   id = Integer.parseInt(request.getParameter("id"));
            	   
            	   dao.cambioNombre(id, nombre);
            	   break;
            	
               case "delete":
            	   id = Integer.parseInt(request.getParameter("id"));
            	   dao.borrarJuego(id);
            	   
            	   break;
            	   
               case "create":
            	   idNueva = dao.crearJuego(user.getId(), user.getLanguage());  	
            	   response.setContentType("text/plain");
                   response.setCharacterEncoding("UTF-8"); 
                   response.getWriter().write(Integer.toString(idNueva));
            	   break;
               
               case "sus":
            	   Gson gson = new Gson(); 
            	   nombre = request.getParameter("nombre");	   
            	   String[] ids = gson.fromJson(request.getParameter("juegos"), String[].class);  
            	   
            	   dao.sustituirJuego(nombre, ids, user.getId(), user.getLanguage());
            	   
            	   break;
               default : 
            	  return;
            }
        	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
	}

}
