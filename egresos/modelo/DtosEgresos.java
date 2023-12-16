package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.EgresosDAO;
import dao.EgresosMySQL;

public class DtosEgresos {
	
	private EgresosDAO egresosDAO = new EgresosMySQL();
	private Egreso egresos[];
	private static Pago formasPago[];
	private static Proveedor proveedores[];
	private static ClasificacionEgreso destinos[];
	private Calendar calendario;
	
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = egresosDAO.getAñosCargados();
		
		if(respuesta.length == 0)
			return new String[] {calendario.get(Calendar.YEAR) + ""};

		if(respuesta[0].equals(calendario.get(Calendar.YEAR) + ""))
			return respuesta;
		String temp[] = new String[respuesta.length + 1];
		temp[0] = calendario.get(Calendar.YEAR) + "";
		System.arraycopy(respuesta, 0, temp, 1, respuesta.length);
		return temp;
	}

	public String [] getListaMeses() {
		
		return new String [] {"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public int getMesActual() {
		
		calendario = new GregorianCalendar();
		return calendario.get(Calendar.MONTH) + 1;
	}
	
	public String [] getListaDestinos() {
		
		destinos = egresosDAO.getDestino();
		
		if(destinos == null)
			destinos = new ClasificacionEgreso[0];
		String respuesta[] = new String[destinos.length + 1];
		respuesta[0] = "Todos";
		int i = 1;
		
		for(ClasificacionEgreso destino: destinos) {
			
			respuesta[i] = destino.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public String [] getFormasPago() {
		
		formasPago = egresosDAO.getMetodosPagos();
		
		if(formasPago == null)
			formasPago = new Pago[0];
		String respuesta[] = new String[formasPago.length + 1];
		respuesta[0] = "Todos";
		int i = 1;
		
		for(Pago destino: formasPago) {
			
			respuesta[i] = destino.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public DefaultTableModel getTablaEgresos(String año, int mes, int tipo, int pago) {
		
		
		int idDestino = tipo == 0 ? 0: destinos[tipo].getId();
		int idFormaPago = pago == 0 ? 0: formasPago[pago].getId();
		
		egresos = egresosDAO.getListadoEgresos(año, mes, idDestino, idFormaPago);
		String titulo[] = {"Fecha","Nombre", "Forma de pago", "Monto"};
		String tabla[][] = new String [egresos.length][4];
		
		
		
		
		
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
}
