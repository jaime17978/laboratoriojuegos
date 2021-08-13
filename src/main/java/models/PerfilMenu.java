package models;

/**
 * Clase utilizada para guardar la informacion de un colegio
 * de la base de datos.
 */
public class PerfilMenu {

    private int perfil;
    private int menu;
    private String nombre;
 
    /**
     * Constructor de la clase PerfilMenu.
     * @param perfil Perfil al que hace referencia.
     * @param menu Menu al que hace referencia.
     * @param nombre Nuevo nombre del perfil_menu.
     */
    public PerfilMenu(int perfil, int menu, String nombre) {
    	this.perfil = perfil;
    	this.menu = menu;
    	this.nombre = nombre;
    }
    
    /**
     * Metodo get para el perfil.
     * @return ID del perfil.
     */
    public int getPerfil() {
        return perfil;
    }
 
    /**
     * Metodo set para el perfil.
     * @param perfil Perfil nuevo.
     */
    public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

    /**
     * Metodo get para el menu.
     * @return ID del menu.
     */
	public int getMenu() {
		return menu;
	}

	/**
     * Metodo set para el menu.
     * @param menu Menu nuevo del perfil_menu.
     */
	public void setMenu(int menu) {
		this.menu = menu;
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
    
	
}