package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getAņosCargados();
	public boolean newMovimiento(Fiat fiat);
	public double getSaldo(Fiat moneda);
}