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
import dao.EstadisticasDAO;
import models.Categoria;
import models.ContadorEst;
import models.User;

/**
 * Clase servlet que maneja las peticiones de la pantalla estadisticas.
 */
@WebServlet("/estadisticas")
public class EstadisticasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo que maneja las peticiones GET. Obtiene la lista de los
	 * cursos para los que el usuario ha creado cuestionarios y la muestra
	 * en un formulario.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoriaDAO dao = new CategoriaDAO();
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        try {
            /**
             * Obtiene los cursos para los que el usuario ha creado cuestionarios
             * y redirecciona a la pagina est_menu.
             */
            if(user.getPermissions() != 4) {
            	List<Categoria> listaCursos = dao.cursosEstadisticasBD(user.getId());
                request.setAttribute("listaCursos", listaCursos);
        	}
        	else {
        		List<Categoria> listaCursos = dao.cursosEstadisticasInvBD();
                request.setAttribute("listaCursos", listaCursos);
        	}
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/est_menu.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
		
	}

	/**
	 * Metodo que maneja las peticiones POST. Recibe la peticion que se envia
	 * cuando el usuario elige el curso para el que quiere ver estadisticas,
	 * calcula las estadisticas a traves de los DAO y redirecciona a la pagina
	 * donde se visualizan las tablas con las estadisticas.
	 * @param request Peticion que se ha realizado al servlet.
     * @param response Objeto respuesta.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int curso;
		String titulo;
		EstadisticasDAO dao = new EstadisticasDAO();
		
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getId();
        
        try {
        	//Nombre del curso
        	if (request.getParameter("cuest_curso").equals("all") ) {
    			curso = -1;
    			titulo = "Estadisticas generales (todos los cursos)";
    		}
    		else {
    			curso = Integer.parseInt(request.getParameter("cuest_curso"));
    			titulo = "Estadisticas para "+dao.getNombreCursoBD(curso).toLowerCase();	
    		}
        	request.setAttribute("titulo", titulo);
        	
        	//Borrado de las estadisticas anteriores
        	dao.borrarEstadisticas(user_id);
        	//Genera las estadisticas para el curso especificado (o para todos los cursos si curso == -1)
        	if(user.getPermissions() != 4) {
        		dao.calculoEstadisticas(user_id, curso);
        	}
        	else {
        		dao.calculoEstadisticasInv(user_id, curso, user.getLanguage());
        	}
        	//Obtenemos las estadisticas de la tabla "estadisticas" de la base de datos.

        	//Numero de ni�os y ni�as
        	List<ContadorEst> numAlumnosGenero = dao.numAlumnosGeneroBD(user_id);
        	request.setAttribute("numAlumnosGenero", numAlumnosGenero);
        	
        	//Numero de juegos por alumno
        	List<ContadorEst> juegosPorAlumno = dao.juegosPorAlumnoBD(user_id);
        	request.setAttribute("juegosPorAlumno", juegosPorAlumno);
        	
        	//Numero de juegos mencionados por ni�as
        	List<ContadorEst> juegosPorNinhas = dao.juegosPorNinhasBD(user_id);
        	request.setAttribute("juegosPorNinhas", juegosPorNinhas);
        	
        	//Numero de juegos mencionados por ni�os
        	List<ContadorEst> juegosPorNinhos = dao.juegosPorNinhosBD(user_id);
        	request.setAttribute("juegosPorNinhos", juegosPorNinhos);
        	
        	//Numero de juegos mencionados en colegio o favorito
        	List<ContadorEst> juegosColFav = dao.juegosColFavBD(user_id);
        	request.setAttribute("juegosColFav", juegosColFav);
        	
        	//Numero de juegos mencionados en barrio o favorito
        	List<ContadorEst> juegosBarFav = dao.juegosBarFavBD(user_id);
        	request.setAttribute("juegosBarFav", juegosBarFav);
        	
        	//Numero de juegos mencionados como favorito
        	List<ContadorEst> juegosFav = dao.juegosFavBD(user_id);
        	request.setAttribute("juegosFav", juegosFav);
        	
        	/*------------------------------------------------------------------------*/
        	/* Estadisticas antiguas
            //Frecuencia total de juegos
        	List<ContadorEst> frecuenciaJuegos = dao.frecJuegosCursoBD(user_id, curso);
        	request.setAttribute("frecTotalJuegos", frecuenciaJuegos);
        	
        	//Tipos de juegos en los cursos
        	List<ContadorEst> frecuenciaTipos = dao.frecTiposCursoBD(user_id, curso);
        	request.setAttribute("frecTotalTipos", frecuenciaTipos);
        	
        	//5 juegos m�s mencionados para ni�os
        	List<ContadorEst> frecuenciaNignos = dao.frecGeneroCursoBD(user_id, curso, "ni�o");
        	request.setAttribute("frecNignos", frecuenciaNignos);
        	
        	//5 juegos m�s mencionados para ni�as
        	List<ContadorEst> frecuenciaNignas = dao.frecGeneroCursoBD(user_id, curso, "ni�a");
        	request.setAttribute("frecNignas", frecuenciaNignas);

        	//5 juegos m�s mencionados en colegio
        	List<ContadorEst> frecuenciaColegio = dao.frecColeCursoBD(user_id, curso);
        	request.setAttribute("frecColegio", frecuenciaColegio);
        	
        	//5 juegos m�s mencionados en barrio
        	List<ContadorEst> frecuenciaBarrio = dao.frecBarrioCursoBD(user_id, curso);
        	request.setAttribute("frecBarrio", frecuenciaBarrio);
        	
        	//Juegos favoritos en conjunto
        	List<ContadorEst> frecuenciaFavGen = dao.frecFavGenCursoBD(user_id, curso);
        	request.setAttribute("frecFavGen", frecuenciaFavGen);
        	
        	//Juegos favoritos para ni�os
        	List<ContadorEst> frecuenciaFavNignos = dao.frecFavGeneroCursoBD(user_id, curso, "ni�o");
        	request.setAttribute("frecFavNignos", frecuenciaFavNignos);
        	
        	//Juegos favoritos para ni�as
        	List<ContadorEst> frecuenciaFavNignas = dao.frecFavGeneroCursoBD(user_id, curso, "ni�a");
        	request.setAttribute("frecFavNignas", frecuenciaFavNignas);

        	//Juegos favoritos en funci�n del lugar de juego (colegio, barrio, colegio y barrio o ninguno).
        	
        	//Favoritos y colegio (incluye a los que tienen colegio y barrio?)
        	List<ContadorEst> frecuenciaFavCol = dao.frecFavColCursoBD(user_id, curso);
        	request.setAttribute("frecFavCol", frecuenciaFavCol);
        	
        	//Favoritos y barrio (incluye a los que tienen colegio y barrio?)
        	List<ContadorEst> frecuenciaFavBarrio = dao.frecFavBarrioCursoBD(user_id, curso);
        	request.setAttribute("frecFavBarrio", frecuenciaFavBarrio);
        	
        	//Favoritos, colegio y barrio
        	List<ContadorEst> frecuenciaFavColBar = dao.frecFavColBarCursoBD(user_id, curso);
        	request.setAttribute("frecFavColBar", frecuenciaFavColBar);*/
        	
        	
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/estadisticas.jsp");
            dispatcher.forward(request, response);
 
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

}
