package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.Alumno;

/**
 * Clase que contiene los metodos que acceden a la tabla de 
 * alumnos de la base de datos.
 */
public class AlumnoDAO extends BaseDAO {
	
	/**
	 * Crea un alumno en la tabla de alumnos con los valores que 
	 * se le pasan en los parametros.
	 * @param usuarioKey Clave primaria en la base de datos del usuario que crea el alumno.
	 * @param nombre Nombre del nuevo alumno.
	 * @param curso Foreign key del curso del nuevo alumno.
	 * @param genero Genero del nuevo alumno (puede tener los valores "niño" o "niña").
	 * @param edad Edad del nuevo alumno.
	 * @param idioma Foreign key del idioma del nuevo alumno.
	 * @param clave Clave antigua de la tabla alumnos (en la aplicacion no tiene uso y siempre se crean los alumnos con este valor a 0).
	 * @return La id del alumno creado en la base de datos. 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String crearAlumnoDB(int usuarioKey, String nombre, int curso, String genero, int edad, int idioma, int clave) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		int id = 0;
		
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Preparamos la consulta y le pasamos los valores de los parametros
		PreparedStatement stmt = con.prepareStatement("INSERT INTO alumnos (nombrealumno, genero, fkusuario, edad, fkidioma, fechaalta, CODIGO_antiguo) VALUES (?, ?, ?, ?, ?, ?, 0)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nombre);
        stmt.setString(2, genero);
        stmt.setInt(3, usuarioKey);
        stmt.setInt(4, edad);
        stmt.setInt(5, idioma);
        stmt.setTimestamp(6, date);
        stmt.executeUpdate();

        //Si se ha generado el alumno correctamente obtenemos su id para devolversela al servlet.
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        
        con.close();	

        return Integer.toString(id);
	}
	
	/**
	 * Accede a la tabla alumnos de la base de datos y devuelve todos los alumnos creados por un usuario.
	 * @param usuarioKey La clave primaria del usuario de la base de datos para el que queremos buscar los alumnos.
	 * @return Lista de objetos Alumno que contienen la informacion de los alumnos introducidos en la base de datos por el usuario. 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Alumno> alumnosUsuarioBD(int usuarioKey) throws SQLException, ClassNotFoundException {
        List<Alumno> listAlumno = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos
        try (Connection connection = getConnection()) {
            
        	//Preparamos la consulta e introducimos el valor de la clave primaria del usuario en ella.
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos WHERE fkusuario = ? AND fechabaja IS NULL ORDER BY pkalumno DESC");
            stmt.setInt(1, usuarioKey);
            ResultSet result = stmt.executeQuery();
            /** 
             * Para cada uno de los alumnos obtenidos en la consulta, extraemos los valores de las 
             * columnas correspondientes y creamos un objeto Alumno con ellos. Estos objetos se guardan
             * en una lista para devolverse al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkalumno");
                String nombre = result.getString("nombrealumno");
                int edad = result.getInt("edad");
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
	
	/**
	 * Accede a la tabla alumnos de la base de datos y devuelve todos los alumnos creados por un usuario para un curso determinado.
	 * @param usuarioKey La clave primaria del usuario de la base de datos para el que queremos buscar los alumnos.
	 * @param curso_c La foreign key del curso en el que queremos buscar los alumnos.
	 * @return Lista de objetos Alumno que contienen la informacion de los alumnos introducidos en la base de datos por el usuario para el curso especificado.
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public List<Alumno> alumnosUsuarioCursoBD(int usuarioKey, int curso_c) throws SQLException, ClassNotFoundException {
        List<Alumno> listAlumno = new ArrayList<>();
          
        //Iniciamos la conexion con la base de datos
        try (Connection connection = getConnection()) {
            
        	//Preparamos la consulta e introducimos los valores de los parametros de la funcion en esta.
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM alumnos WHERE fkusuario = ? AND fkcurso = ? AND fechabaja IS NULL ORDER BY pkalumno DESC");
            stmt.setInt(1, usuarioKey);
            stmt.setInt(2, curso_c);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Por cada uno de los alumnos que devuelve la consulta extraemos los valores
             * de las columnas correspondientes y creamos un objeto alumno, el cual se guarda
             * en una lista para devolver al servlet. 
             */
            while (result.next()) {
                int id = result.getInt("pkalumno");
                String nombre = result.getString("nombrealumno");
                int edad = result.getInt("edad");
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
	
	/**
	 * Modifica el nombre de un alumno en la base de datos.
	 * @param usuarioKey Clave primaria del usuario que va a realizar la modificacion.
	 * @param id Clave primaria del alumno al que se le va a cambiar el nombre.
	 * @param nombre Nombre nuevo del alumno.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarAlumnoNombre(int usuarioKey, int id, String nombre) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET nombrealumno=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setString(1, nombre);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	/**
	 * Modifica el genero de un alumno en la base de datos.
	 * @param usuarioKey Clave primaria del usuario que va a realizar la modificacion.
	 * @param id Clave primaria del alumno al que se le va a cambiar el genero.
	 * @param genero Nuevo genero del alumno en la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarAlumnoGenero(int usuarioKey, int id, String genero) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Creamos la consulta y le pasamos los valores de los parametros de la funcion.
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET genero=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setString(1, genero);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	/**
	 * Modifica la edad de un alumno en la base de datos.
	 * @param usuarioKey Clave primaria del usuario que va a realizar la modificacion.
	 * @param id Clave primaria del alumno al que se le va a cambiar la edad.
	 * @param edad Edad nueva del usuario.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarAlumnoEdad(int usuarioKey, int id, int edad) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Creamos la consulta y le pasamos los valores de los parametros.
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET edad=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
		
        stmt.setInt(1, edad);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        
        stmt.executeUpdate();

        con.close();
	}
	
	/**
	 * Modifica el idioma de un alumno en la base de datos.
	 * @param usuarioKey Clave primaria del usuario que va a realizar la modificacion.
	 * @param id Clave primaria del alumno al que se le va a cambiar el idioma.
	 * @param idioma Idioma nuevo del alumno.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarAlumnoIdioma(int usuarioKey, int id, int idioma) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Creamos la consulta y le pasamos los valores de los parametros.
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkidioma=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setInt(1, idioma);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	/**
	 * Modifica el curso de un alumno en la base de datos.
	 * @param usuarioKey Clave primaria del usuario que va a realizar la modificacion.
	 * @param id Clave primaria del alumno al que se le va a cambiar el curso.
	 * @param curso Curso nuevo del alumno.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void modificarAlumnoCurso(int usuarioKey, int id, int curso) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Creamos la consulta y le pasamos los valores de los parametros.
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkcurso=?, fkusuario=?, fechamodificacion=? WHERE pkalumno=?");
        stmt.setInt(1, curso);
        stmt.setInt(2, usuarioKey);
        stmt.setTimestamp(3, date);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
	}
	
	/**
	 * Realiza una baja logica del alumno en la base de datos (añadiendo una fecha a la columna fechabaja).
	 * @param usuarioKey Clave primaria del usuario que va a realizar la baja.
	 * @param id Clave primaria del alumno a dar de baja.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void bajaAlumno(int usuarioKey, int id) throws SQLException, ClassNotFoundException {

		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//Iniciamos la conexion con la base de datos
		
        Connection con;
		con = getConnection();
		//Creamos la consulta y le pasamos los valores de los parametros.
		PreparedStatement stmt = con.prepareStatement("UPDATE alumnos SET fkusuario=?, fechabaja=? WHERE pkalumno=?");
        stmt.setInt(1, usuarioKey);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
	}
	
}