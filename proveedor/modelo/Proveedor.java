package modelo;

public class Proveedor {

	private int id;
	private String nombre;
	private String direccion;
	private String cuit;
	private String comentario;
	private String mercado;

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

	public String getDireccion() {
		
		return direccion;
	}

	public void setDireccion(String direccion) {
		
		this.direccion = direccion;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		
		this.cuit = cuit;
	}

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}

	public String getMercado() {
		
		return mercado;
	}

	public void setMercado(String mercado) {
		
		this.mercado = mercado;
	}
}