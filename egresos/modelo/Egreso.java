package modelo;

public class Egreso {

	private int id;
	private String fecha;
	private float monto;
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

	public float getMonto() {
		
		return monto;
	}

	public void setMonto(float monto) {
		
		this.monto = monto;
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