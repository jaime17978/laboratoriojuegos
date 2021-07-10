package models;

public class ContadorEst {
	private int contador;
    private String nombre;
 
    public ContadorEst(int contador, String nombre) {
        this.contador = contador;
        this.nombre = nombre;
    }
 
    public int getContador() {
        return contador;
    }
 
    public void setContador(int contador) {
        this.contador = contador;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
