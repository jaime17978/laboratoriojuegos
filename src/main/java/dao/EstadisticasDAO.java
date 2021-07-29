package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.ContadorEst;

/**
 * Clase que contiene los metodos que acceden a diferentes tablas para
 * generar las estadisticas.
 */
public class EstadisticasDAO {

	/**
	 * Devuelve una lista de los juegos mencionados en los cuestionarios de un usuario y el 
	 * numero de veces que se mencionan dichos juegos.
	 * @param user_id Clave primaria del usuario que genera las estadisticas.
	 * @param curso Foreign key del curso para el que se realiza la consulta (si se introduce -1 en este parametro la consulta se realizara para todos los cursos).
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero (Ej: (futbol,6)).
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecJuegosCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios para devolver los nombres de todos los juegos
	    		 * mencionados y el numero de veces que lo han sido.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(*) as num from admin_juegos.alumnos_juegos aj inner join admin_juegos.juegos j on aj.fkjuego = j.pkjuego "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? "
	            		+ "and (aj.barrio = 1 or aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(*) as num from admin_juegos.alumnos_juegos aj inner join admin_juegos.juegos j on aj.fkjuego = j.pkjuego "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 "
	            		+ "and (aj.barrio = 1 or aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	
	/**
	 * Genera la estadistica de tipos de actividad. Es decir, devuelve una lista con
	 * los diferentes tipos de actividad y el numero de juegos que son de dichos tipos.
	 * @param user_id Clave primaria del usuario que genera las estadisticas.
	 * @param curso Curso para el que filtrar las estadisticas (si se introduce -1 las estadisticas se generan sin filtrar por curso).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecTiposCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los tipos de actividad
	    		 * de los juegos de los cuestionarios y el numero de juegos que tienen dichos tipos de actividad.
	    		 */
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
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los tipos de actividad
	    		 * de los juegos de los cuestionarios y el numero de juegos que tienen dichos tipos de actividad.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select ac.nombreactividad, count(distinct(j.pkjuego)) as num from alumnos_juegos aj inner join juegos j "
	            		+ "inner join tipos_actividad ac on aj.fkjuego = j.pkjuego "
	            		+ "and j.fktipoactividad = ac.pktipoactividad "
	            		+ "where aj.fkusuario = ? and aj.fkidioma = 1 "
	            		+ "group by ac.nombreactividad "
	            		+ "order by 2 desc;");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	
	/**
	 * Genera la estadistica de los 5 juegos mas mencionados para niños o niñas en funcion de
	 * los parametros.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key del curso para el que se filtra la consulta (si vale -1 la consulta se aplica a todos los cursos)
	 * @param genero Genero con el que filtrar la consulta.
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecGeneroCursoBD(int user_id, int curso, String genero) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los 5 juegos mas
	    		 * mencionados por niños o niñas en funcion del parametro "genero".
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and al.genero = ? group by j.nombrejuego order by 2 desc limit 5;");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            stmt.setString(3, genero);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los tipos de actividad
	    		 * de los juegos de los cuestionarios y el numero de juegos que tienen dichos tipos de actividad.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and al.genero = ? group by j.nombrejuego order by 2 desc limit 5;");
	            stmt.setInt(1, user_id);
	            stmt.setString(2, genero);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	
	/**
	 * Genera la estadistica de los 5 juegos mas mencionados que se juegan en el colegio.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que filtramos la consulta por curso (si vale -1 se realizara para todos los cursos).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<ContadorEst> frecColeCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los 5 juegos
	    		 * mas mencionados y jugados en el colegio.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.colegio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.colegio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	/**
	 * Genera la estadistica de los 5 juegos mas mencionados que se juegan en el barrio.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que filtramos la consulta por curso (si vale -1 se realizara para todos los cursos).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<ContadorEst> frecBarrioCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los 5 juegos
	    		 * mas mencionados y jugados en el barrio.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.barrio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los 5 juegos
	    		 * mas mencionados y jugados en el barrio.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc limit 5");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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

	/**
	 * Calcula el numero de veces que se ha mencionado cada juego como favorito.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que se filtra la consulta para un curso especifico (si vale -1 no se realiza el filtro). 
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<ContadorEst> frecFavGenCursoBD(int user_id, int curso) throws SQLException, ClassNotFoundException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y el numero de veces que se han mencionado como favoritos.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y el numero de veces que se han mencionado como favoritos.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	
	/**
	 * Muestra los juegos que se han mencionado como favoritos y el numero
	 * de veces que se han mencionado como favoritos, filtrando los resultados
	 * segun el genero y curso especificado en los parametros.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que se filtra la consulta.
	 * @param genero Genero con el que se filtra la consulta (puede ser "niño" o "niña").
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecFavGeneroCursoBD(int user_id, int curso, String genero) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y el numero de veces que se han mencionado como favoritos, 
	    		 * filtrandolos segun el parametro "genero".
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and al.genero = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            stmt.setString(3, genero);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y el numero de veces que se han mencionado como favoritos,
	    		 * filtrandolos segun el parametro "genero".
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j inner join alumnos al on aj.fkjuego = j.pkjuego and aj.fkalumno = al.pkalumno where aj.fkusuario = ? and aj.fkidioma = 1 and al.genero = ? and aj.favorito = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setString(2, genero);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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

	/**
	 * Genera una lista de los juegos mencionados como favoritos y jugados en el 
	 * colegio y el numero de veces que se han mencionado de esta forma.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que filtrar la consulta por curso (si vale -1 el filtrado no se realiza).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecFavColCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y jugados en el colegio asi como el numero de veces que se han
	    		 * mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.colegio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y jugados en el colegio asi como el numero de veces que se han
	    		 * mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.colegio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
	
	/**
	 * Genera una lista de los juegos mencionados como favoritos y jugados en el 
	 * barrio y el numero de veces que se han mencionado de esta forma.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que filtrar la consulta por curso (si vale -1 el filtrado no se realiza).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecFavBarrioCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y jugados en el barrio asi como el numero de veces que se han
	    		 * mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos y jugados en el barrio asi como el numero de veces que se han
	    		 * mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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

	/**
	 * Genera una lista de los juegos mencionados como favoritos, jugados en el 
	 * colegio y jugados en el barrio asi como el numero de veces que se han mencionado 
	 * de esta forma.
	 * @param user_id Clave primaria del usuario para el que se calculan las estadisticas.
	 * @param curso Foreign key con la que filtrar la consulta por curso (si vale -1 el filtrado no se realiza).
	 * @return Lista de objetos "ContadorEst" que contienen los resultados de la consulta.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> frecFavColBarCursoBD(int user_id, int curso) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaFrec = new ArrayList<ContadorEst>();
		Class.forName("com.mysql.cj.jdbc.Driver");  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos, jugados en el colegio y jugados en el barrio asi como el numero de veces 
	    		 * que se han mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement(
	            		"select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.fkcurso = ? and aj.favorito = 1  and aj.colegio = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
				/**
	    		 * Creamos la consulta e introducimos los datos de los parametros.
	    		 * 
	    		 * La consulta busca en la tabla de cuestionarios (alumnos_juegos) para devolver los juegos
	    		 * mencionados como favoritos, jugados en el colegio y jugados en el barrio asi como el numero
	    		 * de veces que se han mencionado de esta forma.
	    		 */
	            PreparedStatement stmt = connection.prepareStatement("select j.nombrejuego, count(j.pkjuego) as num from alumnos_juegos aj inner join juegos j on aj.fkjuego = j.pkjuego where aj.fkusuario = ? and aj.fkidioma = 1 and aj.favorito = 1 and aj.colegio = 1 and aj.barrio = 1 group by j.nombrejuego order by 2 desc");
	            stmt.setInt(1, user_id);
	            ResultSet result = stmt.executeQuery();
	            /**
	             * Obtenemos los resultados de la consulta y los guardamos en objetos
	             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
	             */
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

	/**
	 * Obtiene el nombre de un curso mediante su clave primaria.
	 * @param curso Clave primaria del curso para el que queremos obtener el nombre.
	 * @return Nombre del curso
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getNombreCursoBD(int curso) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");  
      //Iniciamos la conexion con la base de datos.
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin_juegos?serverTimezone=ECT", "root", "")) {
        	/**
    		 * Creamos la consulta e introducimos los datos de los parametros.
    		 * 
    		 */
        	PreparedStatement stmt = connection.prepareStatement("SELECT nombrecurso FROM cursos WHERE pkcurso = ?");
            stmt.setInt(1, curso);
            ResultSet result = stmt.executeQuery();
             
            /**
             * Accedemos a los resultados de la consulta y devolvemos el nombre
             */
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
