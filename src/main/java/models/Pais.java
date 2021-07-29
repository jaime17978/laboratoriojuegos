package models;

/**
 * Esta clase se utiliza para guardar datos
 * de la tabla Paises de la base de datos.
 */
public class Pais {

	private String id;
	private String nombre;
	
	/**
	 * Constructor de la clase Pais
	 * @param id ID del pais.
	 * @param nombre Nombre del pais.
	 */
	public Pais(String id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
	
	/**
	 * Metodo get para el nombre.
	 * @return Nombre del pais.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set para el nombre.
	 * @param nombre Nombre nuevo del pais.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get para la ID.
	 * @return ID del pais.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Metodo set para la ID del pais.
	 * @param id ID nueva del pais.
	 */
	public void setId(String id) {
		this.id = id;
	}
}
