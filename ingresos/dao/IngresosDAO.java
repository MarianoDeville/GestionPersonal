package dao;

import modelo.Concepto;
import modelo.Ingreso;

public interface IngresosDAO {

	public String [] getAñosCargados();
	public Concepto [] getConcepto();
	public Ingreso [] getListado(String año, int mes, int idConcepto, int idFormaCobro, String moneda, String filtro);
	public boolean nuevo(Ingreso egreso);
	public boolean update(Ingreso ingreso);
	public boolean delete(Ingreso ingreso);
}
