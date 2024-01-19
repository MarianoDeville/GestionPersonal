package dao;

import modelo.Transaccion;

public interface TransaccionDAO {

	public Transaccion [] getMetodos(String egresoIngreso);
	public Transaccion getId(String nombre);
}
