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
	private static String destinos[];
	private Calendar calendario;
	private String msgError;
	private double suma;
	private int cantidadElementos;
	private static String año;
	private static int mes;
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	
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

	public String [] getListaDestinos(String cabecera) {
		
		destinos = new String[] {
				"Canasta básica",
				"Vestimenta",
				"Impuestos",
				"Servicios",
				"Salud",
				"Ahorro / Inversión",
				"Viajes",
				"Lujo / Superfluo",
				"Ocio / Esparcimiento",
				"Estudio / Capacitación",
				"Varios",
				"Pichos",
				"Tarjetas",
				"Comisiones / Mantenimiento", 
				"Préstamo"
		};
		String respuesta[] = new String[destinos.length + 1]; 
		respuesta[0] = cabecera;
		System.arraycopy(destinos, 0, respuesta, 1, destinos.length);
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
	
	public boolean isFinanciado(int seleccion) {
		
		if(seleccion == 0)
			return false;
		
		if(formasPago[seleccion-1].getFinanciado() == 0)
			return false;
		return true;
	}
	
	public DefaultTableModel getTablaEgresos(String año, int mes, String tipo, int pago, String monedas, String filtro, boolean diferido, boolean fijo) {
		
		int idFormaPago = pago == 0 ? 0: formasPago[pago - 1].getId();
		suma = 0;
		cantidadElementos = 0;
		egresos = egresosDAO.getListado(año, mes, tipo, idFormaPago, monedas, filtro, fijo? 1: 0);
		String titulo[] = {"Fecha", "Nombre", "Forma de pago", "Dólares", "Euros", "Monto en pesos"};
		String tabla[][] = null;
		double sumaColumnas[] = new double[2];
		
		if(egresos != null) {
		
			tabla = new String [egresos.length + 1][6];
			
			for(int i = 0; i < egresos.length; i++) {
			
				tabla[i][0] = egresos[i].getFecha();
				tabla[i][1] = egresos[i].getProveedor().getNombre();
				tabla[i][2] = egresos[i].getFormaPago().getDescripcion();
				
				if(egresos[i].getMoneda().equals("Pesos")) {
					
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto());
					
					if(diferido || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto();
				} else if(egresos[i].getMoneda().equals("Dólares")) {
					
					tabla[i][3] = formatoResultado.format(egresos[i].getMonto());
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto() * egresos[i].getCotizacion());
					sumaColumnas[0] += egresos[i].getMonto();
					
					if(diferido || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto() * egresos[i].getCotizacion();
				}else if(egresos[i].getMoneda().equals("Euros")) {
					
					tabla[i][4] = formatoResultado.format(egresos[i].getMonto());
					tabla[i][5] = formatoResultado.format(egresos[i].getMonto() * egresos[i].getCotizacion());
					sumaColumnas[1] += egresos[i].getMonto();
					
					if(diferido || egresos[i].getFormaPago().getFinanciado() == 0)
						suma += egresos[i].getMonto() * egresos[i].getCotizacion();
				}
			}
			tabla[egresos.length][3] = formatoResultado.format(sumaColumnas[0]);
			tabla[egresos.length][4] = formatoResultado.format(sumaColumnas[1]);
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		cantidadElementos = egresos.length;
		return tablaModelo;
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
			
			if(monto.contains(",")) {
				
				monto = monto.replace(".", "");
				monto = monto.replace(",", ".");
			}
			egreso.setMonto(Float.parseFloat(monto.replace(",", ".")));
		} catch (Exception e) {
			
			msgError = "El monto debe ser numérico.";
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
				
				msgError = "El valor del día está comprendido entre 1 y 31";
				return false;
			}
			
			if(Integer.parseInt(partes[1]) < 1 || Integer.parseInt(partes[1]) > 12) {
				
				msgError = "El valor del mes está comprendido entre 1 y 12";
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
		egreso.setTipoConsumo(destinos[pos - 1]);
		return true;
	}

	public boolean guardarEgreso() {
		
		if(egreso.getProveedor() == null || egreso.getProveedor().getId() < 1) {
			
			msgError = "El proveedor no está definido.";
			return false;
		}
		
		if(egreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!egresosDAO.nuevo(egreso)) {
		
			msgError = "Error al intentar guardar la información en la base de datos.";
			return false;
		}
		msgError = "La información se ha guardado.";
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
		
		return egreso.getTipoConsumo();
	}
	
	public boolean isGastoFijo() {
		
		return egreso.getGastoFijo() == 1? true: false;
	}
	
	public String getFecha() {
		
		return egreso.getFecha();
	}
	
	public String getProveedor() {
		
		return egreso.getProveedor().getNombre();
	}
	
	public String getMonto() {
		
		return formatoResultado.format(egreso.getMonto());
	}
	
	public String getMoneda() {
		
		return egreso.getMoneda();
	}
	
	public String getCotizacion() {
		
		return formatoResultado.format(egreso.getCotizacion());
	}
	
	public boolean actualizarEgreso() {
		
		if(egreso.getProveedor().getId() < 1) {
			
			msgError = "El proveedor no está definido.";
			return false;
		}
		
		if(egreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!egresosDAO.update(egreso)) {
		
			msgError = "Error al intentar guardar la información en la base de datos.";
			return false;
		}
		msgError = "La información se ha guardado.";
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
	
	public String getCuotas() {
		
		return egreso.getCuotas() + "";
	}
	
	public void setComentario(String comentario) {
		
		egreso.setComentario(comentario);
	}
	
	public void setGastoFijo(boolean gastoFijo) {
		
		egreso.setGastoFijo(gastoFijo? 1: 0);
	}
	
	public boolean setCotizacion(String cotizacion) {
		
		if(egreso.getMoneda().equals("Pesos")) {
			
			egreso.setCotizacion(1);
			return true;
		}
		
		try {

			egreso.setCotizacion(Float.parseFloat(cotizacion));
		} catch (Exception e) {
			
			msgError = "La cotización debe ser numérica.";
			return false;
		}
		return true;
	}
	
	public boolean setCuotas(String cuotas) {
		
		try {
			
			egreso.setCuotas(Integer.parseInt(cuotas));
		} catch (Exception e) {

			msgError = "La cantidad de cuotas debe ser un número entero.";
			return false;
		}
		
		return true;
	}
	
	public boolean borrarEgreso() {
		
		if(!egresosDAO.delete(egreso)) {
		
			msgError = "Error al intentar eliminar la información de la base de datos.";
			return false;
		}
		msgError = "La información se ha eliminado.";
		return true;
	}
	
	public void setAño(String año) {
		
		DtosEgresos.año = año;
	}
	
	public void setMes(int mes) {
		
		DtosEgresos.mes = mes;
	}
	
	public DefaultTableModel getResumen() {
		
		String titulo[] = {"Concepto", "Monto"};
		String tabla[][] = egresosDAO.getResumen(año, mes, destinos);
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
}