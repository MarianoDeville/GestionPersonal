package modelo;

public class Egreso {

	private int id;
	private String fecha;
	private double monto;
	private String moneda;
	private double cotizacion;
	private String comentario;
	private String tipoConsumo;
	private Proveedor proveedor;
	private Transaccion formaPago;
	
	public Egreso() {
	
		id = 0;
		fecha = "";
		monto = 0;
		moneda = "";
		cotizacion = 0;
		comentario = "";
		tipoConsumo = "";
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

	public Proveedor getProveedor() {
		
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		
		this.proveedor = proveedor;
	}

	public Transaccion getFormaPago() {
		
		return formaPago;
	}

	public void setFormaPago(Transaccion formaPago) {
		
		this.formaPago = formaPago;
	}

	public String getTipoConsumo() {
		
		return tipoConsumo;
	}

	public void setTipoConsumo(String tipoConsumo) {
		
		this.tipoConsumo = tipoConsumo;
	}
}