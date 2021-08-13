package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Practica;

/**
 * Clase que contiene los metodos que acceden a la tabla "Practicas"
 * de la base de datos.
 */
public class PracticaDAO extends BaseDAO{

	/**
	 * Devuelve el contenido de la tabla "Practicas" de
	 * la base de datos.
	 * @return Lista de objetos "Practica" con los datos de la tabla.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Practica> practicasBD() throws SQLException, ClassNotFoundException {
        List<Practica> listaPracticas = new ArrayList<>();
          
      //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
            
        	String sql = "SELECT * FROM practicas WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Practica". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
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
	
	/**
	 * Cambia el nombre de una practica.
	 * @param id Clave primaria de la practica.
	 * @param nombre Nombre nuevo de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioNombre(int id, String nombre, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET nombreasignatura=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el tipo de actividad de una practica.
	 * @param id Clave primaria de la practica.
	 * @param tipo Tipo nuevo de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioTipo(int id, int tipo, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fktipoactividad=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, tipo);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el colegio de una practica.
	 * @param id Clave primaria de la practica.
	 * @param colegio Colegio nuevo de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioColegio(int id, int colegio, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkcolegio=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, colegio);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el año de una practica.
	 * @param id Clave primaria de la practica.
	 * @param anho Año nuevo de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioAnho(int id, int anho, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkanho=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, anho);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el alumno de una practica
	 * @param id Clave primaria de la practica.
	 * @param alumno Clave primaria del alumno nuevo de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioAlumno(int id, int alumno, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fkalumno=?, fechamodificacion=?, fkusuario=? WHERE pkpractica=?");
        stmt.setInt(1, alumno);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_user);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Realiza una baja logica en la practica especificada.
	 * @param id Clave primaria de la practica.
	 * @param id_user Clave primaria del usuario que va a realizar el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarPractica(int id, int id_user) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
		PreparedStatement stmt = con.prepareStatement("UPDATE practicas SET fechabaja=?, fkusuario=? WHERE pkpractica=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id_user);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();	
	}
	
	/**
	 * Crea una practica nueva con datos vacios.
	 * @param userId Clave primaria del usuario que va a crear la practica.
	 * @param userIdioma Idioma del usuario que va a crear la practica
	 * @return Entero con la clave primaria de la practica. 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int crearPractica(int userId, int userIdioma) throws ClassNotFoundException, SQLException {
		
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = getConnection();
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
