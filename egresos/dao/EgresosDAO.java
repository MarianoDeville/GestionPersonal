package dao;

import modelo.Egreso;

public interface EgresosDAO {

	public String [] getAñosCargados();
	public Egreso [] getListado(String año, int mes, String tipoConsumo, int idFormaPago, String moneda, String filtro);
	public boolean nuevo(Egreso egreso);
	public boolean update(Egreso egreso);
	public boolean delete(Egreso egreso);
	public String [][] getResumen(String año, int mes, String destinos[]);
}
