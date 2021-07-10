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

import models.Categoria;

public class AnhoDAO {

	public List<Categoria> anhosBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listaAnhos = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM anhos WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            while (result.next()) {
                int id = result.getInt("pkanho");
                String nombre = result.getString("nombreanho");
                Categoria anho = new Categoria(id, nombre);
                     
                listaAnhos.add(anho);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaAnhos;
    }

	public void cambioNombre(int id, String nombre) throws SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE anhos SET nombreanho=?, fechamodificacion=? WHERE pkanho=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}

	public void borrarAnho(int id) throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE anhos SET fechabaja=? WHERE pkanho=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();
        
	}

	public int crearAnho(int id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id_n = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO anhos (nombreanho, fkusuario, fkidioma, fechaalta) VALUES ('', ?, 1, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        stmt.setTimestamp(2, date);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id_n=rs.getInt(1);
        }
        
        con.close();	

        return id_n;
	}
	
}
