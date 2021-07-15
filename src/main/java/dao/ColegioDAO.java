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
import models.Universidad;

public class ColegioDAO {

	public List<Colegio> colegiosBD() throws SQLException, ClassNotFoundException {
        List<Colegio> listaColegios = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM colegios WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
	
	public void cambioNombre(int id, String nombre, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET nombre=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioDireccion(int id, String direccion, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET direccion=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, direccion);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioLocalidad(int id, String localidad, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET localidad=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, localidad);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioRegion(int id, String region, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET fkregion=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, region);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioTipo(int id, String tipo, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET tipocolegio=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setString(1, tipo);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}

	public void cambioCodigoPostal(int id, int cp, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET cp=?, fechamodificacion=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setInt(1, cp);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}

	public void borrarColegio(int id, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE colegios SET fechabaja=?, fkusuario=? WHERE pkcolegio=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id_user);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();	
	}
	
	public int crearColegio(int userId, int userIdioma) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id = -1;  
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
