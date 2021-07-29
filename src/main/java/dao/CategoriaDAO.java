package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Categoria;
import models.User;

/**
 * Clase que accede a diferentes tablas de la base de datos para realizar
 * consultas sencillas.
 */
public class CategoriaDAO {
    
	/**
	 * Accede a la tabla de cursos y devuelve una lista con la informacion de todos ellos.
	 * @return Lista de objetos "Categoria" que contienen la informacion de los cursos de la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
    public List<Categoria> cursosBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM cursos";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Accedemos a los resultados de la consulta y generamos objetos
             * "Categoria" con la informacion de los cursos. Estos objetos se
             * introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkcurso");
                String nombre = result.getString("nombrecurso");
                Categoria category = new Categoria(id, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
    
    /**
     * Para un usuario, accede a las tablas de alumnos y cursos y devuelve los cursos para los
     * que el usuario ha creado alumnos (Ej: Si el usuario ha creado 2 alumnos para 4º, 3 alumnos
     * para 6º y 4 para 1º el metodo devolvera una lista con los datos de 4º, 6º y 1º).
     * @param u Objeto User (usuario) para el cual se buscan los cursos unicos.
     * @return Lista con los cursos para los cuales el usuario ha creado un alumno (sin repeticiones).
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Categoria> cursosCuestBD(User u) throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	//Creamos la consulta y le pasamos los valores correspondientes del usuario pasado por parametro
            String sql = "SELECT DISTINCT fkcurso, nombrecurso from alumnos as a join admin_juegos.cursos as b where a.fkcurso = b.pkcurso and a.fkusuario = "+Integer.toString(u.getId());
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            /**
             * Accedemos a los resultados de la consulta y creamos objetos "Categoria"
             * con la informacion de las columnas correspondientes. Estos objetos se añaden
             * a una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("fkcurso");
                String nombre = result.getString("nombrecurso");
                Categoria category = new Categoria(id, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
    
    /**
     * Para un usuario, accede a las tablas de alumnos y cursos y devuelve los cursos para los
     * que el usuario ha creado cuestionarios (alumnos_juegos).
     * @param id Clave primaria del usuario para el que se extraen los cursos.
     * @return Lista de objetos "Categoria" con la informacion de los cursos.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Categoria> cursosEstadisticasBD(int id) throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT DISTINCT fkcurso, nombrecurso from admin_juegos.alumnos_juegos as a join admin_juegos.cursos as b where a.fkcurso = b.pkcurso and a.fkusuario ="+id;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Categoria". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id_res = result.getInt("fkcurso");
                String nombre = result.getString("nombrecurso");
                Categoria category = new Categoria(id_res, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
    
    /**
     * Devuelve una lista con los idiomas de la base de datos, en forma de objetos "Categoria".
     * @return Lista de objetos "Categoria" que contienen la informacion de los idiomas de la base de datos.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Categoria> idiomasBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM idiomas";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Categoria". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkidioma");
                String nombre = result.getString("nombreidioma");
                Categoria category = new Categoria(id, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
    
    /**
     * Accede a la tabla de perfiles y devuelve aquellos que estan activos.
     * @return Lista de objetos categoria con la informacion de los perfiles activos.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Categoria> perfilesBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM perfiles WHERE activo = 1";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Categoria". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkperfil");
                String nombre = result.getString("nombreperfil");
                Categoria category = new Categoria(id, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
    
    /**
     * Accede a la tabla de tipos de actividad y los devuelve en una lista.
     * @return Lista de objetos "Categoria" que contienen los tipos de actividad de la tabla.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Categoria> tiposActividadBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM tipos_actividad";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "Categoria". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pktipoactividad");
                String nombre = result.getString("nombreactividad");
                Categoria category = new Categoria(id, nombre);
                     
                listCategory.add(category);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listCategory;
    }
}