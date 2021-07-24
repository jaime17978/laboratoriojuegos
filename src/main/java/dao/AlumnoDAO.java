package dao;

import java.sql.Connection;
import java.sql.Date;
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
import models.User;

public class AlumnoDAO {
	
	public String crearAlumnoDB(int usuarioKey, String nombre, int curso, String genero, int edad, int idioma, int clave) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		int id = 0;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("INSERT INTO alumnos (nombrealumno, genero, fkusuario, edad, fkidioma, fechaalta, CODIGO_antiguo) VALUES (?, ?, ?, ?, ?, ?, 0)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nombre);
        stmt.setString(2, genero);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, edad);
        stmt.setInt(5, idioma);
        stmt.setTimestamp(6, date);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        
        con.close();	

        return Integer.toString(id);
	}
	
	public List<Alumno> alumnosUsuarioBD(int usuarioKey) throws SQLException, ClassNotFoundException {
        List<Alumno> listAlumno = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos WHERE fkusuario = ? AND fechabaja IS NULL ORDER BY pkalumno DESC");
            stmt.setInt(1, usuarioKey);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                int id = result.getInt("pkalumno");
                String nombre = result.getString("nombrealumno");
                int edad = result.getInt("edad");
                //Reemplazar cuando este en la bd
                int curso = result.getInt("fkcurso");
                String genero = result.getString("genero");
                int idioma = result.getInt("fkidioma");
                Alumno alumno = new Alumno(id, nombre, curso, genero, edad, idioma);
                     
                listAlumno.add(alumno);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listAlumno;
    }
	
	public List<Alumno> alumnosUsuarioCursoBD(int usuarioKey, int curso_c) throws SQLException, ClassNotFoundException {
        List<Alumno> listAlumno = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos WHERE fkusuario = ? AND fkcurso = ? AND fechabaja IS NULL ORDER BY pkalumno DESC");
            stmt.setInt(1, usuarioKey);
            stmt.setInt(2, curso_c);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                int id = result.getInt("pkalumno");
                String nombre = result.getString("nombrealumno");
                int edad = result.getInt("edad");
                //Reemplazar cuando este en la bd
                int curso = result.getInt("fkcurso");
                String genero = result.getString("genero");
                int idioma = result.getInt("fkidioma");
                Alumno alumno = new Alumno(id, nombre, curso, genero, edad, idioma);
                     
                listAlumno.add(alumno);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listAlumno;
    }
	
	public void modificarAlumnoNombre(int usuarioKey, int id, String nombre) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET nombrealumno=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setString(1, nombre);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	public void modificarAlumnoGenero(int usuarioKey, int id, String genero) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET genero=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setString(1, genero);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	public void modificarAlumnoEdad(int usuarioKey, int id, int edad) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET edad=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
		
        stmt.setInt(1, edad);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        System.out.println(stmt);
        stmt.executeUpdate();

        con.close();
	}
	
	public void modificarAlumnoIdioma(int usuarioKey, int id, int idioma) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkidioma=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setInt(1, idioma);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	public void modificarAlumnoCurso(int usuarioKey, int id, int curso) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkcurso=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setInt(1, curso);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	public void bajaAlumno(int usuarioKey, int id) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkusuario=?, fechabaja=? WHERE pkalumno=?");
        stmt.setInt(1, usuarioKey);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
	}
	
}