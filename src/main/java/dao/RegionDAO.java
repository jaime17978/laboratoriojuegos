package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Region;

public class RegionDAO {

	public List<Region> regionesBD() throws SQLException, ClassNotFoundException {
        List<Region> listaRegiones = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM regiones LIMIT 100";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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

	public void cambioID(String id_a, String id) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET pkregion=? WHERE pkregion=?");
        stmt.setString(1, id);
        stmt.setString(2, id_a);
        stmt.executeUpdate();

        con.close();
		
	}

	public void cambioNombre(String id, String nombre) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET nombreregion=? WHERE pkregion=?");
        stmt.setString(1, nombre);
        stmt.setString(2, id);
        stmt.executeUpdate();

        con.close();
		
	}

	public void crearRegion() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		
		String sql = "INSERT INTO regiones (pkregion, nombreregion, fkpais) VALUES ('', '', '??')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql); 
		
	}

	public void borrarRegion(String id) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("DELETE FROM regiones WHERE pkregion=?");
        stmt.setString(1, id);
		stmt.executeUpdate();
        con.close();
	}

	public void cambioPais(String id, String pais) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE regiones SET fkpais=? WHERE pkregion=?");
        stmt.setString(1, pais);
        stmt.setString(2, id);
        stmt.executeUpdate();

        con.close();
	}
	
}
