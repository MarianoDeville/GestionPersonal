package modelo;

public class Cotizacion {

	private int id;
	private String fecha;
	private double valor;

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}

	public double getValor() {
		
		return valor;
	}

	public void setValor(double valor) {
		
		this.valor = valor;
	}
}