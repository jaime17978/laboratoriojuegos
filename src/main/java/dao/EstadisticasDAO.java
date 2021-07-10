package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Alumno;
import models.Categoria;
import models.ContadorEst;

public class EstadisticasDAO {

	public List<ContadorEst> frecJuegosCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(*) as num from admin_juegos.alumnos_juegos aj inner join admin_juegos.juegos j on aj.fkjuego = j.pkjuego "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? "
	            		+ "and (aj.barrio = 1 or aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(*) as num from admin_juegos.alumnos_juegos aj inner join admin_juegos.juegos j on aj.fkjuego = j.pkjuego "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 "
	            		+ "and (aj.barrio = 1 or aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecTiposCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select ac.nombreactividad, count(distinct(j.pkjuego)) as num from alumnos_juegos aj inner join juegos j "
	            		+ "inner join tipos_actividad ac on aj.fkjuego = j.pkjuego "
	            		+ "and j.fktipoactividad = ac.pktipoactividad "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? "
	            		+ "group by ac.nombreactividad "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombreactividad");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select ac.nombreactividad, count(distinct(j.pkjuego)) as num from alumnos_juegos aj inner join juegos j "
	            		+ "inner join tipos_actividad ac on aj.fkjuego = j.pkjuego "
	            		+ "and j.fktipoactividad = ac.pktipoactividad "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 "
	            		+ "group by ac.nombreactividad "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombreactividad");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}
	//
	public List<ContadorEst> frecGeneroCursoBD(int user_id, int curso, String genero) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and al.genero = ? group by j.nombrejuego order by 2 desc limit 5;");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            stmt.setString(3, genero);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and al.genero = ? group by j.nombrejuego order by 2 desc limit 5;");
	            stmt.setInt(1, user_id);
	            stmt.setString(2, genero);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecColeCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.colegio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.colegio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecBarrioCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.barrio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecFavGenCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecFavGeneroCursoBD(int user_id, int curso, String genero) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and al.genero = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            stmt.setString(3, genero);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and al.genero = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setString(2, genero);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecFavColCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.colegio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.colegio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}
	
	public List<ContadorEst> frecFavBarrioCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public List<ContadorEst> frecFavColBarCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		if (curso != -1) {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.colegio = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.colegio = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            
	            while (result.next()) {
	                int num = result.getInt("num");
	                String nombre = result.getString("nombrejuego");
	                ContadorEst contador = new ContadorEst(num, nombre);
	                     
	                listaFrec.add(contador);
	            }          
	             
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		return listaFrec;
	}

	public String getNombreCursoBD(int curso) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");  
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	
        	PreparedStatement stmt = connection.prepareStatement("SELECT nombrecurso FROM cursos WHERE pkcurso = ?");
            stmt.setInt(1, curso);
            ResultSet result = stmt.executeQuery();
             
            if (result.next()) {
                String nombreCurso = result.getString("nombrecurso");
                
                return nombreCurso;
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }      
         
        return "";
	}

}
