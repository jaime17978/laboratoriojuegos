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
import models.Practica;

public class PracticaDAO {

	public List<Practica> practicasBD() throws SQLException, ClassNotFoundException {
        List<Practica> listaPracticas = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
        	String sql = "SELECT * FROM practicas WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            while (result.next()) {
                int id = result.getInt("pkpractica");
                String nombre = result.getString("nombreasignatura");
                int tipo = result.getInt("fktipoactividad");
                int colegio = result.getInt("fkcolegio");
                int anho = result.getInt("fkanho");
                int alumno = result.getInt("fkalumno");
                
                Practica practica = new Practica(id, nombre, tipo, colegio, anho, alumno);
                     
                listaPracticas.add(practica);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaPracticas;
    }
	
	public void cambioNombre(int id, String nombre, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET nombreasignatura=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioTipo(int id, int tipo, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fktipoactividad=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, tipo);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioColegio(int id, int colegio, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkcolegio=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, colegio);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioAnho(int id, int anho, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkanho=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, anho);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	public void cambioAlumno(int id, int alumno, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkalumno=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, alumno);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}

	public void borrarPractica(int id, int id_user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fechabaja=?, fkusuario=? WHERE pkpractica=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id_user);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();	
	}
	
	public int crearPractica(int userId, int userIdioma) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO practicas (nombreasignatura, fktipoactividad, fkusuario, fechaalta, fkidioma, fkcolegio, fkanho, fkalumno) "
        		+ "VALUES ('', 6, ?, ?, ?, 1, 1, 1)", Statement.RETURN_GENERATED_KEYS);
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
