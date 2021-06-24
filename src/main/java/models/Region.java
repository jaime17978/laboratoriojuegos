package models;

public class Region {
	private String id;
	private String nombre;
	private String pais; 
	
	public Region(String id, String nombre, String pais) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
