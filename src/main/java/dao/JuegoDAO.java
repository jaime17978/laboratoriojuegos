package dao;

import java.sql.Connection;
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
public class JuegoDAO extends BaseDAO{

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
		
        Connection con;
        /**
		 * Creamos la consulta e introducimos los datos de los parametros. 
		 */
		con = getConnection();
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
		
        Connection con;
		con = getConnection();
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
        	int tipo = result.getInt("fktipoactividad");
        	Juego j = new Juego(idJ, nom, tipo);
        	
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
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
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
                int tipo = result.getInt("fktipoactividad");
                Juego game = new Juego(id, nombre, tipo);
                     
                listGames.add(game);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listGames;
    }
	
	/**
	 * Devuelve todos los juegos no dados de baja de un usuario de la base de datos.
	 * @param u ID del usuario.
	 * @return Lista de objetos "Juego" con los datos de los juegos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Juego> juegosUsuarioBD(int u) throws SQLException, ClassNotFoundException {
        List<Juego> listGames = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
   
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM juegos WHERE fechabaja IS NULL AND fkusuario = ?");
            stmt.setInt(1, u);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Juego". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                int tipo = result.getInt("fktipoactividad");
                Juego game = new Juego(id, nombre, tipo);
                     
                listGames.add(game);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listGames;
    }
	
	/**
	 * Devuelve los juegos buscados por nombre.
	 * @param n Nombre por el que buscar los juegos.
	 * @return Lista de objetos "Juego" con los datos de los juegos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Juego> juegosNombreBD(String n) throws SQLException, ClassNotFoundException {
        List<Juego> listGames = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
   
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM juegos WHERE fechabaja IS NULL AND nombrejuego like ?");
            stmt.setString(1, "%"+n+"%");
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Juego". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                int tipo = result.getInt("fktipoactividad");
                Juego game = new Juego(id, nombre, tipo);
                     
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
		
		
		//Iniciamos la conexion con la base de datos. 
        try (Connection connection = getConnection()) {
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
                int tipo = result.getInt("fktipoactividad");
                game = new Juego(id, nombre, tipo);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return game;
	}
	
	/**
	 * Cambia el nombre de un juego en la base de datos.
	 * @param id Clave primaria del juego a modificar.
	 * @param nombre Nuevo nombre del juego.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cambioNombre(int id, String nombre) throws SQLException, ClassNotFoundException {
		
		//Iniciamos la conexion con la base de datos.
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		//Creamos y ejecutamos la consulta.
		PreparedStatement stmt = con.prepareStatement("UPDATE juegos SET nombrejuego=?, fechamodificacion=? WHERE pkjuego = ?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Crea un juego en la base de datos y devuelve su id.
	 * @param id Clave primaria del usuario que crea el juego.
	 * @return La clave primaria del nuevo juego creado.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int crearJuego(int id, int idioma) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		int id_n = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos (nombrejuego, fkusuario, fktipoactividad, fechaalta, fkidioma) VALUES ('', ?, 1, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, idioma);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id_n=rs.getInt(1);
        }
        
        con.close();	
        
        return id_n;
	}
	
	/**
	 * Realiza una baja logica de un año (Anho) en la base de datos.
	 * @param id Clave primaria del año a dar de baja
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarJuego(int id) throws ClassNotFoundException, SQLException {
        
		//Iniciamos la conexion con la base de datos
        
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		/**
		 * Creamos y ejecutamos la consulta.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE juegos SET fechabaja=? WHERE pkjuego=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();
        
	}

	/**
	 * Realiza la sustitucion del nombre de un juego en la tabla juegos_investigador
	 * creando una fila en la tabla juegos_investigador_cambiados en el proceso.
	 * @param id Clave primaria del año a dar de baja
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void sustituirJuego(String nombre, String[] ids, int id, int idioma) throws ClassNotFoundException, SQLException {
		
		//Iniciamos la conexion con la base de datos
        
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        int id_juego;
		con = getConnection();
		
		for (String id_j : ids) {
			id_juego = Integer.parseInt(id_j);
			PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos_investigador_cambiados (nombre_juego, fkjuego, fkusuario, fkidioma, fechaalta) VALUES (?,?,?,?,?)");
	        stmt.setString(1, nombre);
	        stmt.setInt(2, id_juego);
	        stmt.setInt(3, id);
	        stmt.setInt(4, idioma);
			stmt.setTimestamp(5, date);
	        
	        stmt.executeUpdate();
	        
	        PreparedStatement stmt2 = con.prepareStatement("UPDATE juegos_investigador SET nombrejuego=? WHERE pkjuego=?");
	        stmt2.setString(1, nombre);
	        stmt2.setInt(2, id_juego);
	        
	        stmt2.executeUpdate();
		}

        con.close();
	}
}