package servlets;

/**
 * Clase servlet que maneja la peticion GET de la pagina de alumnos.
 */
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
import models.Categoria;
import models.User;
import models.Alumno;

@WebServlet("/alumnos")
public class AlumnosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Constructor de la clase AlumnosServlet.
	 */
    public AlumnosServlet() {
        super();
    }
    
    /**
     * Metodo que maneja las peticiones get que se envian a la url del servlet.
     * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoriaDAO dao = new CategoriaDAO();
		AlumnoDAO daoAlumnos = new AlumnoDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int sim;
        
        try {
        	
        	//Si es un profesor se comprueba si esta simulando a un usuario.
        	//Si lo esta simulando se muestran sus alumnos, si no, se le envia a la pagina de error.
        	if (user.getPermissions() == 2) {
        		sim = user.getSimulado();
        		if (sim != -1) {
        			
        			List<Categoria> listaCursos = dao.cursosBD();
                    request.setAttribute("listaCursos", listaCursos);
                    
                    List<Alumno> listaAlumnos = daoAlumnos.alumnosUsuarioBD(sim);
                    request.setAttribute("listaAlumnos", listaAlumnos);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/alumnos_profesor.jsp");
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
            
        	/*
        	 * Mediante los DAO de categoria y alumnos accedemos a la base de datos,
        	 * guardando los datos obtenidos en objetos de las clases del paquete models,
        	 * que luego se pasan a la pagina JSP mediante request.setAttribute para acceder
        	 * a los datos desde la pagina.
        	 */
            List<Categoria> listaCursos = dao.cursosBD();
            request.setAttribute("listaCursos", listaCursos);
            
            List<Alumno> listaAlumnos = daoAlumnos.alumnosUsuarioBD(user.getId());
            request.setAttribute("listaAlumnos", listaAlumnos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/alumnos.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoriaDAO dao = new CategoriaDAO();
		
		String nombre = request.getParameter("nombre");
		String genero = request.getParameter("genero");
		int edad = Integer.parseInt(request.getParameter("edad"));
		int curso = Integer.parseInt(request.getParameter("cursos"));
		int idioma = Integer.parseInt(request.getParameter("idiomas"));
		
		AlumnoDAO alumno = new AlumnoDAO();
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
		
		try {
			alumno.crearAlumnoDB(user.getId(), nombre, curso, genero, edad, idioma, 0);
			
			List<Categoria> listaCursos = dao.cursosBD();
            request.setAttribute("listaCursos", listaCursos);
            
            List<Categoria> listaIdiomas = dao.idiomasBD();
            request.setAttribute("listaIdiomas", listaIdiomas);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("alumnos.jsp");
            dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			doGet(request,response);
		}
		
	}*/

}
