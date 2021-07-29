package models;

/**
 * Clase utilizada para guardar datos de la tabla
 * "Universidades" de la base de datos.
 */
public class Universidad {

	private int id;
	private String nombre;
	private String direccion;
	private String localidad;
	private String region;
	
	/**
	 * Constructor de la clase Universidad.
	 * @param id ID de la universidad.
	 * @param nombre Nombre de la universidad.
	 * @param direccion Direccion de la universidad.
	 * @param localidad Localidad de la universidad.
	 * @param region Region de la universidad.
	 */
	public Universidad(int id, String nombre, String direccion, String localidad, String region) {
		super();
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.region = region;
    }

	/**
	 * Metodo get para la ID
	 * @return ID de la universidad.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set para la ID.
	 * @param id ID nueva de la universidad.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get para el Nombre
	 * @return Nombre de la universidad.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Metodo set para el nombre.
	 * @param nombre Nombre nuevo de la universidad.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get para la direccion.
	 * @return Direccion de la universidad.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set para la direccion.
	 * @param direccion Direccion nueva de la universidad.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get para localidad.
	 * @return Localidad de la universidad.
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * Metodo set para la localidad.
	 * @param localidad Localidad nueva de la universidad.
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
 
	/**
	 * Metodo get para la region.
	 * @return Region de la universidad.
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Metodo set para la region.
	 * @param region Region nueva de la universidad.
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
}
