package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.EgresosDAO;
import dao.EgresosMySQL;
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
	
	public DefaultTableModel getTablaValores(String año) {
		
		propiedades = operacionDAO.getListadoPropiedades(año, true);
		int tamaño = 0;
		
		try {
			
			tamaño = propiedades[0].getOperaciones().length;
		} catch (Exception e) {

		}
		String tabla[][] = new String[propiedades.length][tamaño + 3];
		String titulo[] = new String[tamaño + 3];
		System.arraycopy(new String[]{"Nombre", "Localización", "Empresa"}, 0, titulo, 0, 3);

		for(int i = 0; i < propiedades.length; i++) {

			tabla[i][0] = propiedades[i].getDescripcion();
			tabla[i][1] = propiedades[i].getLugar();
			tabla[i][2] = propiedades[i].getOperador().getNombre();
			
			for(int e = 0; e < tamaño; e++) {

				tabla[i][e + 3] = formatoResultado.format(propiedades[i].getOperaciones()[e].getPrecio());
				titulo[e + 3] = propiedades[i].getOperaciones()[e].getFecha();
			}
		}		
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
		
		propiedades = mercadoInmobiliarioDAO.getListado(filtro);
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

	public void setComentario(String comentario) {
		
		egreso = new Egreso();
		egreso.setComentario(comentario);
	}
	
	public void setMonedaPago(String moneda) {
		
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
			egreso.setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);
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
			egreso.setMonto(Double.parseDouble(monto));
		} catch (Exception e) {

			msgError = "El precio debe ser un valor numérico.";
			return false;
		}
		return true;	
	}
	
	public boolean setCotizacion(String cot) {
		
		try {
			
			if(cot.contains(",")) {
				
				cot = cot.replace(".", "");
				cot = cot.replace(",", ".");
			}
			egreso.setCotizacion(Double.parseDouble(cot));
		} catch (Exception e) {

			msgError = "La cotización debe ser un valor numérico.";
			return false;
		}
		return true;	
	}
	
	public void setOperacion(String op) {
			
		operacion = new Operacion();
		operacion.setOperacion(op);
	}
	
	public boolean guardarOperacion() {

		msgError = "Error al intentar guardar la información en la base de datos.";
		egreso.setTipoConsumo("Ahorro / Inversión");
		egreso.setGastoFijo(0);
		egreso.setProveedor(propiedad.getOperador());
		EgresosDAO egresoDAO = new EgresosMySQL();
	
		if(!egresoDAO.nuevo(egreso))
			return false;
		operacion.setFecha(egreso.getFecha());
		operacion.setCant(1);
		operacion.setPrecio(egreso.getMonto());
		operacion.setComision(0);
		operacion.setComentario(egreso.getComentario());
		operacion.setIdInmobiliario(propiedad.getId());
		operacion.setIdEgreso(egreso.getId());
		
		if(!operacionDAO.update(operacion))
			return false;
		msgError = "Operación guardada.";
		return true;
	}

	public String getMsgError() {
		
		return msgError;
	}
	
///////////////////////////////////////////////////////////////////Fin compra Inmobiliario /////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
