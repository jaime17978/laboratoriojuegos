package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import models.User;

/**
 * Clase que contiene los metodos que acceden 
 * a la tabla "Usuarios" de la base de datos.
 */
public class UserDAO {
	
	/**
	 * Realiza el proceso de login para un usuario.
	 * @param email Correo electronico del usuario.
	 * @param password Contraseña del usuario.
	 * @return Objeto "User" con los datos del usuario en caso de que la informacion de login sea correcta. En caso contrario devuelve null.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
    public User userLogin(String email, String password) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");

        PreparedStatement stmt = con.prepareStatement("SELECT * from usuarios WHERE  email = ? AND activo = 1 AND fechabaja IS NULL");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        User user = null;

        if(rs.next()) {
            user = new User();
            user.setId(rs.getInt("pkusuario"));
            user.setEmail(email);
            user.setPassword(rs.getString("password"));
            user.setLanguage(rs.getInt("fkidioma"));
            user.setPermissions(rs.getInt("fkperfil"));
        }
        else {
        	return null;
        }
        con.close();
        
        try {
        	if (BCrypt.checkpw(password, user.getPassword()))
            	return user;
            else
            	return null;
        }
        catch(IllegalArgumentException ex){
        	return null;
        }
        
    }

    /**
     * Metodo temporal de cambio de contraseña. Este metodo tiene que ser reemplazado por un mecanismo
     * mas apropiado de cambio de contraseña. Solamente se ha creado para hacer pruebas.
     * @param email Correo del usuario.
     * @param password Contraseña nueva del usuario.
     * @return Objeto "User" con los datos del usuario.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public User userChangePassword(String email, String password) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        
        PreparedStatement stmtUpdate = con.prepareStatement("UPDATE usuarios SET password = ? WHERE email = ? AND fechabaja IS NULL");
    	stmtUpdate.setString(1, hashed);
    	stmtUpdate.setString(2, email);
        stmtUpdate.executeUpdate();
        
        PreparedStatement stmt = con.prepareStatement("SELECT * from usuarios WHERE  email = ? AND password = ? AND activo = 1");
        stmt.setString(1, email);
        stmt.setString(2, hashed);
        ResultSet rs = stmt.executeQuery();

        User user = null;

        if(rs.next()) {
            user = new User();
            user.setId(rs.getInt("pkusuario"));
            user.setEmail(email);
            user.setPassword(password);
            user.setLanguage(rs.getInt("fkidioma"));
            user.setPermissions(rs.getInt("fkperfil"));
        }

        con.close();

        return user;
	}
	
	/**
	 * Devuelve los usuarios no dados de baja de la base de datos.
	 * @return Lista de objetos "User" con los resultados de la consulta.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<User> usuariosBD() throws SQLException, ClassNotFoundException {
        List<User> listUsuarios = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM usuarios WHERE fechabaja IS NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            User user = null;
            /**
             * Guardamos los resultados en objetos "User". Estos
             * objetos se guardan en una lista que se devuelve al
             * servlet.
             */
            while (result.next()) {
            	
            	user = new User();
                user.setId(result.getInt("pkusuario"));
                user.setEmail(result.getString("email"));
                user.setPerfil(result.getInt("fkperfil"));
                user.setUniversidad(result.getInt("fkuniversidad"));
                user.setActivo(result.getBoolean("activo"));
                user.setLanguage(result.getInt("fkidioma"));
                     
                listUsuarios.add(user);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return listUsuarios;
    }
	
	/**
	 * Cambia el email de un usuario.
	 * @param id Clave primaria del usuario al que se le cambia el correo.
	 * @param email Correo nuevo del usuario.
	 * @param id_editor Clave primaria del usuario que realiza el cambio de correo.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioEmail(int id, String email, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET email=?, fechamodificacion=?, fkusuario=? WHERE pkusuario=?");
        stmt.setString(1, email);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_editor);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el tipo de perfil del usuario.
	 * @param id Clave primaria del usuario al que se le cambia el tipo de perfil. 
	 * @param perfil Tipo de perfil nuevo.
	 * @param id_editor Clave primaria del usuario que realiza el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioPerfil(int id, int perfil, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET fkperfil=?, fechamodificacion=?, fkusuario=? WHERE pkusuario=?");
        stmt.setInt(1, perfil);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_editor);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia el idioma de un usuario.
	 * @param id Clave primaria del usuario al que se le cambia el idioma.
	 * @param idioma Idioma nuevo del usuario.
	 * @param id_editor Clave primaria del usuario que realiza el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioIdioma(int id, int idioma, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET fkidioma=?, fechamodificacion=?, fkusuario=? WHERE pkusuario=?");
        stmt.setInt(1, idioma);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_editor);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Cambia la universidad de un usuario.
	 * @param id Clave primaria del usuario al que se le cambia la universidad.
	 * @param universidad Nueva universidad del usuario.
	 * @param id_editor Clave primaria del usuario que realiza el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioUniversidad(int id, int universidad, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET fkuniversidad=?, fechamodificacion=?, fkusuario=? WHERE pkusuario=?");
        stmt.setInt(1, universidad);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_editor);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Activa o desactiva un usuario.
	 * @param id Clave primaria del usuario al que se le cambia el estado de activo.
	 * @param activo Nuevo estado de activo (true/false).
	 * @param id_editor Clave primaria del usuario que realiza el cambio.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void cambioActivo(int id, boolean activo, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET activo=?, fechamodificacion=?, fkusuario=? WHERE pkusuario=?");
        stmt.setBoolean(1, activo);
        stmt.setTimestamp(2, date);
        stmt.setInt(3, id_editor);
        stmt.setInt(4, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Realiza una baja logica de un usuario.
	 * @param id Clave primaria del usuario que se va a dar de baja.
	 * @param id_editor Clave primaria del usuario que realiza la baja.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarUsuario(int id, int id_editor) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		PreparedStatement stmt = con.prepareStatement("UPDATE usuarios SET fechabaja=?, activo=0, fkusuario=? WHERE pkusuario=?");
        stmt.setTimestamp(1, date);
        stmt.setInt(2, id_editor);
        stmt.setInt(3, id);
        stmt.executeUpdate();

        con.close();
		
	}
	
	/**
	 * Crea un usuario con datos por defecto en la base de datos.
	 * @param userId Clave primaria del usuario que crea al otro usuario.
	 * @return Entero con la clave primaria del usuario creado.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int crearUsuario(int userId) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        Connection con;
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "");
		int id = -1;  
        PreparedStatement stmt = con.prepareStatement("INSERT INTO usuarios (email, password, fkperfil, fkuniversidad, activo, fkusuario, fkidioma, fechaalta) VALUES ('', '', 3, 1, 0, ?, 1, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userId);
        stmt.setTimestamp(2, date);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        
        con.close();	
        return id;
	}
	
}