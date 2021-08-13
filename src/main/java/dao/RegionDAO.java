package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Region;

/**
 * Clase con los metodos que acceden a la
 * tabla "Regiones" de la base de datos.
 */
public class RegionDAO extends BaseDAO{
	
	/**
	 * Devuelve los contenidos de la tabla regiones en una lista de objetos "Region".
	 * @return Lista de objetos "Region" con el contenido de la tabla.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Region> regionesBD() throws SQLException, ClassNotFoundException {
        List<Region> listaRegiones = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
            
        	String sql = "SELECT * FROM regiones";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Guardamos los resultados de la consulta en objetos "Region"
             * que se guardan en una lista. Esta lista se devuelve al servlet.
             */
            while (result.next()) {
                String id = result.getString("pkregion");
                String nombre = result.getString("nombreregion");
                String pais =  result.getString("fkpais");
                
                Region region = new Region(id, nombre, pais);
                     
                listaRegiones.add(region);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaRegiones;
    }

	/**
	 * Cambia la ID de una region.
	 * @param id_a Clave primaria de la region.
	 * @param id Clave primaria nueva.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cambioID(String id_a, String id) throws SQLException, ClassNotFoundException {
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET pkregion=? WHERE pkregion=?");
        stmt.setString(1, id);
        stmt.setString(2, id_a);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Cambia el nombre de una region.
	 * @param id Clave primaria de la region.
	 * @param nombre Nombre nuevo de la region.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioNombre(String id, String nombre) throws ClassNotFoundException, SQLException {
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET nombreregion=? WHERE pkregion=?");
        stmt.setString(1, nombre);
        stmt.setString(2, id);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Crea una region nueva en la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void crearRegion() throws SQLException, ClassNotFoundException {
		
        Connection con;
		con = getConnection();
		
		String sql = "INSERT INTO regiones (pkregion, nombreregion, fkpais) VALUES ('', '', '??')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql); 
		
	}

	/**
	 * Borra una region de la base de datos. Al ser una tabla de
	 * administrador el borrado es permanente. No hay baja logica.
	 * @param id Clave primaria de la region a eliminar.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void borrarRegion(String id) throws SQLException, ClassNotFoundException {
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM regiones WHERE pkregion=?");
        stmt.setString(1, id);
		stmt.executeUpdate();
        con.close();
	}

	/**
	 * Cambia el pais de una region.
	 * @param id Clave primaria de la region.
	 * @param pais Pais nuevo de la region.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioPais(String id, String pais) throws ClassNotFoundException, SQLException {
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET fkpais=? WHERE pkregion=?");
        stmt.setString(1, pais);
        stmt.setString(2, id);
        stmt.executeUpdate();

        con.close();
	}

	/**
	 * Devuelve las regiones de la tabla para un pais determinado.
	 * @param p Nombre del pais con el que filtrar la consulta.
	 * @return Lista de objetos "Region".
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Region> regionesBD(String p) throws ClassNotFoundException, SQLException {
		List<Region> listaRegiones = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
            /**
             * Creamos la consulta y le pasamos el nombre del pais pasado en el parametro "p".
             */
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM regiones WHERE fkpais = ?");
            stmt.setString(1, p);
            ResultSet result = stmt.executeQuery();
            /**
             * Creamos objetos "Region" con los resultados de la consulta.
             * Estos objetos se guardan en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                String id = result.getString("pkregion");
                String nombre = result.getString("nombreregion");
                String pais =  result.getString("fkpais");
                
                Region region = new Region(id, nombre, pais);
                     
                listaRegiones.add(region);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaRegiones;
	}
	
}
