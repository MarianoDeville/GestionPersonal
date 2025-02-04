package dao;

import modelo.Inmobiliario;

public interface MercadoInmobiliarioDAO {

	public String [] getAñosCargados();
	public Inmobiliario [] getListado(String filtro); 
}
