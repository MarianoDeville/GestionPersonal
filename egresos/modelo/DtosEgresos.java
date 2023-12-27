package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.EgresosDAO;
import dao.EgresosMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosEgresos {
	
	private EgresosDAO egresosDAO = new EgresosMySQL();
	private Egreso egresos[];
	private static Egreso egreso;
	private static Transaccion formasPago[];
	private static Proveedor proveedores[];
	private static ClasificacionEgreso destinos[];
	private Calendar calendario;
	private String msgError;
	private double suma;
	private int cantidadElementos;
	private static String a�o;
	private static int mes;
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	
	public String [] getListaA�os() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = egresosDAO.getA�osCargados();
		
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
	
	public String [] getListaDestinos(String cabecera) {
		
		if(destinos == null || destinos.length < 1) {
			destinos = egresosDAO.getDestino();
			
			if(destinos == null)
				destinos = new ClasificacionEgreso[0];
		}
		String respuesta[] = new String[destinos.length + 1];
		respuesta[0] = cabecera;
		int i = 1;
		
		for(ClasificacionEgreso destino: destinos) {
			
			respuesta[i] = destino.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public String [] getFormasPago(String cabecera) {
		
		if(formasPago == null || formasPago.length < 1) {

			TransaccionDAO transaccionDAO = new TransaccionMySQL();
			formasPago = transaccionDAO.getMetodos("E");
			
			if(formasPago == null)
				formasPago = new Transaccion[0];
		}
		String respuesta[] = new String[formasPago.length + 1];
		respuesta[0] = cabecera;
		int i = 1;
		
		for(Transaccion pago: formasPago) {
			
			respuesta[i] = pago.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public DefaultTableModel getTablaEgresos(String a�o, int mes, int tipo, int pago, String monedas, String filtro, boolean financiado) {
		
		int idDestino = (tipo == 0 ? 0: destinos[tipo - 1].getId());
		int idFormaPago = pago == 0 ? 0: formasPago[pago - 1].getId();
		suma = 0;
		cantidadElementos = 0;
		egresos = egresosDAO.getListado(a�o, mes, idDestino, idFormaPago, monedas, filtro);
		String titulo[] = {"Fecha", "Nombre", "Forma de pago", "D�lares", "Euros", "Monto en pesos"};
		String tabla[][] = null;
		
		if(egresos != null) {
		
			tabla = new String [egresos.length][6];
			
			for(int i = 0; i < tabla.length; i++) {
			
				tabla[i][0] = egresos[i].getFecha();
				tabla[i][1] = egresos[i].getProveedor().getNombre();
				tabla[i][2] = egresos[i].getFormaPago().getDescripcion();
				
				if(egresos[i].getMoneda().equals("Pesos")) {
					
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto());
					
					if(financiado || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto();
				} else if(egresos[i].getMoneda().equals("D�lares")) {
					
					tabla[i][3] = formatoResultado.format(egresos[i].getMonto());
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto() * egresos[i].getCotizacion());
					
					if(financiado || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto() * egresos[i].getCotizacion();
				}else if(egresos[i].getMoneda().equals("Euros")) {
					
					tabla[i][4] = formatoResultado.format(egresos[i].getMonto());
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto() * egresos[i].getCotizacion());
					
					if(financiado || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto() * egresos[i].getCotizacion();
				}
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		cantidadElementos = tabla.length;
		return tablaModelo;
	}
	
	public String getFechaActual() {
		
		calendario = new GregorianCalendar();
		return calendario.get(Calendar.DAY_OF_MONTH) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
	}
	
	public DefaultTableModel getListaProveedores(String filtro) {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		proveedores = proveedoresDAO.getListado(filtro, "E");
		String tabla[][] = new String [proveedores.length][1];
		int i = 0;
		
		for(Proveedor prov: proveedores) {
			
			tabla[i][0] = prov.getNombre();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre empresa"});
		return tablaModelo;
	}

	public void setEgreso(Egreso info) {
		
		DtosEgresos.egreso = info;
	}
	
	public void setProveedor(int pos) {
		
		if(egreso == null)
			egreso = new Egreso();
		egreso.setProveedor(new Proveedor());
		egreso.getProveedor().setId( proveedores[pos].getId() );
	}
	
	public boolean setMonto(String monto) {
		
		try {
			
			egreso.setMonto(Float.parseFloat(monto.replace(",", ".")));
		} catch (Exception e) {
			
			msgError = "El monto debe ser num�rico.";
			return false;
		}
		return true;
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean setFecha(String fecha) {
		
		if(egreso == null)
			egreso = new Egreso();
		calendario = new GregorianCalendar();
		msgError = "El formato de la fecha es incorrecto, debe ser DD/MM/AAAA";
		
		try {

			String partes[] = fecha.split(fecha.contains("/")?"/":"-");

			if(Integer.parseInt(partes[0]) < 1 || Integer.parseInt(partes[0]) > 31) {
				
				msgError = "El valor del d�a est� comprendido entre 1 y 31";
				return false;
			}
			
			if(Integer.parseInt(partes[1]) < 1 || Integer.parseInt(partes[1]) > 12) {
				
				msgError = "El valor del mes est� comprendido entre 1 y 12";
				return false;
			}
			egreso.setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);	
		} catch(Exception e) {
			
			return false;
		}
		return true;
	}
		
	public boolean setFormaPago(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir una forma de pago.";
			return false;
		}
		egreso.setFormaPago(new Transaccion());
		egreso.getFormaPago().setId(formasPago[pos - 1].getId());
		return true;
	}
	
	public boolean setDestino(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe definir el tipo de gasto.";
			return false;
		}
		egreso.setTipoConsumo(new ClasificacionEgreso());
		egreso.getTipoConsumo().setId(destinos[pos - 1].getId());
		return true;
	}

	public boolean guardarEgreso() {
		
		if(egreso.getProveedor().getId() < 1) {
			
			msgError = "El proveedor no est� definido.";
			return false;
		}
		
		if(egreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!egresosDAO.nuevo(egreso)) {
		
			msgError = "Error al intentar guardar la informaci�n en la base de datos.";
			return false;
		}
		msgError = "La informaci�n se ha guardado.";
		return true;
	}

	public String getSuma() {
		
		return formatoResultado.format(suma);
	}

	public String getCantidadElementos() {
		
		return cantidadElementos + "";
	}
	
	public void seleccionarEgreso(int pos) {
		
		egreso = egresos[pos];
	}
	
	public String getFormaPagoSeleccionada() {
		
		return egreso.getFormaPago().getDescripcion();
	}
	
	public String getDestinoConsumo() {
		
		return egreso.getTipoConsumo().getDescripcion();
	}
	
	public String getFecha() {
		
		return egreso.getFecha();
	}
	
	public String getProveedor() {
		
		return egreso.getProveedor().getNombre();
	}
	
	public String getMonto() {
		
		return String.format("%.2f", egreso.getMonto());
	}
	
	public String getMoneda() {
		
		return egreso.getMoneda();
	}
	
	public String getCotizacion() {
		
		return String.format("%.2f", egreso.getCotizacion());
	}
	
	public boolean actualizarEgreso() {
		
		if(egreso.getProveedor().getId() < 1) {
			
			msgError = "El proveedor no est� definido.";
			return false;
		}
		
		if(egreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!egresosDAO.update(egreso)) {
		
			msgError = "Error al intentar guardar la informaci�n en la base de datos.";
			return false;
		}
		msgError = "La informaci�n se ha guardado.";
		return true;
	}
	
	public void setMoneda(String moneda) {
		
		if(egreso == null)
			egreso = new Egreso();
		egreso.setMoneda(moneda);
	}
	
	public String getComentario() {
		
		return egreso.getComentario();
	}
	
	public void setComentario(String comentario) {
		
		egreso.setComentario(comentario);
	}
	
	public boolean setCotizacion(String cotizacion) {
		
		if(egreso.getMoneda().equals("Pesos")) {
			
			egreso.setCotizacion(0);
			return true;
		}
		
		try {

			egreso.setCotizacion(Float.parseFloat(cotizacion));
		} catch (Exception e) {
			
			msgError = "La cotizaci�n debe ser num�rica.";
			return false;
		}
		return true;
	}
	
	public boolean borrarEgreso() {
		
		if(!egresosDAO.delete(egreso)) {
		
			msgError = "Error al intentar eliminar la informaci�n de la base de datos.";
			return false;
		}
		msgError = "La informaci�n se ha eliminado.";
		return true;
	}
	
	public void setA�o(String a�o) {
		
		DtosEgresos.a�o = a�o;
	}
	
	public void setMes(int mes) {
		
		DtosEgresos.mes = mes;
	}
	
	public DefaultTableModel getResumen() {
		
		String titulo[] = {"Concepto", "Monto"};
		String tabla[][] = egresosDAO.getResumen(a�o, mes, destinos);
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
}