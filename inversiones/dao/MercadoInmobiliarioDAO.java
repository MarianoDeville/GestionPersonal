package dao;

import modelo.Inmobiliario;

public interface MercadoInmobiliarioDAO {

	public String [] getA�osCargados();
	public Inmobiliario [] getListado(String filtro); 
}
