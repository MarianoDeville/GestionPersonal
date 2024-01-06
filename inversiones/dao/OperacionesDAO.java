package dao;

import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean newCompra(Operacion operacion);
	public boolean newVenta(Operacion operacion);
	public boolean getListaOperaciones(Valores valor);
}