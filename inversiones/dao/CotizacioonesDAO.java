package dao;

import modelo.Fiat;
import modelo.Valores;

public interface CotizacioonesDAO {

	public int getCotizaciones(String a�o, int mes, Valores valores[]);
	public int getCotizaciones(String a�o, int mes, Fiat monedas[]);
}
