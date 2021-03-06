package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.AlumnoDAO;
import dao.CategoriaDAO;
import dao.CuestionarioDAO;
import dao.JuegoDAO;
import models.Alumno;
import models.Categoria;
import models.Cuestionario;
import models.Juego;
import models.User;

/**
 * Clase servlet que maneja las peticiones de la pagina de seleccion de curso
 * de la pantalla de cuestionarios.
 * 
 * Antes de que un usuario pueda acceder a los cuestionarios debe seleccionar el curso
 * para el que quiere crear o modificar los cuestionarios.
 * 
 * Es importante tener en cuenta que para que un curso aparezca como opcion posible para
 * crear/modificar cuestionarios, primero se debe crear un alumno en ese curso.
 */
@WebServlet("/cuest_curso")
public class CuestionariosAgnoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo que maneja las peticiones GET.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriaDAO dao = new CategoriaDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            /**
             * Se obtiene la lista de cursos mediante el DAO y
             * se le pasa a la pagina correspondiente.
             */
            List<Categoria> listaCursos = dao.cursosCuestBD(user);
            request.setAttribute("listaCursos", listaCursos);
            
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/cuest_curso.jsp");
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
		
		int curso = Integer.parseInt(request.getParameter("cuest_curso"));
		
		CategoriaDAO daoTipos = new CategoriaDAO();
		AlumnoDAO daoAlumnos = new AlumnoDAO();
		JuegoDAO daoJuegos = new JuegoDAO();
		CuestionarioDAO daoCuest = new CuestionarioDAO(); 
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
        	/**
        	 * Se obtienen los datos necesarios de la base de datos mediante
        	 * los DAO.
        	 */
            List<Alumno> listaAlumnos = daoAlumnos.alumnosUsuarioCursoBD(user.getId(), curso);
            request.setAttribute("listaAlumnos", listaAlumnos);
            
            List<Juego> listaJuegos = daoJuegos.juegosBD();
            request.setAttribute("listaJuegos", listaJuegos);
            
            List<Categoria> listaTipos = daoTipos.tiposActividadBD();
            request.setAttribute("listaTipos", listaTipos);
            
            
            List<String> listaNombres = new ArrayList<String>();
            for (Juego j: listaJuegos) {
            	listaNombres.add(j.getName());
            }
            
            /**
             * Para poder utilizar la funcion de autocompletar
             * al escribir el nombre de un juego en el cuestionario
             * es necesario pasar la lista de nombres de juegos a 
             * JSON para que la pagina (cuestionarios.jsp) la pueda utilizar.
             */
            String json = new Gson().toJson(listaNombres);
            request.setAttribute("listaNombres", json);
            
            List<Cuestionario> listaCuest = daoCuest.cuestionariosBD(user.getId(), curso);
            request.setAttribute("listaCuestionarios", listaCuest);
            
            List<Juego> listaJuegosCuest = new ArrayList<Juego>();
            for (Cuestionario c : listaCuest) {
            	Juego jAux = new Juego(c.getIdJuego(),c.getNombreJuego(),1);
            	listaJuegosCuest.add(jAux);
            }
            
            Set<Juego> setJuegos = new HashSet<Juego>(listaJuegosCuest);
            
            request.setAttribute("setJuegos", setJuegos);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/cuestionarios.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

}
