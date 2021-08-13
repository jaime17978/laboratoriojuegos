package models;

/**
 * Clase utilizada para guardar los datos de los
 * usuarios de la base de datos (tabla "Usuarios").
 */
public class User {
    private int id;
    private String email;
    private String password;
    private int permissions;
    private int language;
    //Parte del usuario que se utiliza en la pantalla de administrador
    private int perfil;
    private int universidad;
    private boolean activo;
    //Parte del usuario que se usa para la simulacion de un alumno desde el perfil profesor
    private int simulado = -1;
    
    /**
     * Metodo get para la ID.
     * @return ID del usuario.
     */
    public int getId(){
        return id;
    }
    
    /**
     * Metodo set para la ID.
     * @param i ID nueva del usuario.
     */
    public void setId(int i){
        id = i;
    }

    /**
     * Metodo get para el email.
     * @return Email del usuario.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Metodo set para el email.
     * @param e Email nuevo del usuario.
     */
    public void setEmail(String e){
        email = e;
    }

    /**
     * Metodo get para la contraseña.
     * @return Contraseña del usuario.
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Metodo set para la contraseña.
     * @param p Contraseña nueva del usuario.
     */
    public void setPassword(String p){
        password = p;
    }
    
    /**
     * Metodo get para el nivel de permiso.
     * @return Permisos del usuario.
     */
    public int getPermissions() {
    	return permissions;
    }
    
    /**
     * Metodo set para el nivel de permiso.
     * @param i Nivel de permiso nuevo del usuario.
     */
    public void setPermissions(int i) {
    	permissions = i;
    }

    /**
     * Metodo get para el idioma.
     * @return Idioma del usuario.
     */
	public int getLanguage() {
		return language;
	}

	/**
	 * Metodo set para el idioma del usuario.
	 * @param language Idioma nuevo del usuario.
	 */
	public void setLanguage(int language) {
		this.language = language;
	}

	/**
	 * Metodo get para el perfil del usuario.
	 * @return Perfil del usuario.
	 */
	public int getPerfil() {
		return perfil;
	}

	/**
	 * Metodo set para el perfil del usuario.
	 * @param perfil Perfil nuevo del usuario.
	 */
	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	/**
	 * Metodo get para la universidad.
	 * @return Universidad del usuario.
	 */
	public int getUniversidad() {
		return universidad;
	}

	/**
	 * Metodo set para la universidad.
	 * @param universidad Universidad del usuario.
	 */
	public void setUniversidad(int universidad) {
		this.universidad = universidad;
	}
	
	/**
	 * Metodo get para el estado de activo.
	 * @return Estado de activo del usuario (boolean).
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Metodo set para el estado de activo.
	 * @param activo Booleano con el estado de activo nuevo del usuario.
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/**
     * Metodo get para la ID del usuario a simular.
     * @return ID del usuario.
     */
    public int getSimulado(){
        return simulado;
    }
    
    /**
     * Metodo set para la ID del usuario a simular.
     * @param s ID nueva del usuario simulado.
     */
    public void setSimulado(int s){
        simulado = s;
    }
}
