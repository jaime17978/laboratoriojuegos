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
 
public class CategoriaDAO {
     
    public List<Categoria> cursosBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM cursos";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
    
    public List<Categoria> cursosCuestBD(User u) throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT DISTINCT fkcurso, nombrecurso from alumnos as a join admin_juegos.cursos as b where a.fkcurso = b.pkcurso and a.fkusuario = "+Integer.toString(u.getId());
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
    
    public List<Categoria> cursosEstadisticasBD(int id) throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT DISTINCT fkcurso, nombrecurso from admin_juegos.alumnos_juegos as a join admin_juegos.cursos as b where a.fkcurso = b.pkcurso and a.fkusuario ="+id;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
    
    public List<Categoria> idiomasBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM idiomas";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
    
    public List<Categoria> perfilesBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM perfiles WHERE activo = 1";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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
    
    public List<Categoria> tiposActividadBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listCategory = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM tipos_actividad";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
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