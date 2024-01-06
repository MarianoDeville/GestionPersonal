package modelo;

public class Cotizacion {

	private int id;
	private String fecha;
	private double valor;
	private int idValores;
	private int idCripto;
	private int idFiat;
	
	public Cotizacion() {
	
		id = 0;
		fecha = "";
		valor = 0;
		idValores = 0;
		idCripto = 0;
		idFiat = 0;
	}
	
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

	public int getIdValores() {
		
		return idValores;
	}

	public void setIdValores(int idValores) {
		
		this.idValores = idValores;
	}

	public int getIdCripto() {
		
		return idCripto;
	}

	public void setIdCripto(int idCripto) {
		
		this.idCripto = idCripto;
	}

	public int getIdFiat() {
		
		return idFiat;
	}

	public void setIdFiat(int idFiat) {
		
		this.idFiat = idFiat;
	}
}