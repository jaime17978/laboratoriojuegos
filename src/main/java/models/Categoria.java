package models;

/**
 * Esta clase se utiliza para guardar datos de la
 * base de datos que tengan el siguiente formato:
 * 
 * -Id: Valor numerico de ID.
 * -Nombre: String con un nombre.
 */
public class Categoria {
    private int id;
    private String nombre;
 
    /**
     * Constructor de la clase Categoria.
     * @param id
     * @param nombre
     */
    public Categoria(int id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
    
    /**
     * Metodo get para la ID.
     * @return ID de la categoria.
     */
    public int getId() {
        return id;
    }
 
    /**
     * Metodo set para la ID.
     * @param id ID nueva de la categoria.
     */
    public void setId(int id) {
        this.id = id;
    }
 
    /**
     * Metodo get para el nombre.
     * @return Nombre de la categoria.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Metodo set para el nombre.
     * @param nombre Nombre nuevo de la categoria.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return String.format("ID: "+id+" Nombre: "+nombre);
    }
}