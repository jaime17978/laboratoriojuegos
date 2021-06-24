package dao;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

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
}