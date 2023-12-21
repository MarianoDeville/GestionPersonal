package modelo;

public class Inversion {

	private int id;
	private String nombre;
	private int cant;
	private String custodia;
	private String mercado;
	private Operacion operaciones[];
	private Cotizacion cotizaciones[];

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

	public int getCant() {
		
		return cant;
	}

	public void setCant(int cant) {
		
		this.cant = cant;
	}

	public String getCustodia() {
		
		return custodia;
	}

	public void setCustodia(String custodia) {
		
		this.custodia = custodia;
	}

	public String getMercado() {
		
		return mercado;
	}

	public void setMercado(String mercado) {
		
		this.mercado = mercado;
	}

	public Operacion[] getOperaciones() {
		
		return operaciones;
	}

	public void setOperaciones(Operacion[] operaciones) {
		
		this.operaciones = operaciones;
	}

	public Cotizacion[] getCotizaciones() {
		
		return cotizaciones;
	}

	public void setCotizaciones(Cotizacion[] cotizaciones) {
		
		this.cotizaciones = cotizaciones;
	}
}