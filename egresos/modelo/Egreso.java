package modelo;

public class Egreso {

	private int id;
	private String fecha;
	private double monto;
	private String moneda;
	private float cotizacion;
	private String comentario;
	private Proveedor proveedor;
	private Pago formaPago;
	private ClasificacionEgreso tipoConsumo;

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

	public Proveedor getProveedor() {
		
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		
		this.proveedor = proveedor;
	}

	public Pago getFormaPago() {
		
		return formaPago;
	}

	public void setFormaPago(Pago formaPago) {
		
		this.formaPago = formaPago;
	}

	public ClasificacionEgreso getTipoConsumo() {
		
		return tipoConsumo;
	}

	public void setTipoConsumo(ClasificacionEgreso tipoConsumo) {
		
		this.tipoConsumo = tipoConsumo;
	}
}