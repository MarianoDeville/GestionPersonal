package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getAņosCargados();
	public boolean update(Fiat fiat);
	public double getSaldo(Fiat moneda);
}