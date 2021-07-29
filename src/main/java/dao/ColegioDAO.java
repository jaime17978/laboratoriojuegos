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

import models.Colegio;

/**
 * Clase que contiene los metodos que acceden a la tabla
 * colegios de la base de datos.
 */
public class ColegioDAO {

	/**
	 * Devuelve una lista con todos los colegios (no dados de baja) de la base de datos.
	 * @return Lista de objetos "Colegio".
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Colegio> colegiosBD() throws SQLException, ClassNotFoundException {
        List<Colegio> listaColegios = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM colegios WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Colegio". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkcolegio");
                String nombre = result.getString("nombre");
                String direccion = result.getString("direccion");
                String localidad = result.getString("localidad");
                String region = result.getString("fkregion");
                String tipo = result.getString("tipocolegio");
                int codigo = result.getInt("cp");
                
                Colegio colegio = new Colegio(id, nombre, direccion, localidad, region, tipo, codigo);
                     
                listaColegios.add(colegio);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaColegios;
    }
	
	/**
	 * Cambia el nombre de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar el nombre.
	 * @param nombre Nombre nuevo del colegio.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioNombre(int id, String nombre, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET nombre=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia la direccion de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar la direccion.
	 * @param direccion Direccion nueva del colegio.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioDireccion(int id, String direccion, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET direccion=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, direccion);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia la localidad de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar la localidad.
	 * @param localidad Localidad nueva del colegio.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioLocalidad(int id, String localidad, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET localidad=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, localidad);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia la region de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar la region.
	 * @param region Region nueva del colegio.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioRegion(int id, String region, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.        
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET fkregion=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, region);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el tipo de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar el tipo.
	 * @param tipo Tipo nuevo del colegio.
	 * @param id_user Clave primaria del usuario 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioTipo(int id, String tipo, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET tipocolegio=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, tipo);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Cambia el codigo postal de un colegio en la base de datos.
	 * @param id Clave primaria del colegio al que se le va a cambiar el codigo postal.
	 * @param cp Nuevo codigo postal del colegio.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioCodigoPostal(int id, int cp, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET cp=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setInt(1, cp);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Realiza una baja logica del colegio en la base de datos (añadiendo una fecha a la columna fechabaja).
	 * @param id Clave primaria del colegio que se va a dar de baja.
	 * @param id_user Clave primaria del usuario que va a realizar la baja.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarColegio(int id, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET fechabaja=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id_user);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();	
	}
	
	/**
	 * Crea un nuevo colegio en la base de datos.
	 * @param userId Clave primaria del usuario que va a crear el colegio.
	 * @param userIdioma Foreign key del idioma del usuario.
	 * @return Entero que contiene la clave primaria (ID) del nuevo colegio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int crearColegio(int userId, int userIdioma) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
        //Iniciamos la conexion con la base de datos.
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id = -1;  
		/**
		 * Creamos la consulta e introducimos los datos de los parametros.
		 */
        PreparedStatement stmt = con.prepareStatement("INSERT INTO colegios (nombre, direccion, localidad, fkregion, tipocolegio, fkusuario, fechaalta, fkidioma, cp) VALUES ('', '', '', '??-??', 'publico', ?, ?, ?, '')", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userId);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, userIdioma);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        
        con.close();	

        return id;
	}
}
