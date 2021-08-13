package models;

/**
 * Clase utilizada para guardar la informacion de un Alumno
 * de la base de datos.
 */
public class Alumno {
	
	private int id;
	private String nombre;
	private int curso;
	private String genero;
	private int edad;
	private int idioma;
	
	/**
	 * Constructor de la clase Alumno.
	 * @param id Id del alumno.
	 * @param nombre Nombre del alumno.
	 * @param curso Foreign key del curso del alumno.
	 * @param genero Genero del alumno (niño/niña).
	 * @param edad Edad del alumno.
	 * @param idioma Foreign key del idioma del alumno.
	 */ 
	public Alumno(int id, String nombre, int curso, String genero, int edad, int idioma) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.genero = genero;
        this.edad = edad;
        this.idioma = idioma;
    }
	
	/**
	 * Metodo get para la ID.
	 * @return ID del alumno.
	 */
    public int getId(){
        return id;
    }

    /**
     * Metodo set para la ID
     * @param i ID nueva del alumno.
     */
    public void setId(int i){
        id = i;
    }

    /**
     * Metodo get para el nombre.
     * @return Nombre del alumno.
     */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set para el nombre.
	 * @param nombre Nombre nuevo del alumno.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get para el curso.
	 * @return Foreign key del curso del alumno.
	 */
	public int getCurso() {
		return curso;
	}

	/**
	 * Metodo set para el curso.
	 * @param curso Foreign key del nuevo curso del alumno.
	 */
	public void setCurso(int curso) {
		this.curso = curso;
	}

	/**
	 * Metodo get para el genero.
	 * @return Genero del alumno (niño/niña)
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Metodo set para el genero.
	 * @param genero Genero nuevo del alumno.
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Metodo get para la edad.
	 * @return Edad del alumno.
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * Metodo set para la edad.
	 * @param edad Edad nueva del alumno.
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * Metodo get para el idioma.
	 * @return Idioma del alumno.
	 */
	public int getIdioma() {
		return idioma;
	}

	/**
	 * Metodo set para el idioma.
	 * @param idioma Idioma nuevo del alumno.
	 */
	public void setIdioma(int idioma) {
		this.idioma = idioma;
	}

	@Override
    public String toString() {
        return String.format(nombre);
    }
}
