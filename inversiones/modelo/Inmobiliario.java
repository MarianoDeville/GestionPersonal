package modelo;

public class Inmobiliario {

	private int id;
	private String descripcion;
	private String lugar;
	private Proveedor operador;
	private Operacion operaciones[];

	public Inmobiliario() {
		
		id = 0;
		descripcion = "";
		lugar = "";
	}
	
	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public String getDescripcion() {
		
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		
		this.descripcion = descripcion;
	}

	public String getLugar() {
		
		return lugar;
	}

	public void setLugar(String lugar) {
		
		this.lugar = lugar;
	}

	public Proveedor getOperador() {
		
		return operador;
	}

	public void setOperador(Proveedor operador) {
		
		this.operador = operador;
	}

	public Operacion[] getOperaciones() {
		
		return operaciones;
	}

	public void setOperaciones(Operacion[] operaciones) {
		
		this.operaciones = operaciones;
	}
}