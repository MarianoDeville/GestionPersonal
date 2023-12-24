package modelo;

public class Ingreso {

	private int id;
	private String fecha;
	private double monto;
	private String moneda;
	private float cotizacion;
	private String comentario;
	private Concepto concepto;
	private Fuente fuente;
	private Cobro formaCobro;

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

	public float getCotizacion() {
		
		return cotizacion;
	}

	public void setCotizacion(float cotizacion) {
		
		this.cotizacion = cotizacion;
	}

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}

	public Concepto getConcepto() {
		
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		
		this.concepto = concepto;
	}

	public Fuente getFuente() {
		
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		
		this.fuente = fuente;
	}

	public Cobro getFormaCobro() {
		
		return formaCobro;
	}

	public void setFormaCobro(Cobro formaCobro) {
		
		this.formaCobro = formaCobro;
	}
}