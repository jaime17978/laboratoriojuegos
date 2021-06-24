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

@WebServlet("/cuestionarios")
public class CuestionariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CuestionariosServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
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
    		
    		RequestDispatcher dispatcher = request.getRequestDispatcher("cuestionarios.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
		
	}

}