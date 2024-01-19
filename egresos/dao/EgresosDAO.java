package dao;

import modelo.Egreso;

public interface EgresosDAO {

	public String [] getA�osCargados();
	public Egreso [] getListado(String a�o, int mes, String tipoConsumo, int idFormaPago, String moneda, String filtro);
	public boolean nuevo(Egreso egreso);
	public boolean update(Egreso egreso);
	public boolean delete(Egreso egreso);
	public String [][] getResumen(String a�o, int mes, String destinos[]);
}
