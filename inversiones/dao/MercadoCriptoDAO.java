package dao;

import modelo.Cripto;

public interface MercadoCriptoDAO {

	public String [] getAñosCargados();
	public boolean update(Cripto cripto);
	public double getSaldo(Cripto moneda);
}