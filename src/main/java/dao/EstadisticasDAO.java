package dao;

import java.sql.Connection;
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
public class EstadisticasDAO extends BaseDAO{
	
	/*Estadisticas nuevas*/
	
	/**
	 * Borra las estadisticas generadas para un usuario.
	 * @param user_id Clave primaria del usuario que borra sus estadisticas.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void borrarEstadisticas(int user_id) throws ClassNotFoundException, SQLException {
		  		
	
		try (Connection connection = getConnection()) {
			
			/*Borra las estadisticas para un usuario*/
            PreparedStatement stmt = connection.prepareStatement(
            		"DELETE FROM estadisticas WHERE fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
	}
	
	/**
	 * Calcula todas las estadisticas y las guarda en la tabla de estadisticas para ser extraidas mas adelante.
	 * @param user_id Clave primaria del usuario que genera las estadisticas.
	 * @param curso Foreign key del curso para el que se realizan la consultas (si se introduce -1 en este parametro la consulta se realizara para todos los cursos).
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero (Ej: (futbol,6)).
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void calculoEstadisticas(int user_id, int curso) throws ClassNotFoundException, SQLException {
		  		
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			
			try (Connection connection = getConnection()) {
				
				/*Cuenta el numero de niños y niñas*/
	            PreparedStatement stmt = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas(nombre,descripcion,valor,fkcurso,fkusuario, fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de niños y niñas'as nombre_estadistica, ev.genero as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.genero as genero, count(*) as numero, a.fkcurso as curso, aj.fkusuario as usuario, aj.fkidioma as idioma from alumnos_juegos aj inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and aj.fkcurso = ? group by a.genero "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, curso);
	            stmt.executeUpdate();
	            
	            /*Cuenta el numero de juegos por niño/a*/
	            PreparedStatement stmt2 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre,descripcion,valor,fkcurso,fkusuario,fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de juegos por alumno' as nombre, ev.nombre_alumno as descripcion, ev.numero as valor, curso as fkcurso, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.nombrealumno as nombre_alumno, count(*) as numero, aj.fkusuario as usuario, aj.fkcurso as curso, aj.fkidioma as idioma "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? "
	            		+ "group by  a.nombrealumno "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt2.setInt(1, user_id);
	            stmt2.setInt(2, curso);
	            stmt2.executeUpdate();
	            
	            /*Numero de juegos mencionados por las niñas*/
	            PreparedStatement stmt3 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para las niñas' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? and a.genero = 'niña' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt3.setInt(1, user_id);
	            stmt3.setInt(2, curso);
	            stmt3.executeUpdate();
	                      
	            /*Numero de juegos mencionados por los niños*/
	            PreparedStatement stmt4 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para los niños' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? and a.genero = 'niño' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt4.setInt(1, user_id);
	            stmt4.setInt(2, curso);
	            stmt4.executeUpdate();
	            
	            /*Numero de juegos mencionados en colegio o favoritos*/
	            PreparedStatement stmt5 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el colegio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? and (aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt5.setInt(1, user_id);
	            stmt5.setInt(2, curso);
	            stmt5.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt6 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el barrio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? and (aj.barrio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt6.setInt(1, user_id);
	            stmt6.setInt(2, curso);
	            stmt6.executeUpdate();
	            
	            /*Numero de juegos mencionados como favoritos*/
	            PreparedStatement stmt7 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados como favorito 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and a.fkcurso = ? and (aj.favorito = 1) "
	            		+ "group by a.fkcurso, j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt7.setInt(1, user_id);
	            stmt7.setInt(2, curso);
	            stmt7.executeUpdate();
	            
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
				
				/*Cuenta el numero de niños y niñas*/
	            PreparedStatement stmt = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas(nombre,descripcion,valor,fkusuario, fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de niños y niñas'as nombre_estadistica, ev.genero as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.genero as genero, count(*) as numero, aj.fkusuario as usuario, aj.fkidioma as idioma from alumnos_juegos aj inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? group by a.genero "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt.setInt(1, user_id);
	            stmt.executeUpdate();
	                      
	            /*Cuenta el numero de juegos por niño/a*/
	            PreparedStatement stmt2 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre,descripcion,valor,fkusuario,fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de juegos por alumno' as nombre, ev.nombre_alumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.nombrealumno as nombre_alumno, count(*) as numero, aj.fkusuario as usuario, aj.fkidioma as idioma "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? "
	            		+ "group by  a.nombrealumno "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt2.setInt(1, user_id);
	            stmt2.executeUpdate();
	            
	            /*Numero de juegos mencionados por las niñas*/
	            PreparedStatement stmt3 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para las niñas' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and a.genero = 'niña' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt3.setInt(1, user_id);
	            stmt3.executeUpdate();
	            
	            /*Numero de juegos mencionados por los niños*/
	            PreparedStatement stmt4 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para los niños' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkusuario = ? and a.genero = 'niño' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt4.setInt(1, user_id);
	            stmt4.executeUpdate();
	            
	            /*Numero de juegos mencionados en colegio o favoritos*/
	            PreparedStatement stmt5 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el colegio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and (aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt5.setInt(1, user_id);
	            stmt5.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt6 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el barrio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and (aj.barrio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt6.setInt(1, user_id);
	            stmt6.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt7 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados como favorito 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, aj.fkidioma as idioma, aj.fkusuario as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos j on j.pkjuego = aj.fkjuego "
	            		+ "where aj.fkusuario = ? and (aj.favorito = 1) "
	            		+ "group by a.fkcurso, j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt7.setInt(1, user_id);
	            stmt7.executeUpdate();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
	}
	
	/**
	 * Calcula todas las estadisticas para un investigador y las guarda en la tabla de estadisticas para ser extraidas mas adelante.
	 * @param user_id Clave primaria del usuario que genera las estadisticas.
	 * @param curso Foreign key del curso para el que se realizan la consultas (si se introduce -1 en este parametro la consulta se realizara para todos los cursos).
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero (Ej: (futbol,6)).
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void calculoEstadisticasInv(int user_id, int curso, int idioma) throws ClassNotFoundException, SQLException {
		  		
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			
			try (Connection connection = getConnection()) {
				
				/*Cuenta el numero de niños y niñas*/
	            PreparedStatement stmt = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas(nombre,descripcion,valor,fkcurso,fkusuario, fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de niños y niñas'as nombre_estadistica, ev.genero as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.genero as genero, count(*) as numero, a.fkcurso as curso, ? as usuario, ? as idioma from alumnos_juegos aj inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where aj.fkcurso = ? group by a.genero "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, idioma);
	            stmt.setInt(3, curso);
	            stmt.executeUpdate();
	            
	            /*Cuenta el numero de juegos por niño/a*/
	            PreparedStatement stmt2 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre,descripcion,valor,fkcurso,fkusuario,fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de juegos por alumno' as nombre, ev.nombre_alumno as descripcion, ev.numero as valor, curso as fkcurso, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.nombrealumno as nombre_alumno, count(*) as numero, ? as usuario, aj.fkcurso as curso, ? as idioma "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where a.fkcurso = ? "
	            		+ "group by  a.nombrealumno "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt2.setInt(1, user_id);
	            stmt2.setInt(2, idioma);
	            stmt2.setInt(3, curso);
	            stmt2.executeUpdate();
	            
	            /*Numero de juegos mencionados por las niñas*/
	            PreparedStatement stmt3 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para las niñas' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where a.fkcurso = ? and a.genero = 'niña' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt3.setInt(1, idioma);
	            stmt3.setInt(2, user_id);
	            stmt3.setInt(3, curso);
	            stmt3.executeUpdate();
	                      
	            /*Numero de juegos mencionados por los niños*/
	            PreparedStatement stmt4 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para los niños' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where a.fkcurso = ? and a.genero = 'niño' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt4.setInt(1, idioma);
	            stmt4.setInt(2, user_id);
	            stmt4.setInt(3, curso);
	            stmt4.executeUpdate();
	            
	            /*Numero de juegos mencionados en colegio o favoritos*/
	            PreparedStatement stmt5 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el colegio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where a.fkcurso = ? and (aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt5.setInt(1, idioma);
	            stmt5.setInt(2, user_id);
	            stmt5.setInt(3, curso);
	            stmt5.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt6 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el barrio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where a.fkcurso = ? and (aj.barrio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt6.setInt(1, idioma);
	            stmt6.setInt(2, user_id);
	            stmt6.setInt(3, curso);
	            stmt6.executeUpdate();
	            
	            /*Numero de juegos mencionados como favoritos*/
	            PreparedStatement stmt7 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkcurso, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados como favorito 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, curso as fkcurso ,usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where a.fkcurso = ? and (aj.favorito = 1) "
	            		+ "group by a.fkcurso, j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt7.setInt(1, idioma);
	            stmt7.setInt(2, user_id);
	            stmt7.setInt(3, curso);
	            stmt7.executeUpdate();
	            
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
		else {
			
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
				
				/*Cuenta el numero de niños y niñas*/
	            PreparedStatement stmt = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas(nombre,descripcion,valor,fkusuario, fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de niños y niñas'as nombre_estadistica, ev.genero as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.genero as genero, count(*) as numero, ? as usuario, ? as idioma from alumnos_juegos aj inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "group by a.genero "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt.setInt(1, user_id);
	            stmt.setInt(2, idioma);
	            stmt.executeUpdate();
	                      
	            /*Cuenta el numero de juegos por niño/a*/
	            PreparedStatement stmt2 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre,descripcion,valor,fkusuario,fkidioma,fechaalta,fechamodificacion,fechabaja) "
	            		+ "SELECT 'Número de juegos por alumno' as nombre, ev.nombre_alumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  a.nombrealumno as nombre_alumno, count(*) as numero, ? as usuario, ? as idioma "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "group by  a.nombrealumno "
	            		+ "order by 1 desc ) ev;"
	            		);
	            stmt2.setInt(1, user_id);
	            stmt2.setInt(2, idioma);
	            stmt2.executeUpdate();
	            
	            /*Numero de juegos mencionados por las niñas*/
	            PreparedStatement stmt3 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para las niñas' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where a.genero = 'niña' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);

	            stmt3.setInt(1, idioma);
	            stmt3.setInt(2, user_id);
	            stmt3.executeUpdate();
	            
	            /*Numero de juegos mencionados por los niños*/
	            PreparedStatement stmt4 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'número de juegos mencioandos para los niños' as nombre, ev.nombrealumno as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select a.nombrealumno, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "where a.genero = 'niño' "
	            		+ "group by a.genero, a.nombrealumno "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt4.setInt(1, idioma);
	            stmt4.setInt(2, user_id);
	            stmt4.executeUpdate();
	            
	            /*Numero de juegos mencionados en colegio o favoritos*/
	            PreparedStatement stmt5 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el colegio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where (aj.colegio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt5.setInt(1, idioma);
	            stmt5.setInt(2, user_id);
	            stmt5.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt6 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados en el barrio 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where (aj.barrio = 1 or aj.favorito = 1) "
	            		+ "group by j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt6.setInt(1, idioma);
	            stmt6.setInt(2, user_id);
	            stmt6.executeUpdate();
	            
	            /*Numero de juegos mencionados en barrio o favoritos*/
	            PreparedStatement stmt7 = connection.prepareStatement(
	            		"INSERT INTO admin_juegos.estadisticas "
	            		+ "(nombre, descripcion, valor, fkusuario, fkidioma, fechaalta, fechamodificacion, fechabaja) "
	            		+ "SELECT 'juegos mas mencionados como favorito 'as nombre, ev.nombre_juego as descripcion, ev.numero as valor, usuario as fkusuario, idioma as fkidioma, now() as fechaalta, null as fechamodificacion, null as fechabaja "
	            		+ "FROM (select  j.nombrejuego as nombre_juego, count(*) as numero, a.fkcurso as curso, ? as idioma, ? as usuario "
	            		+ "from alumnos_juegos aj "
	            		+ "inner join alumnos a on a.pkalumno = aj.fkalumno "
	            		+ "inner join juegos_investigador j on j.pkjuego = aj.fkjuego "
	            		+ "where (aj.favorito = 1) "
	            		+ "group by a.fkcurso, j.nombrejuego "
	            		+ "order by 2 desc) ev;"
	            		);
	            stmt7.setInt(1, idioma);
	            stmt7.setInt(2, user_id);
	            stmt7.executeUpdate();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
			
		}
	}
	
	/**
	 * Devuelve una lista con el numero de niños y niñas.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> numAlumnosGeneroBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'Número de niños y niñas' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos por alumno.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosPorAlumnoBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'Número de juegos por alumno' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos mencionados por niñas.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosPorNinhasBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'número de juegos mencioandos para las niñas' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos mencionados por niños.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosPorNinhosBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'número de juegos mencioandos para los niños' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos mencionados como jugados en el colegio o favoritos.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosColFavBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'juegos mas mencionados en el colegio ' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos mencionados como jugados en el barrio o favoritos.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosBarFavBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'juegos mas mencionados en el barrio ' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/**
	 * Devuelve una lista con el numero de juegos mencionados como favoritos.
	 * @param user_id Clave primaria del usuario para el que se buscan las estadisticas generadas.
	 * @return Lista de objetos "ContadorEst" que contienen un numbre y un valor entero.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<ContadorEst> juegosFavBD(int user_id) throws ClassNotFoundException, SQLException {
		List<ContadorEst> listaEstadisticas = new ArrayList<ContadorEst>();
		  
		
		//Iniciamos la conexion con la base de datos.
		try (Connection connection = getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(
            		"SELECT * FROM estadisticas WHERE nombre = 'juegos mas mencionados como favorito ' and fkusuario = ?"
            		);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            
            /**
             * Obtenemos los resultados de la consulta y los guardamos en objetos
             * "ContadorEst". Estos objetos se introducen en una lista que se devuelve al servlet.
             */
            while (result.next()) {
                int num = result.getInt("valor");
                String nombre = result.getString("descripcion");
                ContadorEst contador = new ContadorEst(num, nombre);
                     
                listaEstadisticas.add(contador);
            }          
             
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
		
		return listaEstadisticas;
	}
	
	/*Estadisticas antiguas*/
	
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
		  
		
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
		  
		/**
		 * Dado que la aplicacion permite mostrar las estadisticas para un curso o para todos los cursos
		 * se permite que el parametro curso tenga valor -1. En caso de ser asi la consulta no filtrara ningun curso y mostrara los resultados en general.
		 */
		if (curso != -1) {
			//Iniciamos la conexion con la base de datos.
			try (Connection connection = getConnection()) {
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
			try (Connection connection = getConnection()) {
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
          
      //Iniciamos la conexion con la base de datos.
        try (Connection connection = getConnection()) {
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
