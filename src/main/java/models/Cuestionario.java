package models;

/**
 * Clase que se utiliza para guardar la informacion
 * de los cuestionarios (corresponde a la tabla alumnos_juegos).
 */
public class Cuestionario {
	int idAlumno;
	int idUsuario;
	int idJuego;
	String nombreJuego;
	boolean favorito;
	boolean barrio;
	boolean colegio;
	
	/**
	 * Constructor de la clase cuestionario.
	 * @param idA ID del alumno del cuestionario.
	 * @param idU ID del usuario del cuestionario.
	 * @param idJ ID del juego del cuestionario.
	 * @param nom Nombre del juego del cuestionario.
	 * @param fav Si el juego se ha marcado como favorito.
	 * @param bar Si el juego se ha marcado como jugado en el barrio.
	 * @param col Si el juego se ha marcado como jugado en el colegio.
	 */
	public Cuestionario(int idA, int idU, int idJ, String nom, boolean fav, boolean bar, boolean col) {
        this.idAlumno = idA;
        this.idUsuario = idU;
        this.idJuego = idJ;
        this.nombreJuego = nom;
        this.favorito = fav;
        this.barrio = bar;
        this.colegio = col;
    }
	
	@Override
	public String toString() {
		return "Cuestionario [idAlumno=" + idAlumno + ", idUsuario=" + idUsuario + ", idJuego=" + idJuego
				+ ", nombreJuego=" + nombreJuego + ", favorito=" + favorito + ", barrio=" + barrio + ", colegio="
				+ colegio + "]";
	}

	/**
	 * Metodo get para la ID del juego del cuestionario.
	 * @return ID del juego.
	 */
	public int getIdJuego() {
		return idJuego;
	}

	/**
	 * Metodo set para la ID del juego del cuestionario.
	 * @param idJuego ID del juego nuevo del cuestionario.
	 */
	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	/**
	 * Metodo get para el nombre del juego del cuestionario.
	 * @return Nombre del juego del cuestionario.
	 */
	public String getNombreJuego() {
		return nombreJuego;
	}

	/**
	 * Metodo set para el nombre de juego del cuestionario.
	 * @param nombreJuego Nombre del juego nuevo del cuestionario.
	 */
	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
	}

	/**
	 * Metodo get para la ID del alumno del cuestionario.
	 * @return ID del alumno del cuestionario.
	 */
	public int getIdAlumno() {
		return idAlumno;
	}

	/**
	 * Metodo set de la ID del Alumno del cuestionario.
	 * @param idAlumno ID del alumno nuevo del cuestionario.
	 */
	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	/**
	 * Metodo get para la ID del usuario del cuestionario.	
	 * @return ID del usuario del cuestionario.
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Metodo set de la ID del usuario del cuestionario.
	 * @param idUsuario ID del usuario nuevo del cuestionario.
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Metodo get para el valor de favorito.
	 * @return Booleano con el valor de favorito.
	 */
	public boolean getFavorito() {
		return favorito;
	}

	/**
	 * Metodo set para el valor de favorito.
	 * @param favorito Valor nuevo de favorito.
	 */
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}
	
	/**
	 * Metodo get para el valor de barrio.
	 * @return Booleano con el valor de barrio.
	 */
	public boolean getBarrio() {
		return barrio;
	}

	/**
	 * Metodo set para el valor de barrio.
	 * @param barrio Valor nuevo de barrio.
	 */
	public void setBarrio(boolean barrio) {
		this.barrio = barrio;
	}
	
	/**
	 * Metodo get para el valor de colegio.
	 * @return Booleano con el valor de colegio.
	 */
	public boolean getColegio() {
		return colegio;
	}

	/**
	 * Metodo set para el valor de colegio.
	 * @param colegio Valor nuevo de colegio.
	 */
	public void setColegio(boolean colegio) {
		this.colegio = colegio;
	}
}
