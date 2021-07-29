package models;

/**
 * Clase utilizada para guardar la informacion de un colegio
 * de la base de datos.
 */
public class Colegio {
	
	private int id;
	private String nombre;
	private String direccion;
	private String localidad;
	private String region;
	private String tipo;
	private int codigo;
	
	/**
	 * Constructor de la clase "Colegio".
	 * @param id Id del colegio.
	 * @param nombre Nombre del colegio.
	 * @param direccion Direccion del colegio.
	 * @param localidad Localidad del colegio.
	 * @param region Region del colegio.
	 * @param tipo Tipo del colegio.
	 * @param codigo Codigo del colegio.
	 */
	public Colegio(int id, String nombre, String direccion, String localidad, String region, String tipo, int codigo) {
		super();
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.region = region;
        this.tipo = tipo;
        this.codigo = codigo;
    }

	/**
	 * Metodo get para la ID.
	 * @return Entero con la ID del colegio.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo set para la ID.
	 * @param id Id nueva.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo get para el nombre.
	 * @return Nombre del colegio.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo set para el nombre.
	 * @param nombre Nuevo nombre del colegio.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo get para la direccion.
	 * @return Direccion del colegio.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Metodo set para la direccion
	 * @param direccion Direccion nueva del colegio.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Metodo get para la localidad.
	 * @return Localidad del colegio.
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * Metodo set para la localidad.
	 * @param localidad Localidad nueva del colegio.
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * Metodo get para la region.
	 * @return Region del colegio.
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Metodo set para la region.
	 * @param region Region nueva del colegio.
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Metido get para el tipo del colegio.
	 * @return Tipo del colegio.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Metodo set para el tipo del colegio.
	 * @param tipo Tipo nuevo del colegio.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo get para el codigo del colegio.
	 * @return Codigo del colegio.
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Metodo set para el codigo.
	 * @param codigo Codigo nuevo del colegio.
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
