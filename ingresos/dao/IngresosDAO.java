package dao;

import modelo.Cobro;
import modelo.Concepto;
import modelo.Fuente;
import modelo.Ingreso;

public interface IngresosDAO {

	public String [] getA�osCargados();
	public Concepto [] getConcepto();
	public Cobro [] getMetodosCobros();
	public Ingreso [] getListadoIngresos(String a�o, int mes, int idConcepto, int idFormaCobro, String moneda, String filtro);
	public Fuente [] getListaFuentes(String filtro);
	public boolean nuevoIngreso(Ingreso egreso);
	public boolean updateIngreso(Ingreso ingreso);
}
