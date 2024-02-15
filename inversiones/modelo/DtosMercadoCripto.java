package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.CotizacionesMySQL;
import dao.CotizacioonesDAO;
import dao.EgresosDAO;
import dao.EgresosMySQL;
import dao.IngresosDAO;
import dao.IngresosMySQL;
import dao.MercadoCriptoDAO;
import dao.MercadoCriptoMySQL;
import dao.OperacionesDAO;
import dao.OperacionesMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosMercadoCripto {

	private MercadoCriptoDAO mercadoCriptoDAO = new MercadoCriptoMySQL();
	private OperacionesDAO operacionesDAO = new OperacionesMySQL();
	private CotizacioonesDAO cotizacionesDAO = new CotizacionesMySQL();
	private DecimalFormat formato4Decimales = new DecimalFormat("###,###,##0.0000");
	private DecimalFormat formatoHasta8Decimales = new DecimalFormat("###,###,##0.00######");
	private DecimalFormat formato2Decimales = new DecimalFormat("###,###,##0.0000");
	private Calendar calendario;
	private Cripto monedas[];
	private Cripto monedasAgrupadas[];
	private static Cripto moneda;
	private MonedaCripto listaMonedas[];
	private Transaccion metodosPago[];
	private Proveedor localizaciones[];
	private Operacion operacion;
	private Egreso egreso;
	private Ingreso ingreso;
	private String msgError;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////Página principal de mercado cripto //////////////////////////////////////////////////////////////
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = mercadoCriptoDAO.getAñosCargados();
		
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

		monedas = operacionesDAO.getListadoCripto(año, mes);
		int tamaño = 0;
		
		try {
			
			tamaño = monedas[0].getOperaciones().length;
		} catch (Exception e) {

		}
		String tabla[][] = new String [monedas.length][tamaño + 3];
		String titulo[] = new String[tamaño + 3];
		System.arraycopy(new String[]{"Moneda", "Cant.", "Localización"}, 0, titulo, 0, 3);
		
		for(int i = 0; i < monedas.length; i++) {

			tabla[i][0] = monedas[i].getMoneda().getNombre() + " " + monedas[i].getMoneda().getSimbolo();
			tabla[i][1] = formato4Decimales.format(monedas[i].getCant());
			tabla[i][2] = monedas[i].getCustodia().getNombre();
			
			for(int e = 0; e < tamaño; e++) {

				tabla[i][e + 3] = formato4Decimales.format(monedas[i].getOperaciones()[e].getCant());
				titulo[e + 3] = monedas[i].getOperaciones()[e].getFecha();
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaCotizaciones(String año, int mes, boolean agregar) {
		
		monedasAgrupadas = cotizacionesDAO.getCotizacionesCripto(año, mes);
		int tamaño = 0;

		try {

			tamaño = monedasAgrupadas[0].getCotizaciones().length;
		} catch (Exception e) {

		}
		String tabla[][] = new String[monedasAgrupadas.length + 1][tamaño + (agregar? 3: 2)];
		String titulo[] = new String[tamaño + (agregar? 3: 2)];
		double calculoDia[] = new double[tamaño];
		System.arraycopy(new String[]{"Moneda", "Cant."}, 0, titulo, 0, 2);

		for(int i = 0; i < monedasAgrupadas.length; i++) {
			
			tabla[i][0] = monedasAgrupadas[i].getMoneda().getSimbolo();
			tabla[i][1] = formato4Decimales.format(monedasAgrupadas[i].getCant());
			
			for(int e = 0; e < tamaño; e++) {
			
				if(monedasAgrupadas[i].getCotizaciones()[e].getValor() > 0)
					tabla[i][e + 2] = formatoHasta8Decimales.format(monedasAgrupadas[i].getCotizaciones()[e].getValor());
				else
					tabla[i][e + 2] = "-";
				calculoDia[e] += monedasAgrupadas[i].getCotizaciones()[e].getValor() * monedasAgrupadas[i].getCant();
				
				if( i == monedasAgrupadas.length -1)
					tabla[monedasAgrupadas.length][e + 2] = formato2Decimales.format(calculoDia[e]);;
				titulo[e + 2] = monedasAgrupadas[i].getCotizaciones()[e].getFecha();
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
	
	public boolean guardarCotizacion(JTable tablaCotizaciones) {
		
		int ultCol = tablaCotizaciones.getColumnCount() - 1;
		msgError = "Error al intentar guardar la información";
		
		try {
		
			for(int i = 0; i < monedasAgrupadas.length; i++) {
			
				monedasAgrupadas[i].setCotizaciones(new Cotizacion[1]);
				monedasAgrupadas[i].getCotizaciones()[0] = new Cotizacion();
				monedasAgrupadas[i].getCotizaciones()[0].setFecha(DtosComunes.getFechaActual());
				monedasAgrupadas[i].getCotizaciones()[0].setValor(Double.parseDouble((String)tablaCotizaciones.getValueAt(i, ultCol)));
			}
		} catch (Exception e) {
			
			msgError = "El valor de las cotizaciones debe ser numérico.";
			return false;
		}
		return cotizacionesDAO.update(monedasAgrupadas);
	}
////////////////////////////////////////////////////////////////////////////////////Fin página principal de mercado cripto //////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////Inicio Compra de cripto /////////////////////////////////////////////////////////////////////////
	public String [] getListadoMetPago(boolean ingreso) {
		
		TransaccionDAO transaccionDAO = new TransaccionMySQL();
		metodosPago = transaccionDAO.getMetodos(ingreso? "I":"E");
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
		
		listaMonedas = cotizacionesDAO.getMonedasCripto();
		String respuesta[] = new String[listaMonedas.length + 1];
		respuesta[0] = "Seleccione una opción";
		
		for(int i = 0; i < listaMonedas.length; i++) {

			respuesta[i + 1] = listaMonedas[i].getNombre();
		}
		return respuesta;
	}
	
	public void setLocalizacion(int pos) {
		
		if(moneda == null)
			moneda = new Cripto();
		moneda.setCustodia(localizaciones[pos]);
	}

	public DefaultTableModel getListadoLocalizaciones(String filtro) {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		localizaciones = proveedoresDAO.getListado(filtro, "C");
		String tabla[][] = new String [localizaciones.length][2];
		int i = 0;
		
		for(Proveedor loc: localizaciones) {
			
			tabla[i][0] = loc.getNombre();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre"});
		return tablaModelo;
	}
	
	public void setMonedaPago(String mon) {	
		
		if(egreso == null)
			egreso = new Egreso();
		egreso.setMoneda(mon);
	}
	
	public void setConcepto(String concepto) {
	
		if(operacion == null)
			operacion = new Operacion();
		operacion.setOperacion("Acreditación de " + concepto);
	}
	
	public void setComentario(String comentario) {
		
		if(operacion == null)
			operacion = new Operacion();
		operacion.setComentario(comentario);
	}
	
	public boolean setFecha(String fecha) {
		
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
			operacion.setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);
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
		egreso.setFormaPago(metodosPago[pos - 1]);
		return true;
	}
	
	public boolean setMoneda(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir una moneda.";
			return false;
		}
		moneda.setMoneda(listaMonedas[pos - 1]);
		return true;
	}
	
	public boolean setPrecio(String precio) {
		
		try {
			
			if(precio.contains(",")) {
				
				precio = precio.replace(".", "");
				precio = precio.replace(",", ".");
			}
			operacion.setPrecio(Double.parseDouble(precio));
		} catch (Exception e) {

			msgError = "El precio debe ser un valor numérico.";
			return false;
		}
		return true;	
	}
	
	public boolean setCantidad(String cantidad) {

		try {
			
			if(cantidad.contains(",")) {
				
				cantidad = cantidad.replace(".", "");
				cantidad = cantidad.replace(",", ".");
			}
			operacion.setCant(Double.parseDouble(cantidad));
		} catch (Exception e) {

			msgError = "La cantidad debe ser un valor numérico.";
			return false;
		}
		return true;
	}

	public boolean setComision(String comision) {
		
		try {
			
			if(comision.contains(",")) {
				
				comision = comision.replace(".", "");
				comision = comision.replace(",", ".");
			}
			operacion.setComision(Double.parseDouble(comision));
		} catch (Exception e) {

			msgError = "La comisión debe ser un valor numérico.";
			return false;
		}
		return true;		
	}
	
	public boolean guardarCompra(boolean acreditacion) {

		if(acreditacion && operacion.getOperacion().contains("Seleccione")) {
			
			msgError = "Debe seleccionar una fuente de dividendos.";
			return false;
		}
			
		try {
		
			if(moneda.getCustodia().getId() == 0) {
				
				msgError = "Debe elegir el lugar donde se depositará la compra.";
				return false;
			}
		} catch (Exception e) {
			
			msgError = "Debe elegir el lugar donde se depositará la compra.";
			return false;
		}
		egreso.setFecha(operacion.getFecha());
		egreso.setProveedor(moneda.getCustodia());
		egreso.setComentario(operacion.getComentario());
		moneda.setCant(operacion.getCant());

		if(mercadoCriptoDAO.update(moneda)) {

			operacion.setIdCripto(moneda.getId());
			EgresosDAO egresosDAO = new EgresosMySQL();

			if(acreditacion) {
				
				egreso.setMoneda("Pesos");
				egreso.setTipoConsumo("Comisiones");
				egreso.setCotizacion(1);
				egreso.setMonto(operacion.getComision());
				operacion.setPrecio(0);
			} else {

				operacion.setOperacion("Compra");
				egreso.setTipoConsumo("Ahorro / Inversión");
				egreso.setCotizacion(cotizacionesDAO.getUltima(moneda.getMoneda().getNombre()));
				egreso.setMonto((operacion.getPrecio() * operacion.getCant()) + operacion.getComision());

			}
			egresosDAO.nuevo(egreso);
			operacion.setIdEgreso(egreso.getId());
			
			if(operacionesDAO.update(operacion)) {
				
				operacion = null;
				moneda = null;
				egreso = null;
				ingreso = null;
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}

	public String getMsgError() {
		
		return msgError;
	}
	
	public void resetMoneda() {
		
		moneda = null;
	}
	
	public String [] getListaConceptos() {

		return new String [] {"Interes", "Reintegro compra"};
	}
////////////////////////////////////////////////////////////////////////////////////Fin Compra de cripto ////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////Inicio venta de cripto //////////////////////////////////////////////////////////////////////////
	public void setMonedaCobro(String mon) {
		
		if(ingreso == null)
			ingreso = new Ingreso();
		ingreso.setMoneda(mon);
	}
	
	public boolean setMetodoCobro(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir un método de pago.";
			return false;
		}
		ingreso.setFormaCobro(metodosPago[pos - 1]);
		return true;
	}
	
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
		double saldo = mercadoCriptoDAO.getSaldo(moneda);

		if(saldo < operacion.getCant()) {
			
			msgError = "El saldo disponible es de: " + formatoHasta8Decimales.format(saldo);
			return false;
		}
		moneda.setCant(- operacion.getCant());

		if(mercadoCriptoDAO.update(moneda)) {
		
			IngresosDAO ingresosDAO = new IngresosMySQL();
			EgresosDAO egresosDAO = new EgresosMySQL();
			ingreso.setFecha(operacion.getFecha());
			ingreso.setMonto(operacion.getPrecio() * operacion.getCant());
			ingreso.setCotizacion(ingreso.getMoneda().equals("Pesos")? 1: operacion.getPrecio());
			ingreso.setComentario(operacion.getComentario());
			ingreso.setConcepto("Compra / Venta moneda extrangera");
			ingreso.setFuente(moneda.getCustodia());
			egreso = new Egreso();
			egreso.setFecha(operacion.getFecha());
			egreso.setMonto(operacion.getComision());
			egreso.setMoneda("Pesos");
			egreso.setCotizacion(1);
			egreso.setTipoConsumo("Comision");
			egreso.setComentario(operacion.getComentario());
			egreso.setProveedor(moneda.getCustodia());
			egreso.setFormaPago(ingreso.getFormaCobro());
			ingresosDAO.nuevo(ingreso);
			egresosDAO.nuevo(egreso);
			egreso.setMonto(operacion.getCant());
			egreso.setMoneda(moneda.getMoneda().getNombre());
			egreso.setCotizacion(operacion.getPrecio());
			egreso.setTipoConsumo("Ahorro / Inversión");
			egresosDAO.nuevo(egreso);
			operacion.setOperacion("Venta");
			operacion.setIdFiat(moneda.getId());
			operacion.setIdEgreso(egreso.getId());
			operacion.setIdIngreso(ingreso.getId());
			operacion.setCant(moneda.getCant());
			
			if(operacionesDAO.update(operacion)) {
				
				operacion = null;
				moneda = null;
				egreso = null;
				ingreso = null;
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la venta.";
		return false;
	}
////////////////////////////////////////////////////////////////////////////////////Fin venta de cripto /////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////Inicio detalle de cripto ////////////////////////////////////////////////////////////////////////	
	public void getDetalle(int pos) {
		
		moneda = monedas[pos];

	}
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////Fin venta de cripto /////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}