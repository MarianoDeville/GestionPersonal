package dao;

import modelo.Cripto;

public interface MercadoCriptoDAO {

	public String [] getA�osCargados();
	public boolean update(Cripto cripto);
	public double getSaldo(Cripto moneda);
}