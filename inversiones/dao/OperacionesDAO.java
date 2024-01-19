package dao;

import modelo.Fiat;
import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean create(Operacion operacion);
	public boolean getListado(Valores valor);
	public Fiat [] getListado(String año, int mes);
}