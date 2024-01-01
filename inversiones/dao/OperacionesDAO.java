package dao;

import modelo.Operacion;

public interface OperacionesDAO {

	public boolean newCompra(Operacion operacion);
	public boolean newVenta(Operacion operacion);
}