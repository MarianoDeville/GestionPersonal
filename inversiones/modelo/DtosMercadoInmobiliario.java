package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.MercadoInmobiliarioDAO;
import dao.MercadoInmobiliarioMySQL;
import dao.OperacionesDAO;
import dao.OperacionesMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosMercadoInmobiliario {

	private MercadoInmobiliarioDAO mercadoInmobiliarioDAO = new MercadoInmobiliarioMySQL();
	private OperacionesDAO operacionDAO = new OperacionesMySQL();
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	private Inmobiliario propiedades[];
	private Inmobiliario propiedad;
	private Transaccion metodosPago[];
	private Operacion operacion;
	private Ingreso ingreso;
	private Egreso egreso;
	private String msgError;
	private Calendar calendario;
	private double suma;
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////Inicio Mercado Inmobiliario /////////////////////////////////////////////////////////////////////////////////////
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = mercadoInmobiliarioDAO.getAñosCargados();
		
		if(respuesta.length == 0)
			return new String[] {calendario.get(Calendar.YEAR) + ""};

		if(respuesta[0].equals(calendario.get(Calendar.YEAR) + ""))
			return respuesta;
		String temp[] = new String[respuesta.length + 1];
		temp[0] = calendario.get(Calendar.YEAR) + "";
		System.arraycopy(respuesta, 0, temp, 1, respuesta.length);
		return temp;
	}
	
	public DefaultTableModel getTablaValores(String año, int mes) {
		
		String tabla[][] = null;
		String titulo[] = new String[] {"Nombre", "Localización"};

		
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}

	public String getSuma() {
		
		return formatoResultado.format(suma);
	}
	
	public String getCantValores() {
		
		return propiedades.length + "";
	}

	public void setPropiedad(int pos) {
	
		propiedad = propiedades[pos];
	}
///////////////////////////////////////////////////////////////////Fin Mercado Inmobiliario ////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////Inicio compra Inmobiliario //////////////////////////////////////////////////////////////////////////////////////

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
	
	public String [] getListaOperaciones() {
		
		return new String[] {"Aporte capital", "Compra terreno", "Urbanización"};
	}
		
	public void limpiar() {
		
		propiedad = null;
		operacion = null;
		ingreso = null;
		egreso = null;
	}

	public DefaultTableModel getListadoLocalizaciones(String filtro) {
		
		propiedades = mercadoInmobiliarioDAO.getListado();
		String tabla[][] = new String [propiedades.length][3];
		int i = 0;
		
		for(Inmobiliario loc: propiedades) {
			
			tabla[i][0] = loc.getOperador().getNombre();
			tabla[i][1] = loc.getDescripcion();
			tabla[i][2] = loc.getLugar();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"", "", ""});
		return tablaModelo;
	}
	
	public void setLocalizacion(int pos) {
		
		propiedad = propiedades[pos];
	}
	
	public void setComentario(String comentario) {
		
		operacion = new Operacion();
		operacion.setComentario(comentario);
	}
	
	public void setMonedaPago(String moneda) {
		
		egreso = new Egreso();
		egreso.setMoneda(moneda);
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
	
	public boolean setMonto(String monto) {
		
		try {
			
			if(monto.contains(",")) {
				
				monto = monto.replace(".", "");
				monto = monto.replace(",", ".");
			}
			operacion.setPrecio(Double.parseDouble(monto));
			operacion.setCant(1);
		} catch (Exception e) {

			msgError = "El precio debe ser un valor numérico.";
			return false;
		}
		return true;	
	}
	
	public boolean guardarOperacion() {
		// Guardo la operacion y el egreso
		
		
		
		
		msgError = "Operación guardada.";
		return true;
	}
	
	
	
	
	
	
	
	
	public String getMsgError() {
		
		return msgError;
	}
	
///////////////////////////////////////////////////////////////////Fin compra Inmobiliario /////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
