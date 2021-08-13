package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Clase DAO base con el metodo que devuelve la conexion a la base de datos.
 */
public class BaseDAO {

	/**
	 * Metodo que genera la conexion a la base de datos. Para editar los parametros de la conexion
	 * (url, usuario y contraseña) se deben editar desde esta funcion.
	 * @return Conexion con la base de datos.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	 public Connection getConnection() throws ClassNotFoundException, SQLException {
	   Class.forName("com.mysql.cj.jdbc.Driver"); 
	   String url = "jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT";
	   String name = "root";
	   String password = "";
	   
	   Connection con = DriverManager.getConnection(url, name, password);
	   return con;
	 }
}
