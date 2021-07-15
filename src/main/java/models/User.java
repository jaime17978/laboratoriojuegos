package models;

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
    

    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String e){
        email = e;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String p){
        password = p;
    }
    
    public int getPermissions() {
    	return permissions;
    }
    
    public void setPermissions(int i) {
    	permissions = i;
    }

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public int getUniversidad() {
		return universidad;
	}

	public void setUniversidad(int universidad) {
		this.universidad = universidad;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
