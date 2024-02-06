package dao;

import modelo.Cripto;
import modelo.Fiat;
import modelo.Moneda;
import modelo.MonedaCripto;
import modelo.Valores;

public interface CotizacioonesDAO {

	public int getCotizaciones(String a�o, int mes, Valores valores[]);
	public Fiat [] getCotizacionesFiat(String a�o, int mes);
	public Cripto [] getCotizacionesCripto(String a�o, int mes);
	public boolean update(Fiat monedas[]);
	public boolean update(Cripto monedas[]);
	public double getUltima(String moneda);
	public Moneda [] getMonedas();
	public MonedaCripto [] getMonedasCripto();
}
