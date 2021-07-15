package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import models.Categoria;
import models.User;

public class UserDAO {

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
	
	
	public List<User> usuariosBD() throws SQLException, ClassNotFoundException {
        List<User> listUsuarios = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
            String sql = "SELECT * FROM usuarios WHERE fechabaja IS NULL";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
             
            User user = null;
            
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