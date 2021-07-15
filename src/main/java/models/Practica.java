package models;

public class Practica {

	private int id;
	private String nombre;
	private int tipo;
	private int colegio;
	private int anho;
	private int alumno;
	
	public Practica(int id, String nombre, int tipo, int colegio, int anho, int alumno) {
		super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.colegio = colegio;
        this.anho = anho;
        this.alumno = alumno;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getColegio() {
		return colegio;
	}
	public void setColegio(int colegio) {
		this.colegio = colegio;
	}
	public int getAnho() {
		return anho;
	}
	public void setAnho(int anho) {
		this.anho = anho;
	}
	public int getAlumno() {
		return alumno;
	}
	public void setAlumno(int alumno) {
		this.alumno = alumno;
	}
	
	
}
