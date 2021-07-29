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

import models.Categoria;
/**
 * Clase que contiene los metodos que acceden a la tabla Anhos
 * de la base de datos.
 */
public class AnhoDAO {

	/**
	 * Devuelve una lista con los años (Anho) de la base de datos.
	 * @return Lista de objetos "Categoria" que contienen los datos de cada año en la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Categoria> anhosBD() throws SQLException, ClassNotFoundException {
        List<Categoria> listaAnhos = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            //Creamos la consulta
        	String sql = "SELECT * FROM anhos WHERE fechabaja is NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            /**
             * Por cada "Anho" devuelto por la consulta, accedemos a los datos
             * de las columnas correspondientes y creamos un objeto "Categoria"
             * con esta informacion. Este objeto se introduce en una lista
             * que se devuelve al servlet.
             */
            while (result.next()) {
                int id = result.getInt("pkanho");
                String nombre = result.getString("nombreanho");
                Categoria anho = new Categoria(id, nombre);
                     
                listaAnhos.add(anho);
            }             
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listaAnhos;
    }

	/**
	 * Cambia el nombre de un año (Anho) en la base de datos.
	 * @param id Clave primaria del año a modificar.
	 * @param nombre Nuevo nombre del año.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void cambioNombre(int id, String nombre) throws SQLException, ClassNotFoundException {
		
		//Iniciamos la conexion con la base de datos.
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		//Creamos y ejecutamos la consulta.
		PreparedStatement stmt = con.prepareStatement("UPDATE anhos SET nombreanho=?, fechamodificacion=? WHERE pkanho=?");
        stmt.setString(1, nombre);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}

	/**
	 * Realiza una baja logica de un año (Anho) en la base de datos.
	 * @param id Clave primaria del año a dar de baja
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarAnho(int id) throws ClassNotFoundException, SQLException {
        
		//Iniciamos la conexion con la base de datos
        Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		/**
		 * Creamos y ejecutamos la consulta.
		 */
		PreparedStatement stmt = con.prepareStatement("UPDATE anhos SET fechabaja=? WHERE pkanho=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        con.close();
        
	}

	/**
	 * Crea un año (Anho) en la base de datos.
	 * @param id Clave primaria del usuario que crea el año.
	 * @return La clave primaria del nuevo año creado.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int crearAnho(int id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id_n = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO anhos (nombreanho, fkusuario, fkidioma, fechaalta) VALUES ('', ?, 1, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, id);
        stmt.setTimestamp(2, date);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id_n=rs.getInt(1);
        }
        
        con.close();	

        return id_n;
	}
	
}
