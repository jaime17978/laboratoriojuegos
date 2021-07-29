package models;

/**
 * Clase utilizada para guardar datos de la tabla
 * Practicas de la base de datos.
 */
public class Practica {

	private int id;
	private String nombre;
	private int tipo;
	private int colegio;
	private int anho;
	private int alumno;
	
	/**
	 * Constructor de la clase practica.
	 * @param id ID de la practica.
	 * @param nombre Nombre de la practica.
	 * @param tipo Tipo de actividad de la practica.
	 * @param colegio Colegio de la practica.
	 * @param anho Año de la practica.
	 * @param alumno Alumno que realiza la practica.
	 */
	public Practica(int id, String nombre, int tipo, int colegio, int anho, int alumno) {
		super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.colegio = colegio;
        this.anho = anho;
        this.alumno = alumno;
    }
	
	/**
	 * Metodo get para la ID
	 * @return ID de la practica.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Metodo set para la ID.
	 * @param id ID nueva de la practica.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Metodo get ppara el nombre.
	 * @return Nombre de la practica.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Metodo set para el nombre de la practica.
	 * @param nombre Nombre nuevo de la practica.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Metodo get para el tipo de la practica.
	 * @return Tipo de actividad de la practica.
	 */
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Metodo set para el tipo de la practica.
	 * @param tipo Tipo de actividad nuevo de la practica.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Metodo get para el colegio.
	 * @return Colegio de la practica.
	 */
	public int getColegio() {
		return colegio;
	}
	
	/**
	 * Metodo set para el colegio.
	 * @param colegio Colegio nuevo de la practica.
	 */
	public void setColegio(int colegio) {
		this.colegio = colegio;
	}
	
	/**
	 * Metodo get para el año.
	 * @return Año de la practica.
	 */
	public int getAnho() {
		return anho;
	}
	
	/**
	 * Metodo set para el año.
	 * @param anho Año de la practica.
	 */
	public void setAnho(int anho) {
		this.anho = anho;
	}
	
	/**
	 * Metodo get para el alumno.
	 * @return Alumno de la practica.
	 */
	public int getAlumno() {
		return alumno;
	}
	
	/**
	 * Metodo set para el Alumno.
	 * @param alumno Alumno nuevo de la practica.
	 */
	public void setAlumno(int alumno) {
		this.alumno = alumno;
	}
}
