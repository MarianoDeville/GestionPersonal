package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getA�osCargados();
	public boolean update(Fiat fiat);
	public double getSaldo(Fiat moneda);
}