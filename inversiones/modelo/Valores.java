package modelo;

public class Valores {

	private int id;
	private String nombre;
	private double cant;
	private Proveedor custodia;
	private Operacion operaciones[];
	private Cotizacion cotizaciones[];
	private Instrumento instrumento;
	private int plazo;

	public Valores() {
		
		id = 0;
		nombre = "";
		cant = 0;
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

	public Instrumento getInstrumento() {
		
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		
		this.instrumento = instrumento;
	}
	
	public int getPlazo() {
		
		return plazo;
	}

	public void setPlazo(int plazo) {
		
		this.plazo = id;
	}
}