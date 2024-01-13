package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.CotizacionesMySQL;
import dao.CotizacioonesDAO;
import dao.MercadoFiatDAO;
import dao.MercadoFiatMySQL;
import dao.OperacionesDAO;
import dao.OperacionesMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosMercadoFiat {

	private MercadoFiatDAO mercadoFiatDAO = new MercadoFiatMySQL();
	private OperacionesDAO operacionesDAO = new OperacionesMySQL();
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	private Calendar calendario;
	private Fiat monedas[];
	private Fiat monedasAgrupadas[];
	private Fiat moneda;
	private Proveedor localizaciones[];
	private Transaccion metodosPago[];
	private double suma;
	private String listaMonedas[] = {"Selecciones una opción", "Dólares", "Euros"};
	private String msgError;
	
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = mercadoFiatDAO.getAñosCargados();
		
		if(respuesta.length == 0)
			return new String[] {calendario.get(Calendar.YEAR) + ""};

		if(respuesta[0].equals(calendario.get(Calendar.YEAR) + ""))
			return respuesta;
		String temp[] = new String[respuesta.length + 1];
		temp[0] = calendario.get(Calendar.YEAR) + "";
		System.arraycopy(respuesta, 0, temp, 1, respuesta.length);
		return temp;
	}

	public DefaultTableModel getListadoOperaciones(String año, int mes) {
		
		monedas = mercadoFiatDAO.getListado(false);
		int tamaño = operacionesDAO.getListado(año, mes, monedas);
		String tabla[][] = new String [monedas.length][tamaño + 3];
		String titulo[] = new String[tamaño + 3];
		System.arraycopy(new String[]{"Moneda", "Cant.", "Localización"}, 0, titulo, 0, 3);
		
		for(int i = 0; i < monedas.length; i++) {

			tabla[i][0] = monedas[i].getMoneda();
			tabla[i][1] = formatoResultado.format(monedas[i].getCant());
			tabla[i][2] = monedas[i].getCustodia().getNombre();
			
			for(int e = 0; e < tamaño; e++) {

				tabla[i][e + 3] = monedas[i].getOperaciones()[e].getOperacion().equals("Compra")? "+": "-";
				tabla[i][e + 3] += formatoResultado.format(monedas[i].getOperaciones()[e].getCant());
				titulo[e + 3] = monedas[i].getOperaciones()[e].getFecha();
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public DefaultTableModel getTablaCotizaciones(String año, int mes, boolean agregar) {
		
		monedasAgrupadas = mercadoFiatDAO.getListado(true);
		CotizacioonesDAO cotizacionesDAO = new CotizacionesMySQL();
		int tamaño = cotizacionesDAO.getCotizaciones(año, mes, monedasAgrupadas);

		String tabla[][] = new String[monedasAgrupadas.length][tamaño + (agregar? 3: 2)];
		String titulo[] = new String[tamaño + (agregar? 3: 2)];
		System.arraycopy(new String[]{"Moneda", "Cant."}, 0, titulo, 0, 2);
		
		
		for(int i = 0; i < monedasAgrupadas.length; i++) {
			
			tabla[i][0] = monedasAgrupadas[i].getMoneda();
			tabla[i][1] = formatoResultado.format(monedasAgrupadas[i].getCant());
			
			for(int e = 0; e < tamaño; e++) {
			
				if(monedasAgrupadas[i].getCotizaciones()[e].getValor() > 0)
					tabla[i][e + 2] = formatoResultado.format(monedasAgrupadas[i].getCotizaciones()[e].getValor());
				else
					tabla[i][e + 2] = "-";
				titulo[e + 3] = monedasAgrupadas[i].getCotizaciones()[e].getFecha();
				

			}
		}
		
		if(agregar)
			titulo[titulo.length - 1] = DtosComunes.getFechaActual();
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				if(agregar)
					return column == titulo.length - 1? true: false;
				else
					return false;
			}
		};
		return tablaModelo;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	

	public String getSuma() {
		
		return formatoResultado.format(suma);
	}
	
	public String getCantFiat() {
		
		return monedas.length + "";
	}
///////////////////////////////////////////// Inicio compra de fiat ///////////////////////////////////////////////////////////////////////////////////////////////////////
	public DefaultTableModel getListadoLocalizaciones(String filtro) {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		localizaciones = proveedoresDAO.getListado(filtro, "M");
		String tabla[][] = new String [localizaciones.length][2];
		int i = 0;
		
		for(Proveedor loc: localizaciones) {
			
			tabla[i][0] = loc.getNombre();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre"});
		return tablaModelo;
	}

	public String [] getListadoMetPago() {
		
		TransaccionDAO transaccionDAO = new TransaccionMySQL();
		metodosPago = transaccionDAO.getMetodos("E");
		String respuesta[] = new String [metodosPago.length + 1];
		respuesta[0] = "Selecciones una opción";
		int i = 1;
		
		for(Transaccion pago: metodosPago) {
			
			respuesta[i] = pago.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public String [] getListaMonedas() {
		
		return listaMonedas;
	}
	
	public void setLocalizacion(int pos) {
		
		if(moneda == null)
			moneda = new Fiat();
		moneda.setCustodia(localizaciones[pos]);
	}
	
	public void resetMoneda() {
		
		moneda = null;
	}
	
	public void setMonedaPago(String mon) {
		
		if(moneda == null)
			moneda = new Fiat();
		moneda.setOperaciones(new Operacion[1]);
		moneda.getOperaciones()[0] = new Operacion();
		moneda.getOperaciones()[0].setMoneda(mon);
	}
	
	public void setComentario(String comentario) {
		
		moneda.getOperaciones()[0].setComentario(comentario);
	}
	
	public void setTipoOperacion(String tipoOper) {
		
		moneda.getOperaciones()[0].setOperacion(tipoOper);
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean setFecha(String fecha) {
		
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
			moneda.getOperaciones()[0].setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);	
		} catch(Exception e) {
			
			return false;
		}
		msgError = "";
		return true;
	}
	
	public boolean setMetodoPago(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir un método de pago.";
			return false;
		}
		moneda.getOperaciones()[0].setTransaccion(metodosPago[pos - 1].getDescripcion());
		return true;
	}
	
	public boolean setMoneda(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir una moneda.";
			return false;
		}
		moneda.setMoneda(listaMonedas[pos]);
		return true;
	}
	
	public boolean setPrecio(String precio) {
		
		try {
			
			moneda.getOperaciones()[0].setPrecio(Double.parseDouble(precio));
		} catch (Exception e) {

			msgError = "El precio debe ser un valor numérico.";
			return false;
		}
		return true;	
	}
	
	public boolean setCantidad(String cantidad) {

		try {
			
			moneda.setCant(Double.parseDouble(cantidad));
		} catch (Exception e) {

			msgError = "La cantidad debe ser un valor numérico.";
			return false;
		}
		return true;
	}

	public boolean guardarCompra() {
		
				try {
		
			if(moneda.getCustodia().getId() == 0) {
				
				msgError = "Debe elegir el lugar donde se depositará la compra.";
				return false;
			}
		} catch (Exception e) {
			
			msgError = "Debe elegir el lugar donde se depositará la compra.";
			return false;
		}

		if(mercadoFiatDAO.newFiat(moneda)) {

			moneda.getOperaciones()[0].setIdFiat(moneda.getId());
			moneda.getOperaciones()[0].setIdCustodia(moneda.getCustodia().getId());
			moneda.getOperaciones()[0].setCant(moneda.getCant());
			
			if(operacionesDAO.newCompra(moneda.getOperaciones()[0])) {
				
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}
///////////////////////////////////////////// Fin compra de fiat ///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////// Inicio compra de fiat ///////////////////////////////////////////////////////////////////////////////////////////////////////	
	public boolean guardarVenta() {
		
		try {

			if(moneda.getCustodia().getId() == 0) {
				
				msgError = "Debe elegir el lugar donde se depositará la compra.";
				return false;
			}
		} catch (Exception e) {
			
			msgError = "Debe elegir el lugar donde se depositará la compra.";
			return false;
		}
		
// debo consultar si en el proveedor hay saldo suficiente para la venta de la moneda.
/*		
		if(mercadoFiatDAO.newFiat(moneda)) {
		
			moneda.getOperaciones()[0].setIdFiat(moneda.getId());
			moneda.getOperaciones()[0].setIdCustodia(moneda.getCustodia().getId());
			moneda.getOperaciones()[0].setCant(moneda.getCant());
			
			if(operacionesDAO.newCompra(moneda.getOperaciones()[0])) {
				
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
*/		msgError = "Error al intentar guardar la compra.";
		return false;
	}
	
	
	
///////////////////////////////////////////// Fin compra de fiat ///////////////////////////////////////////////////////////////////////////////////////////////////////	
}