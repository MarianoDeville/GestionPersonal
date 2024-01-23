package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getAñosCargados();
	public boolean update(Fiat fiat);
	public double getSaldo(Fiat moneda);
}