package models;

public class Pais {

	private String id;
	private String nombre;
	
	public Pais(String id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
