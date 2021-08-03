package models;

/**
 * Esta clase se utiliza para guardar datos de la
 * tabla perfil de la base de datos.
 */
public class Perfil {
    private int id;
    private String nombre;
    private boolean activo;
 
    /**
     * Constructor de la clase Perfil.
     * @param id Clave primaria del nuevo perfil.
     * @param activo Si el nuevo perfil esta activo.
     * @param nombre Nuevo nombre del perfil.
     */
    public Perfil(int id, String nombre, boolean activo) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }
    
    /**
     * Metodo get para la ID.
     * @return ID del perfil.
     */
    public int getId() {
        return id;
    }
 
    /**
     * Metodo set para la ID.
     * @param id ID nueva del perfil.
     */
    public void setId(int id) {
        this.id = id;
    }
 
    /**
     * Metodo get para el nombre.
     * @return Nombre del perfil.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Metodo set para el nombre.
     * @param nombre Nombre nuevo del perfil.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Metodo get para el estado activo
     * @return Si el perfil esta activo o no.
     */
    public boolean isActivo() {
		return activo;
	}

    /**
     * Metodo set para el estado de activo.
     * @param activo Nuevo estado de activo del perfil.
     */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
    @Override
    public String toString() {
        return String.format("ID: "+id+" Nombre: "+nombre);
    }
	
}