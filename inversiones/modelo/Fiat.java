package modelo;

public class Fiat {

	private int id;
	private String moneda;
	private double cant;
	private Proveedor custodia;
	private Operacion operaciones[];
	private Cotizacion cotizaciones[];
	
	public Fiat() {
		
		id = 0;
		moneda = "";
		cant = 0;		
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}

	public String getMoneda() {
		
		return moneda;
	}

	public void setMoneda(String moneda) {
		
		this.moneda = moneda;
	}

	public double getCant() {
		
		return cant;
	}

	public void setCant(double cant) {
		
		this.cant = cant;
	}

	public Proveedor getCustodia() {
		
		return custodia;
	}

	public void setCustodia(Proveedor custodia) {
		
		this.custodia = custodia;
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
