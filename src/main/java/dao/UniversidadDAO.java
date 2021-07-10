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

import models.Universidad;

public class UniversidadDAO {

	public List<Universidad> universidadesBD() throws SQLException, ClassNotFoundException {
        List<Universidad> listaUnis = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM universidades WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            while (result.next()) {
                int id = result.getInt("pkuniversidad");
                String nombre = result.getString("nombreuniversidad");
                String direccion = result.getString("direccion");
                String localidad = result.getString("localidad");
                String region = result.getString("fkregion");
                
                Universidad uni = new Universidad(id, nombre, direccion, localidad, region);
                     
                listaUnis.add(uni);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaUnis;
    }
	
	public void cambioNombre(int id, String nombre) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE universidades SET nombreuniversidad=?, fechamodificacion=? WHERE pkuniversidad=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioDireccion(int id, String direccion) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE universidades SET direccion=?, fechamodificacion=? WHERE pkuniversidad=?");
        stmt.setString(1, direccion);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioLocalidad(int id, String localidad) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE universidades SET localidad=?, fechamodificacion=? WHERE pkuniversidad=?");
        stmt.setString(1, localidad);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioRegion(int id, String region) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE universidades SET fkregion=?, fechamodificacion=? WHERE pkuniversidad=?");
        stmt.setString(1, region);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void borrarUniversidad(int id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE universidades SET fechabaja=? WHERE pkuniversidad=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();	
	}
	
	public int crearUniversidad(int userId, int userIdioma) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO universidades (nombreuniversidad, localidad, fkusuario, fechaalta, fkidioma, fkregion) VALUES ('', '', ?, ?, ?, '??-??')", Statement.RETURN_GENERATED_KEYS);
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
