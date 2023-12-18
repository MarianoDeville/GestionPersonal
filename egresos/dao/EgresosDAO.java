package dao;

import modelo.ClasificacionEgreso;
import modelo.Egreso;
import modelo.Pago;
import modelo.Proveedor;

public interface EgresosDAO {

	public String [] getA�osCargados();
	public ClasificacionEgreso [] getDestino();
	public Pago [] getMetodosPagos();
	public Proveedor [] getListaProveedores(String filtro);
	public Egreso [] getListadoEgresos(String a�o, int mes, int idTipoConsumo, int idFormaPago);
	public boolean nuevoEgreso(Egreso egreso);
	public boolean updateEgreso(Egreso egreso);
}
