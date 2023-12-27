package modelo;

public class Transaccion {

	private int id;
	private String descripcion;
	private int financiado;

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

	public int getFinanciado() {
		
		return financiado;
	}

	public void setFinanciado(int financiado) {
		
		this.financiado = financiado;
	}
}