package modelo;

public class Operacion {

	private int id;
	private String fecha;
	private String operacion;
	private int cant;
	private double precio;
	private double comision;

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

	public String getOperacion() {
		
		return operacion;
	}

	public void setOperacion(String operacion) {
		
		this.operacion = operacion;
	}

	public int getCant() {
		
		return cant;
	}

	public void setCant(int cant) {
		
		this.cant = cant;
	}

	public double getPrecio() {
		
		return precio;
	}

	public void setPrecio(double precio) {
		
		this.precio = precio;
	}

	public double getComision() {
		
		return comision;
	}

	public void setComision(double comision) {
		
		this.comision = comision;
	}
}
