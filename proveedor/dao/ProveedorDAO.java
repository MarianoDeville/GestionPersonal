package dao;

import modelo.Proveedor;

public interface ProveedorDAO {

	public Proveedor [] getListado(String filtro, String egresoIngreso);
}