package modelo;

public class Ingreso {

	private int id;
	private String fecha;
	private double monto;
	private String moneda;
	private double cotizacion;
	private String comentario;
	private String concepto;
	private Proveedor fuente;
	private Transaccion formaCobro;

	public Ingreso() {
		
		id = 0;
		fecha = "";
		monto = 0;
		moneda = "";
		cotizacion = 0;
		comentario = "";
		concepto = "";
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

	public double getMonto() {
		
		return monto;
	}

	public void setMonto(double monto) {
		
		this.monto = monto;
	}

	public String getMoneda() {
		
		return moneda;
	}

	public void setMoneda(String moneda) {
		
		this.moneda = moneda;
	}

	public double getCotizacion() {
		
		return cotizacion;
	}

	public void setCotizacion(double cotizacion) {
		
		this.cotizacion = cotizacion;
	}

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}

	public String getConcepto() {
		
		return concepto;
	}

	public void setConcepto(String concepto) {
		
		this.concepto = concepto;
	}

	public Proveedor getFuente() {
		
		return fuente;
	}

	public void setFuente(Proveedor fuente) {
		
		this.fuente = fuente;
	}

	public Transaccion getFormaCobro() {
		
		return formaCobro;
	}

	public void setFormaCobro(Transaccion formaCobro) {
		
		this.formaCobro = formaCobro;
	}
}