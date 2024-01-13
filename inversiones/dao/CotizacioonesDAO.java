package dao;

import modelo.Fiat;
import modelo.Valores;

public interface CotizacioonesDAO {

	public int getCotizaciones(String año, int mes, Valores valores[]);
	public int getCotizaciones(String año, int mes, Fiat monedas[]);
}
