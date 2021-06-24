package models;

public class Alumno {
	
	private int id;
	private String nombre;
	private int curso;
	private String genero;
	private int edad;
	private int idioma;

	public Alumno(int id, String nombre, int curso, String genero, int edad, int idioma) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.genero = genero;
        this.edad = edad;
        this.idioma = idioma;
    }
	
    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getIdioma() {
		return idioma;
	}

	public void setIdioma(int idioma) {
		this.idioma = idioma;
	}

	@Override
    public String toString() {
        return String.format(nombre);
    }
}
