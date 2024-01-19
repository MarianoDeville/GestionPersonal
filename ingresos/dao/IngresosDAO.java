package dao;

import modelo.Ingreso;

public interface IngresosDAO {

	public String [] getA�osCargados();
	public Ingreso [] getListado(String a�o, int mes, String concepto, int idFormaCobro, String moneda, String filtro);
	public boolean nuevo(Ingreso egreso);
	public boolean update(Ingreso ingreso);
	public boolean delete(Ingreso ingreso);
}
