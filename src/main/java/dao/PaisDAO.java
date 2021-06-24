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

import models.Pais;

public class PaisDAO {

	public List<Pais> paisesBD() throws SQLException, ClassNotFoundException {
        List<Pais> listaPaises = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM paises";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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

	public void cambioNombre(String nombre_a, String nombre) throws SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE paises SET nombrepais=? WHERE nombrepais=?");
        stmt.setString(1, nombre);
        stmt.setString(2, nombre_a);
        stmt.executeUpdate();

        con.close();
		
	}

	public void cambioID(String id, String nombre) throws SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE paises SET pkpais=? WHERE nombrepais=?");
        stmt.setString(1, id);
        stmt.setString(2, nombre);
        stmt.executeUpdate();

        con.close();
		
	}

	public void borrarPais(String nombre) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("DELETE FROM paises WHERE nombrepais=?");
        stmt.setString(1, nombre);
		stmt.executeUpdate();
        con.close();
        
	}

	public void crearPais() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		
		String sql = "INSERT INTO paises (pkpais, nombrepais) VALUES ('', '')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);  
	}

}
