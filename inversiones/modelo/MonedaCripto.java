package modelo;

public class MonedaCripto {

	private int id;
	private String nombre;
	private String simbolo;
	private int estable;
	private String descripcion;
	
	public MonedaCripto() {
	
		id = 0;
		nombre = "";
		simbolo = "";
		estable = 0;
		descripcion = "";
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

	public String getSimbolo() {
		
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		
		this.simbolo = simbolo;
	}

	public int getEstable() {
		
		return estable;
	}

	public void setEstable(int estable) {
		
		this.estable = estable;
	}

	public String getDescripcion() {
		
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		
		this.descripcion = descripcion;
	}
}