package models;

public class Cuestionario {
	int idAlumno;
	int idUsuario;
	int idJuego;
	String nombreJuego;
	boolean favorito;
	boolean barrio;
	boolean colegio;
	
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

	public int getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	public String getNombreJuego() {
		return nombreJuego;
	}

	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
	}

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public boolean getBarrio() {
		return barrio;
	}

	public void setBarrio(boolean barrio) {
		this.barrio = barrio;
	}

	public boolean getColegio() {
		return colegio;
	}

	public void setColegio(boolean colegio) {
		this.colegio = colegio;
	}
}
