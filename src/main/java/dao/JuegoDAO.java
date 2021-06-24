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

import models.Alumno;
import models.Categoria;
import models.Cuestionario;
import models.Juego;


public class JuegoDAO {

	
	public void crearJuegoDB(String nombre, int usuarioKey, int tipoKey, int idiomaKey) throws SQLException, ClassNotFoundException {
        
        Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos (nombrejuego, fktipoactividad, fkusuario, fkidioma, fechaalta) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setInt(2, tipoKey);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, idiomaKey);
        stmt.setTimestamp(5, date);
        stmt.executeUpdate();

        con.close();

	}
	
	public Juego crearJuegoCuestDB(String nombre, int usuarioKey, int tipoKey, int idiomaKey) throws SQLException, ClassNotFoundException {
        
        Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("INSERT INTO juegos (nombrejuego, fktipoactividad, fkusuario, fkidioma, fechaalta) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setInt(2, tipoKey);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, idiomaKey);
        stmt.setTimestamp(5, date);
        stmt.executeUpdate();
        
        PreparedStatement stmtGet = con.prepareStatement("SELECT * FROM juegos WHERE fkusuario=? AND nombrejuego=? AND fechabaja IS NULL");
        stmtGet.setInt(1, usuarioKey);
        stmtGet.setString(2, nombre);
        ResultSet result = stmtGet.executeQuery();
        
        if (result.next()) {
        	int idJ = result.getInt("pkJuego");
        	String nom = result.getString("nombrejuego");
        	Juego j = new Juego(idJ, nom);
        	
        	return j;
        }          

        con.close();

        return null;
	}
	
	public List<Juego> juegosBD() throws SQLException, ClassNotFoundException {
        List<Juego> listGames = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM juegos WHERE fechabaja IS NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            while (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                Juego game = new Juego(id, nombre);
                     
                listGames.add(game);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listGames;
    }
	
	public Juego juegoBD(String n)throws SQLException, ClassNotFoundException {
		Juego game = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");  
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM juegos WHERE nombrejuego = ? AND fechabaja IS NULL");
            stmt.setString(1, n);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                int id = result.getInt("pkjuego");
                String nombre = result.getString("nombrejuego");
                game = new Juego(id, nombre);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return game;
	}
}