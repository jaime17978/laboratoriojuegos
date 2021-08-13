package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase utilizada para verificar si un usuario tiene permiso para acceder
 * a una pagina mediante la tabla perfiles menus.
 */
public class PermisosDAO extends BaseDAO{
	
	/**
	 * Verifica si un tipo de usuario tiene acceso a una pagina determinada.
	 * @param id_perfil Tipo del usuario para el que verificar los permisos.
	 * @param id_menu Clave primaria del menu para el que se va a crear 
	 * @return Verdadero o falso en funcion de si el tipo de perfil especifico tiene permiso para acceder a la pagina.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean verificarPermisos(int id_perfil, int id_menu) throws ClassNotFoundException, SQLException {
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM admin_juegos.perfiles_menus where fkperfil = ? and fkmenu = ?;"
            		);
            stmt.setInt(1, id_perfil);
            stmt.setInt(2, id_menu);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Si la consulta devuelve una fila de perfilesmenus
             * significa que el usuario tiene permisos para acceder
             * a la pagina y el metodo devuelve true.
             */
            if (result.next()) {
                return true;
            }          
            else {
            	return false;
            }
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
	}
}
