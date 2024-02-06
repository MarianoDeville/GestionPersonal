package modelo;

public class Cripto {

	private int id;
	private double cant;
	private MonedaCripto moneda;
	private Proveedor custodia;
	private Cotizacion cotizaciones[];
	private Operacion operaciones[];
	
	public Cripto() {
		
		id = 0;
		cant = 0;
		moneda = new MonedaCripto();
		custodia = new Proveedor();
	}
	
	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public double getCant() {
		
		return cant;
	}

	public void setCant(double cant) {
		
		this.cant = cant;
	}

	public MonedaCripto getMoneda() {
		
		return moneda;
	}

	public void setMoneda(MonedaCripto moneda) {
		
		this.moneda = moneda;
	}

	public Proveedor getCustodia() {
		
		return custodia;
	}

	public void setCustodia(Proveedor custodia) {
		
		this.custodia = custodia;
	}

	public Cotizacion[] getCotizaciones() {
		
		return cotizaciones;
	}

	public void setCotizaciones(Cotizacion[] cotizaciones) {
		
		this.cotizaciones = cotizaciones;
	}

	public Operacion[] getOperaciones() {
		
		return operaciones;
	}

	public void setOperaciones(Operacion[] operaciones) {
		
		this.operaciones = operaciones;
	}
}