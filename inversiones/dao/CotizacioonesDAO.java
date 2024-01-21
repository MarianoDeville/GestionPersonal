package dao;

import modelo.Fiat;
import modelo.Valores;

public interface CotizacioonesDAO {

	public int getCotizaciones(String año, int mes, Valores valores[]);
	
	public Fiat [] getCotizaciones(String año, int mes);
	public boolean newCotizacion(Fiat monedas[]);
	public double getUltima(String moneda);
}
