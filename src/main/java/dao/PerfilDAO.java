package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Perfil;

/**
 * Clase que contiene los metodos que acceden a la tabla
 * perfiles de la base de datos.
 */
public class PerfilDAO {

	/**
	 * Devuelve los contenidos de la tabla perfiles de la base de datos.
	 * @return Lista de objetos "Perfil" con los distintos perfiles de la tabla de la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Perfil> perfilesBD() throws SQLException, ClassNotFoundException {
        List<Perfil> listaPerfiles = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM perfiles";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Perfil". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkperfil");
                String nombre = result.getString("nombreperfil");
                boolean activo = result.getBoolean("activo");
                Perfil perfil = new Perfil(id, nombre, activo);
                     
                listaPerfiles.add(perfil);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaPerfiles;
    }
	
	/**
	 * Cambia el nombre de un perfil.
	 * @param id Clave primaria del perfil.
	 * @param nombre Nombre nuevo del perfil.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioNombre(int id, String nombre) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE perfiles SET nombreperfil=? WHERE pkperfil=?");
        stmt.setString(1, nombre);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Activa o desactiva un perfil.
	 * @param id Clave primaria del perfil al que se le cambia el estado de activo.
	 * @param activo Nuevo estado de activo (true/false).
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioActivo(int id, boolean activo) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE perfiles SET activo=? WHERE pkperfil=?");
        stmt.setBoolean(1, activo);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Borra un perfil de la base de datos. Al ser una tabla de
	 * administrador el borrado es permanente. No hay baja logica.
	 * @param id Clave primaria del perfil a eliminar.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void borrarPerfil(int id) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("DELETE FROM perfiles WHERE pkperfil=?");
        stmt.setInt(1, id);
		stmt.executeUpdate();
        con.close();
	}
	
	/**
	 * Crea un perfil nuevo en la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int crearPerfil() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		
		int id = -1;
		
		String sql = "INSERT INTO perfiles (nombreperfil, activo) VALUES ('', false)";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql); 
		
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        
        con.close();	

        return id;
	}
	
}
