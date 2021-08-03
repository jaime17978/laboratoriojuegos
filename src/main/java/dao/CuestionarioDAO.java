package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Cuestionario;

/**
 * Clase que contiene los metodos que acceden a la tabla de cuestionarios
 * (alumnos_juegos) de la base de datos.
 */
public class CuestionarioDAO {
	
	/**
	 * Devuelve todos los cuestionarios de un usuario para un curso determinado.
	 * @param id Clave primaria del usuario para el que se obtienen los cuestionarios.
	 * @param curso Curso con el que filtrar la consulta de cuestionarios.
	 * @return Lista de objetos "Cuestionario" con los cuestionarios obtenidos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Cuestionario> cuestionariosBD(int id, int curso) throws ClassNotFoundException, SQLException {
		 List<Cuestionario> listCuest = new ArrayList<Cuestionario>();
	        Class.forName("com.mysql.cj.jdbc.Driver");  
	        //Iniciamos la conexion con la base de datos.
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
	            
	        	/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("SELECT fkalumno, a.fkusuario, fkjuego, nombrejuego, favorito, barrio, colegio "
	            		+ " FROM alumnos_juegos as a join juegos as b WHERE a.fkusuario = ? AND a.fkcurso = ? AND a.fechabaja IS NULL AND b.fechabaja IS NULL AND a.fkjuego = b.pkjuego");
	            stmt.setInt(1, id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "Cuestionario". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
	            while (result.next()) {
	            	int idA = result.getInt("fkAlumno");
	            	int idU = result.getInt("fkUsuario");
	            	int idJ = result.getInt("fkJuego");
	            	String nom = result.getString("nombrejuego");
	            	boolean fav = result.getBoolean("favorito");
	            	boolean bar = result.getBoolean("barrio");
	            	boolean col = result.getBoolean("colegio");
	            	
	            	Cuestionario cuest = new Cuestionario(idA, idU, idJ, nom, fav, bar, col);
	            	listCuest.add(cuest);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }      
	         
	        return listCuest;
	}

	/**
	 * Busca en la tabla de cuestionarios (alumnos_juegos) un cuestionario con el usuario,
	 * alumno y juego pasados por parametro. Si existe, le asigna los valores de favorito, barrio
	 * y colegio pasados por parametro. Si no existe, utiliza todos los valores de los argumentos
	 * y lo crea con dichos valores.
	 * @param id Clave primaria del usuario que va a realizar la modificacion/insercion en la tabla.
	 * @param idAlumno Clave primaria del alumno del cuestionario.
	 * @param idJuego Clave primaria del juego del cuestionario.
	 * @param favorito Valor booleano que indica si el juego se ha marcado como favorito.
	 * @param barrio Valor booleano que indica si el juego se ha marcado como jugado en el barrio.
	 * @param colegio Valor booleano que indica si el juego se ha marcado como jugado en casa.
	 * @param idioma Foreign key del idioma del usuario.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void creaModificaCuest(int id, int idAlumno, int idJuego, boolean favorito, boolean barrio, boolean colegio, int idioma) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");  
		//Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	/**
    		 * Creamos la consulta de busqueda de cuestionarios e introducimos los datos de los parametros.
    		 */
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos_juegos WHERE fkusuario = ? AND fkalumno = ? AND fkjuego = ? AND fechabaja IS NULL");
            stmt.setInt(1, id);
            stmt.setInt(2, idAlumno);
            stmt.setInt(3, idJuego);
            ResultSet result = stmt.executeQuery();
            Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            
            //Si existe el cuestionario buscado, se accede a este y se modifica.
            if (result.next()) {
            	PreparedStatement stmtUpdate = connection.prepareStatement("UPDATE alumnos_juegos SET favorito = ?, barrio = ?, colegio = ?, fechamodificacion = ? WHERE fkusuario = ? AND fkalumno = ? AND fkjuego = ? AND fechabaja IS NULL");
            	stmtUpdate.setBoolean(1, favorito);
            	stmtUpdate.setBoolean(2, barrio);
            	stmtUpdate.setBoolean(3, colegio);
            	stmtUpdate.setTimestamp(4, date);
            	stmtUpdate.setInt(5, id);
            	stmtUpdate.setInt(6, idAlumno);
            	stmtUpdate.setInt(7, idJuego);
            	System.out.println(stmtUpdate);
                stmtUpdate.executeUpdate();
            }
            
            /**
             * Si no existe el cuestionario buscado, se accede a las tablas de usuarios y alumnos
             * para obtener los datos necesarios para crear el nuevo cuestionario.
             */
            else {
            	//Consulta para obtener los datos del alumno (fkcurso)
            	PreparedStatement stmtAlumno = connection.prepareStatement("SELECT * FROM alumnos WHERE pkalumno = ? AND fechabaja IS NULL");
            	stmtAlumno.setInt(1, idAlumno);            	
                ResultSet resultAlumno = stmtAlumno.executeQuery();
                int fkcurso;
                if (resultAlumno.next() != false) {
                	fkcurso = resultAlumno.getInt("fkcurso");
                }
                else {
                	//Excepcion aqui
                	System.out.println("No existe alumno");
                	return;
                }
                
              //Consulta para obtener los datos del usuario (anho, colegio);
                int fkanho;
                int fkcolegio;
            	PreparedStatement stmtUsuario = connection.prepareStatement("SELECT * FROM practicas WHERE fkalumno = ? AND fechabaja IS NULL");
            	stmtUsuario.setInt(1, id);            	
                ResultSet resultUsuario = stmtUsuario.executeQuery();
                if (resultUsuario.next() != false) {
                	fkanho = resultUsuario.getInt("fkanho");
                	fkcolegio = resultUsuario.getInt("fkcolegio"); 
                }
                else {
                	//Excepcion aqui
                	System.out.println("No existe usuario");
                	return;
                }
                
                //Con todos los datos necesario, creamos el cuestionario.
                PreparedStatement stmtCreate = connection.prepareStatement("INSERT INTO alumnos_juegos (fkalumno, fkcolegio, fkcurso, fkanho, fkjuego, favorito, barrio, colegio, "
                		+ "fkusuario, fkidioma, fechaalta) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
               
                stmtCreate.setInt(1, idAlumno);
                stmtCreate.setInt(2, fkcolegio);
                stmtCreate.setInt(3, fkcurso);
                stmtCreate.setInt(4, fkanho);
                stmtCreate.setInt(5, idJuego);
                stmtCreate.setBoolean(6, favorito);
                stmtCreate.setBoolean(7, barrio);
                stmtCreate.setBoolean(8, colegio);
                stmtCreate.setInt(9, id);
                stmtCreate.setInt(10, idioma);
                stmtCreate.setTimestamp(11, date);
                System.out.println(stmtCreate);
                stmtCreate.executeUpdate();
            }
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
	}
	
	/**
	 * Da de baja a todos los cuestionarios para un usuario y juego especificos. 
	 * @param usuarioKey Clave primaria del usuario que realiza las bajas.
	 * @param id Clave primaria del juego por el que filtrar los cuestionarios a dar de baja.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void bajaCuestionario(int usuarioKey, int id) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos.
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos_juegos SET fechabaja=? WHERE fkusuario=? AND fkjuego=? AND fechabaja IS NULL");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, usuarioKey);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
	}
}
