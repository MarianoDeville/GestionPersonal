package modelo;

public class Moneda {

	private int id;
	private String nombre;
	
	public Moneda() {
		
		id = 0;
		nombre = "";
	}
	
	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
}
