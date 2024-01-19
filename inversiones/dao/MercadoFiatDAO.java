package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getA�osCargados();
	public boolean newMovimiento(Fiat fiat);
	public double getSaldo(Fiat moneda);
}