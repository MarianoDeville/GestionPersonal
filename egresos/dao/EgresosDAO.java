package dao;

import modelo.ClasificacionEgreso;
import modelo.Egreso;

public interface EgresosDAO {

	public String [] getAñosCargados();
	public ClasificacionEgreso [] getDestino();
	public Egreso [] getListado(String año, int mes, int idTipoConsumo, int idFormaPago, String moneda, String filtro);
	public boolean nuevo(Egreso egreso);
	public boolean update(Egreso egreso);
	public boolean delete(Egreso egreso);
	public String [][] getResumen(String año, int mes, ClasificacionEgreso destinos[]);
}
