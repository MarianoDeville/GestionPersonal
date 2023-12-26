package modelo;

public class Valores {

	private int id;
	private String nombre;
	private int cant;
	private Proveedor custodia;
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