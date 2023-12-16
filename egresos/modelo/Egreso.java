package modelo;

public class Egreso {

	private int id;
	private String fecha;
	private float monto;
	private int idProveedor;
	private int idFormaPago;
	private int idTipoConsumo;

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

	public int getIdProveedor() {
		
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		
		this.idProveedor = idProveedor;
	}

	public int getIdFormaPago() {
		
		return idFormaPago;
	}

	public void setIdFormaPago(int idFormaPago) {
		
		this.idFormaPago = idFormaPago;
	}

	public int getIdTipoConsumo() {
		
		return idTipoConsumo;
	}

	public void setIdTipoConsumo(int idTipoConsumo) {
		
		this.idTipoConsumo = idTipoConsumo;
	}
}