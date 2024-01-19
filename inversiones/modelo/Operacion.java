package modelo;

public class Operacion {

	private int id;
	private String fecha;
	private String operacion;
	private double cant;
	private double precio;
	private double comision;	
	private String comentario;
	private int idValores;
	private int idCripto;
	private int idFiat;
	private int idInmobiliario;
	private int idEgreso;
	private int idIngreso;

	public Operacion() {
	
		id = 0;
		fecha = "";
		operacion = "";
		comentario = "";
		cant = 0;
		precio = 0;
		comision = 0;
		idValores = 0;
		idCripto = 0;
		idFiat = 0;
		idInmobiliario = 0;
		idEgreso = 0;
		idIngreso = 0;
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

	public String getOperacion() {
		
		return operacion;
	}

	public void setOperacion(String operacion) {
		
		this.operacion = operacion;
	}

	public double getCant() {
		
		return cant;
	}

	public void setCant(double cant) {
		
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

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}

	public int getIdValores() {
		
		return idValores;
	}

	public void setIdValores(int idInversion) {
		
		this.idValores = idInversion;
	}

	public int getIdCripto() {
		
		return idCripto;
	}

	public void setIdCripto(int idCripto) {
		
		this.idCripto = idCripto;
	}

	public int getIdFiat() {
		
		return idFiat;
	}

	public void setIdFiat(int idFiat) {
		
		this.idFiat = idFiat;
	}

	public int getIdInmobiliario() {
		
		return idInmobiliario;
	}

	public void setIdInmobiliario(int idInmobiliario) {
		
		this.idInmobiliario = idInmobiliario;
	}

	public int getIdEgreso() {
		
		return idEgreso;
	}

	public void setIdEgreso(int idEgreso) {
		
		this.idEgreso = idEgreso;
	}

	public int getIdIngreso() {
		
		return idIngreso;
	}

	public void setIdIngreso(int idIngreso) {
		
		this.idIngreso = idIngreso;
	}
}
