package models;

import java.util.Objects;

public class Juego {
	private int id;
    private String name;

    public Juego(int i, String n) {
		id = i;
		name = n;
	}

	public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public String getName(){
        return name;
    }

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
