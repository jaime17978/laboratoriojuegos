package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.PerfilMenu;

/**
 * Clase que contiene los metodos que acceden a la tabla
 * perfiles_menus de la base de datos.
 */
public class PerfilMenuDAO extends BaseDAO{

	/**
	 * Devuelve los contenidos de la tabla perfiles de la base de datos.
	 * @return Lista de objetos "Perfil" con los distintos perfiles de la tabla de la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<PerfilMenu> perfilesMenusBD() throws SQLException, ClassNotFoundException {
        List<PerfilMenu> listaPerfilesMenus = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
            
        	String sql = "SELECT * FROM perfiles_menus";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "PerfilMenu". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int perfil = result.getInt("fkperfil");
                int menu = result.getInt("fkmenu");
                String nombre = result.getString("aux1");
               
                PerfilMenu perfilMenu = new PerfilMenu(perfil, menu, nombre);
                     
                listaPerfilesMenus.add(perfilMenu);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaPerfilesMenus;
    }

	public void cambioNombre(int perfil, int menu, String nombre) throws ClassNotFoundException, SQLException {
		
		
		try (Connection connection = getConnection()) {

			PreparedStatement stmt = connection.prepareStatement("UPDATE perfiles_menus SET aux1 = ? WHERE fkperfil = ? AND fkmenu = ?");
			stmt.setString(1, nombre);
			stmt.setInt(2, perfil);
	        stmt.setInt(3, menu);
	        stmt.executeUpdate();
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }  

	}

	public void borrarPerfilMenu(int perfil, int menu) throws SQLException, ClassNotFoundException {
		
		
		try (Connection connection = getConnection()) {

			PreparedStatement stmt = connection.prepareStatement("DELETE from perfiles_menus WHERE fkperfil = ? AND fkmenu = ?");
			stmt.setInt(1, perfil);
	        stmt.setInt(2, menu);
	        stmt.executeUpdate();
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } 
	}

	public boolean crearPerfilMenu(int perfil, int menu, String nombre) throws ClassNotFoundException, SQLException {
          
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM perfiles_menus WHERE fkperfil = ? and fkmenu = ?"
            		);
            stmt.setInt(1, perfil);
            stmt.setInt(2, menu);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return false;
            }             
            else {
        		PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO perfiles_menus (fkperfil, fkmenu, aux1) VALUES (?, ?, ?)");
        		stmt2.setInt(1, perfil);
                stmt2.setInt(2, menu);
                stmt2.setString(3, nombre);
                stmt2.executeUpdate();
                return true;
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }      
		
	}
	
}
