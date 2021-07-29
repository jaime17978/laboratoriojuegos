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
 * Clase servlet que maneja las peticiones de la pagina de seleccion
 * de pais de la pantalla regiones. Esta pagina requiere que se eliga primero
 * el pais para el que editar las regiones. Esto es asi porque hay un gran numero
 * de regiones en la base de datos y mostrar todas en una pagina JSP causa mucho
 * retardo en la pagina, ya que tiene que cargar una fila de la tabla por cada region.
 * 
 * Por ello, la pantalla de regiones funciona de la siguiente manera: primero se muestra un
 * formulario que permite elegir el pais. Una vez elegido el pais se mostrara la
 * tabla de regiones de forma similar al resto de paginas de la aplicacion.
 */
@WebServlet("/regiones_pais")
public class RegionesPaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PaisDAO daoPais = new PaisDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        /**
         * A esta pantalla solo tiene acceso el administrador.
         * Para asegurar esto es necesario comprobar los permisos de los usuarios.
         * Si no tienen permiso para acceder a esta pagina se les redireccionara
         * a la pagina de error.
         */
        if (user.getPermissions() != 1) {
        	request.setAttribute("msg", "No tienes permiso para acceder a esta parte de la aplicacion.");
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
        
		try {
            /**
             * Se obtiene la lista de paises mediante un DAO
             * para mostrar en el formulario.
             */
            List<Pais> listaPaises = daoPais.paisesBD();
            request.setAttribute("listaPaises", listaPaises);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("regiones_pais.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

	/**
	 * Metodo que maneja las peticiones POST.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegionDAO dao = new RegionDAO();
		PaisDAO daoPais = new PaisDAO();
		
        String pais = request.getParameter("regiones_pais");
        
        try {
            /**
             * Una vez elegido el pais, se accede a la base de datos
             * mediante el dao para obtener los datos necesarios para la
             * pagina.
             */
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
