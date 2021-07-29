package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Juego;

/**
 * Clase que contiene los metodos que acceden a la tabla juegos
 * de la base de datos.
 */
public class JuegoDAO {

	/**
	 * Crea un juego nuevo en la base de datos.
	 * @param nombre Nombre del juego nuevo.
	 * @param usuarioKey Clave primaria del usuario que va a crear el juego.
	 * @param tipoKey Foreign key del tipo de actividad del juego nuevo.
	 * @param idiomaKey Foreign key del idioma del juego nuevo.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void crearJuegoDB(String nombre, int usuarioKey, int tipoKey, int idiomaKey) throws SQLException, ClassNotFoundException {
        
        Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        //Iniciamos la conexion con la base de datos.
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
        /**
		 * Creamos la consulta e introducimos los datos de los parametros. 
		 */
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos (nombrejuego, fktipoactividad, fkusuario, fkidioma, fechaalta) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setInt(2, tipoKey);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, idiomaKey);
        stmt.setTimestamp(5, date);
        stmt.executeUpdate();

        con.close();

	}
	
	/**
	 * Crea un juego nuevo, devolviendo un objeto "Juego" con la
	 * informacion del juego creado.
	 * @param nombre Nombre del juego nuevo.
	 * @param usuarioKey Clave primaria del usuario que va a crear el juego.
	 * @param tipoKey Foreign key del tipo de actividad del juego nuevo.
	 * @param idiomaKey Foreign key del idioma del juego nuevo.
	 * @return Objeto "Juego" con la informacion del juego creado en la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Juego crearJuegoCuestDB(String nombre, int usuarioKey, int tipoKey, int idiomaKey) throws SQLException, ClassNotFoundException {
        
        Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
      //Iniciamos la conexion con la base de datos.
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos (nombrejuego, fktipoactividad, fkusuario, fkidioma, fechaalta) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setInt(2, tipoKey);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, idiomaKey);
        stmt.setTimestamp(5, date);
        stmt.executeUpdate();
        
        /**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 * Esta consulta devuelve el juego creado con el insert anterior.
		 */
        PreparedStatement stmtGet = con.prepareStatement("SELECT * FROM juegos WHERE fkusuario=? AND nombrejuego=? AND fechabaja IS NULL");
        stmtGet.setInt(1, usuarioKey);
        stmtGet.setString(2, nombre);
        ResultSet result = stmtGet.executeQuery();
        
        /**
         * Obtenemos los resultados de la consulta y los guardamos en un objeto
         * "Juego" que se devuelve al servlet.
         */
        if (result.next()) {
        	int idJ = result.getInt("pkJuego");
        	String nom = result.getString("nombrejuego");
        	Juego j = new Juego(idJ, nom);
        	
        	return j;
        }          

        con.close();

        return null;
	}
	
	/**
	 * Devuelve todos los juegos no dados de baja de la base de datos.
	 * @return Lista de objetos "Juego" con los datos de los juegos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Juego> juegosBD() throws SQLException, ClassNotFoundException {
        List<Juego> listGames = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM juegos WHERE fechabaja IS NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Juego". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                Juego game = new Juego(id, nombre);
                     
                listGames.add(game);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listGames;
    }
	
	/**
	 * Busca un juego en la base de datos.
	 * @param n Nombre del juego a buscar en la tabla.
	 * @return Objeto "Juego" con los datos del juego buscado.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Juego juegoBD(String n)throws SQLException, ClassNotFoundException {
		Juego game = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Iniciamos la conexion con la base de datos. 
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	/**
    		 * Creamos la consulta e introducimos los datos de los parametros.
    		 */
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM juegos WHERE nombrejuego = ? AND fechabaja IS NULL");
            stmt.setString(1, n);
            ResultSet result = stmt.executeQuery();
            /**
             * Obtenemos los resultados de la consulta y los guardamos en un objeto "Juego".
             */
            if (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                game = new Juego(id, nombre);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return game;
	}
}