package models;

/**
 * Esta clase se utiliza para guardar datos de la
 * parte de estadisticas.
 */
public class ContadorEst {
	private int contador;
    private String nombre;
 
    /**
     * Constructor de la clase ContadorEst.
     * @param contador Numero del contador.
     * @param nombre Nombre del contador.
     */
    public ContadorEst(int contador, String nombre) {
        this.contador = contador;
        this.nombre = nombre;
    }
 
    /**
     * Metodo get para el contador.
     * @return Numero del contador.
     */
    public int getContador() {
        return contador;
    }
 
    /**
     * Metodo set para el contador.
     * @param contador Numero nuevo para el contador.
     */
    public void setContador(int contador) {
        this.contador = contador;
    }
 
    /**
     * Metodo get para el nombre.
     * @return Nombre del contador.
     */
    public String getNombre() {
        return nombre;
    }
 
    /**
     * Metodo set para el nombre.
     * @param nombre Nombre nuevo del contador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
