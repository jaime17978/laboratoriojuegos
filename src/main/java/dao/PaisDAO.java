package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Pais;

/**
 * Clase que contiene los metodos que acceden a la tabla
 * paises de la base de datos.
 */
public class PaisDAO extends BaseDAO{
	
	/**
	 * Devuelve los contenidos de la tabla paises de la base de datos.
	 * @return Lista de objetos "Pais" con los distintos paises de la tabla de la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Pais> paisesBD() throws SQLException, ClassNotFoundException {
        List<Pais> listaPaises = new ArrayList<>();
          
      //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
            
        	String sql = "SELECT * FROM paises";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Pais". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                String id = result.getString("pkpais");
                String nombre = result.getString("nombrepais");
                Pais pais = new Pais(id, nombre);
                     
                listaPaises.add(pais);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaPaises;
    }
	
	/**
	 * Modifica el nombre de un pais en la base de datos.
	 * @param nombre_a Nombre antiguo del pais (la clave primaria de la tabla pais es el nombre).
	 * @param nombre Nombre nuevo del pais.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cambioNombre(String nombre_a, String nombre) throws SQLException, ClassNotFoundException {
		
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE paises SET nombrepais=? WHERE nombrepais=?");
        stmt.setString(1, nombre);
        stmt.setString(2, nombre_a);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Cambia la ID del pais.
	 * @param id Id nueva del pais.
	 * @param nombre Nombre del pais.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cambioID(String id, String nombre) throws SQLException, ClassNotFoundException {
		
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE paises SET pkpais=? WHERE nombrepais=?");
        stmt.setString(1, id);
        stmt.setString(2, nombre);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Borra el pais de la tabla correspondiente. A diferencia de otras tablas
	 * en esta el borrado es permanente y no una baja logica. Esto es asi porque a esta tabla
	 * solo tiene acceso el administrador.
	 * @param nombre
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarPais(String nombre) throws ClassNotFoundException, SQLException {
		
		
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM paises WHERE nombrepais=?");
        stmt.setString(1, nombre);
		stmt.executeUpdate();
        con.close();
        
	}
	
	/**
	 * Crea un pais en la base de datos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void crearPais() throws ClassNotFoundException, SQLException {
		
        Connection con;
		con = getConnection();
		
		String sql = "INSERT INTO paises (pkpais, nombrepais) VALUES ('', '')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);  
	}

}
