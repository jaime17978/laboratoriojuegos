package models;

/**
 * Clase que se utiliza para guardar la informacion
 * de la tabla "Regiones" de la base de datos.
 */
public class Region {
	private String id;
	private String nombre;
	private String pais; 
	
	/**
	 * Constructor de la clase Region.
	 * @param id Id de la region.
	 * @param nombre Nombre de la region.
	 * @param pais Foreign key del pais de la region.
	 */
	public Region(String id, String nombre, String pais) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }
	
	/**
	 * Metodo get para el nombre.
	 * @return Nombre de la region.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set para el nombre.
	 * @param nombre Nombre nuevo de la region.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get para la ID.
	 * @return ID de la region.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Metodo set para la ID.
	 * @param id ID nueva de la region.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Metodo get para el pais.
	 * @return Pais de la region.
	 */
	public String getPais() {
		return pais;
	}
	
	/**
	 * Metodo set para el pais.
	 * @param pais Pais nuevo de la region.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
}
