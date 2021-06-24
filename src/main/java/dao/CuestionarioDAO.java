package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Alumno;
import models.Cuestionario;

public class CuestionarioDAO {
	
	public List<Cuestionario> cuestionariosBD(int id, int curso) throws ClassNotFoundException, SQLException {
		 List<Cuestionario> listCuest = new ArrayList<Cuestionario>();
	        Class.forName("com.mysql.cj.jdbc.Driver");  
	        
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
	            //SELECT fkalumno, a.fkusuario, fkjuego, nombrejuego, favorito, barrio, colegio 
	        	//FROM admin_juegos.alumnos_juegos as a join admin_juegos.juegos as b where a.fkjuego = b.pkjuego;
	            PreparedStatement stmt = connection.prepareStatement("SELECT fkalumno, a.fkusuario, fkjuego, nombrejuego, favorito, barrio, colegio "
	            		+ " FROM alumnos_juegos as a join juegos as b WHERE a.fkusuario = ? AND a.fkcurso = ? AND a.fechabaja IS NULL AND b.fechabaja IS NULL AND a.fkjuego = b.pkjuego");
	            stmt.setInt(1, id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	            	int idA = result.getInt("fkAlumno");
	            	int idU = result.getInt("fkUsuario");
	            	int idJ = result.getInt("fkJuego");
	            	String nom = result.getString("nombrejuego");
	            	boolean fav = result.getBoolean("favorito");
	            	boolean bar = result.getBoolean("barrio");
	            	boolean col = result.getBoolean("colegio");
	            	
	            	Cuestionario cuest = new Cuestionario(idA, idU, idJ, nom, fav, bar, col);
	            	listCuest.add(cuest);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }      
	         
	        return listCuest;
	}

	public void creaModificaCuest(int id, int idAlumno, int idJuego, boolean favorito, boolean barrio, boolean colegio, int idioma) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");  
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos_juegos WHERE fkusuario = ? AND fkalumno = ? AND fkjuego = ? AND fechabaja IS NULL");
            stmt.setInt(1, id);
            stmt.setInt(2, idAlumno);
            stmt.setInt(3, idJuego);
            ResultSet result = stmt.executeQuery();
            Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            if (result.next()) {
            	//update admin_juegos.practicas set pkpractica = 1000 where nombreasignatura = "Asignatura prueba";
            	PreparedStatement stmtUpdate = connection.prepareStatement("UPDATE alumnos_juegos SET favorito = ?, barrio = ?, colegio = ?, fechamodificacion = ? WHERE fkusuario = ? AND fkalumno = ? AND fkjuego = ? AND fechabaja IS NULL");
            	stmtUpdate.setBoolean(1, favorito);
            	stmtUpdate.setBoolean(2, barrio);
            	stmtUpdate.setBoolean(3, colegio);
            	stmtUpdate.setTimestamp(4, date);
            	stmtUpdate.setInt(5, id);
            	stmtUpdate.setInt(6, idAlumno);
            	stmtUpdate.setInt(7, idJuego);
                stmtUpdate.executeUpdate();
            }
            
            else {
            	//Query para obtener los datos del alumno (fkcurso)
            	PreparedStatement stmtAlumno = connection.prepareStatement("SELECT * FROM alumnos WHERE pkalumno = ? AND fechabaja IS NULL");
            	stmtAlumno.setInt(1, idAlumno);            	
                ResultSet resultAlumno = stmtAlumno.executeQuery();
                int fkcurso;
                if (resultAlumno.next() != false) {
                	fkcurso = resultAlumno.getInt("fkcurso");
                }
                else {
                	//Excepcion aqui
                	System.out.println("No existe alumno");
                	return;
                }
                
              //Query para obtener los datos del usuario (anho, colegio);
                int fkanho;
                int fkcolegio;
            	PreparedStatement stmtUsuario = connection.prepareStatement("SELECT * FROM practicas WHERE fkalumno = ? AND fechabaja IS NULL");
            	stmtUsuario.setInt(1, id);            	
                ResultSet resultUsuario = stmtUsuario.executeQuery();
                if (resultUsuario.next() != false) {
                	fkanho = resultUsuario.getInt("fkanho");
                	fkcolegio = resultUsuario.getInt("fkcolegio"); 
                }
                else {
                	//Excepcion aqui
                	System.out.println("No existe usuario");
                	return;
                }
                
                //Con todos los datos necesario, creamos el cuestionario.
                //INSERT INTO admin_juegos.practicas (nombreasignatura, fktipoactividad, fkusuario, fkidioma, fechaalta, fkcolegio, fkanho, fkalumno) values (1000, "Asignatura prueba", 1, 34, 1, 2021-06-02, 1, 52,34);
                PreparedStatement stmtCreate = connection.prepareStatement("INSERT INTO alumnos_juegos (fkalumno, fkcolegio, fkcurso, fkanho, fkjuego, favorito, barrio, colegio, "
                		+ "fkusuario, fkidioma, fechaalta) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
               
                stmtCreate.setInt(1, idAlumno);
                stmtCreate.setInt(2, fkcolegio);
                stmtCreate.setInt(3, fkcurso);
                stmtCreate.setInt(4, fkanho);
                stmtCreate.setInt(5, idJuego);
                stmtCreate.setBoolean(6, favorito);
                stmtCreate.setBoolean(7, barrio);
                stmtCreate.setBoolean(8, colegio);
                stmtCreate.setInt(9, id);
                stmtCreate.setInt(10, idioma);
                stmtCreate.setTimestamp(11, date);
                
                stmtCreate.executeUpdate();
            }
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
	}
	
	public void bajaCuestionario(int usuarioKey, int id) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos_juegos SET fechabaja=? WHERE fkusuario=? AND fkjuego=? AND fechabaja IS NULL");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, usuarioKey);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
	}
}
