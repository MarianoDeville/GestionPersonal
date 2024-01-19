package dao;

import modelo.Ingreso;

public interface IngresosDAO {

	public String [] getAñosCargados();
	public Ingreso [] getListado(String año, int mes, String concepto, int idFormaCobro, String moneda, String filtro);
	public boolean nuevo(Ingreso egreso);
	public boolean update(Ingreso ingreso);
	public boolean delete(Ingreso ingreso);
}
