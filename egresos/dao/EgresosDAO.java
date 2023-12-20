package dao;

import modelo.ClasificacionEgreso;
import modelo.Egreso;
import modelo.Pago;
import modelo.Proveedor;

public interface EgresosDAO {

	public String [] getAñosCargados();
	public ClasificacionEgreso [] getDestino();
	public Pago [] getMetodosPagos();
	public Proveedor [] getListaProveedores(String filtro);
	public Egreso [] getListadoEgresos(String año, int mes, int idTipoConsumo, int idFormaPago, String moneda, String filtro);
	public boolean nuevoEgreso(Egreso egreso);
	public boolean updateEgreso(Egreso egreso);
}
