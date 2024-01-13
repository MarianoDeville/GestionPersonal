package dao;

import modelo.Fiat;

public interface MercadoFiatDAO {

	public String [] getAñosCargados();
	public Fiat [] getListado(boolean agrupar);
	public boolean newFiat(Fiat fiat);
}