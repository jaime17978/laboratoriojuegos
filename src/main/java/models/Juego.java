package models;

import java.util.Objects;

/**
 * Clase utilizada para guardar los datos de un juego
 * de la base de datos.
 */
public class Juego {
	private int id;
    private String name;
    private int type;
    
    /**
     * Constructor de la clase juego.
     * @param i ID del juego.
     * @param n Nombre del juego.
     * @param t tipo de actividad del juego.
     */
    public Juego(int i, String n, int t) {
		id = i;
		name = n;
		type = t;
	}

    /**
     * Metodo get para la ID.
     * @return ID del juego.
     */
	public int getId(){
        return id;
    }

	/**
	 * Metodo set para la ID.
	 * @param i ID nueva del juego.
	 */
    public void setId(int i){
        id = i;
    }

    /**
     * Metodo get para el tipo.
     * @return tipo del juego.
     */
	public int getType(){
        return type;
    }

	/**
	 * Metodo set para el tipo.
	 * @param t tipo nuevo del juego.
	 */
    public void setType(int t){
        type = t;
    }
    
    /**
     * Metodo get para el nombre.
     * @return Nombre del juego.
     */
    public String getName(){
        return name;
    }

    /**
     * Metodo ser para el nombre.
     * @param n Nombre nuevo del juego.
     */
    public void setName(String n){
        name = n;
    }
    
    @Override
    public boolean equals(Object o) {
    	Juego j = (Juego) o;
        return j.id == this.id && j.name.equals(this.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
