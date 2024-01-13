package dao;

import modelo.Fiat;
import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean newCompra(Operacion operacion);
	public boolean newVenta(Operacion operacion);
	public boolean getListado(Valores valor);
	public int getListado(String año, int mes, Fiat monedas[]);
}