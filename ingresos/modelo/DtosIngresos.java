package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.IngresosDAO;
import dao.IngresosMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosIngresos {

	private IngresosDAO ingresosDAO = new IngresosMySQL();
	private Ingreso ingresos[];
	private static Ingreso ingreso;
	private static Proveedor fuentes[];
	private static Transaccion formasCobro[];
	private static String conceptos[];
	private double suma;
	private int cantidadElementos;
	private String msgError;
	private Calendar calendario;
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = ingresosDAO.getAñosCargados();
		
		if(respuesta.length == 0)
			return new String[] {calendario.get(Calendar.YEAR) + ""};

		if(respuesta[0].equals(calendario.get(Calendar.YEAR) + ""))
			return respuesta;
		String temp[] = new String[respuesta.length + 1];
		temp[0] = calendario.get(Calendar.YEAR) + "";
		System.arraycopy(respuesta, 0, temp, 1, respuesta.length);
		return temp;
	}

	public String [] getListaConceptos(String cabecera) {
		
		conceptos = new String[]{
				
			"Sueldo mensual",
			"Guardias",
			"Alquiler",
			"Acreditación Dividendos / Cupones / Intereses",
			"Viáticos",
			"Venta Activos / Pasivos",
			"Compra / Venta moneda extrangera",
			"Bonos / Retroactivos sueldo"
		};
		String respuesta[] = new String[conceptos.length + 1];
		respuesta[0] = cabecera;
		System.arraycopy(conceptos, 0, respuesta, 1, conceptos.length);
		return respuesta;
	}
	
	public String [] getFormasCobro(String cabecera) {
		
		if(formasCobro == null || formasCobro.length < 1) {

			TransaccionDAO transaccionDAO = new TransaccionMySQL();
			formasCobro = transaccionDAO.getMetodos("I");
			
			if(formasCobro == null)
				formasCobro = new Transaccion[0];
		}
		String respuesta[] = new String[formasCobro.length + 1];
		respuesta[0] = cabecera;
		int i = 1;
		
		for(Transaccion cobro: formasCobro) {
			
			respuesta[i] = cobro.getDescripcion();
			i++;
		}
		return respuesta;
	}
	
	public DefaultTableModel getTablaIngresos(String año, int mes, String tipo, int pago, String monedas, String filtro) {
		
		int idFormaCobro = pago == 0 ? 0: formasCobro[pago - 1].getId();
		suma = 0;
		cantidadElementos = 0;
		ingresos = ingresosDAO.getListado(año, mes, tipo, idFormaCobro, monedas, filtro);
		String titulo[] = {"Fecha", "Nombre", "Forma de cobro", "Concepto", "Dólares", "Euros", "Monto en pesos"};
		String tabla[][] = new String [ingresos.length][7];

		for(int i = 0; i < tabla.length; i++) {
		
			tabla[i][0] = ingresos[i].getFecha();
			tabla[i][1] = ingresos[i].getFuente().getNombre();
			tabla[i][2] = ingresos[i].getFormaCobro().getDescripcion();
			tabla[i][3] = ingresos[i].getConcepto();
			
			if(ingresos[i].getMoneda().equals("Pesos")) {
				
				tabla[i][6] = formatoResultado.format(ingresos[i].getMonto());
				suma += ingresos[i].getMonto();
			} else if(ingresos[i].getMoneda().equals("Dólares")) {
				
				tabla[i][4] = formatoResultado.format(ingresos[i].getMonto());
				tabla[i][6] = formatoResultado.format(ingresos[i].getMonto() * ingresos[i].getCotizacion());
				suma += ingresos[i].getMonto() * ingresos[i].getCotizacion();
			}else if(ingresos[i].getMoneda().equals("Euros")) {
				
				tabla[i][5] = formatoResultado.format(ingresos[i].getMonto());
				tabla[i][6] = formatoResultado.format(ingresos[i].getMonto() * ingresos[i].getCotizacion());
				suma += ingresos[i].getMonto() * ingresos[i].getCotizacion();
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		cantidadElementos = tabla.length;
		return tablaModelo;
	}
	
	public String getSuma() {
		
		return formatoResultado.format(suma);
	}

	public String getCantidadElementos() {
		
		return cantidadElementos + "";
	}
	
	public void seleccionarIngreso(int pos) {
		
		ingreso = ingresos[pos];
	}

	public void setFuente(int pos) {
		
		if(ingreso == null)
			ingreso = new Ingreso();
		ingreso.setFuente(new Proveedor());
		ingreso.getFuente().setId(fuentes[pos].getId() );
	}
	
	public DefaultTableModel getListaFuentes(String filtro) {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		fuentes = proveedoresDAO.getListado(filtro, "I");
		String tabla[][] = new String [fuentes.length][1];
		int i = 0;
		
		for(Proveedor temp: fuentes) {
			
			tabla[i][0] = temp.getNombre();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre de la fuente de ingresos"});
		return tablaModelo;
	}
	
	public void setMoneda(String moneda) {
		
		if(ingreso == null)
			ingreso = new Ingreso();
		ingreso.setMoneda(moneda);
	}
	
	public String getComentario() {
		
		return ingreso.getComentario();
	}
	
	public void setComentario(String comentario) {
		
		ingreso.setComentario(comentario);
	}
	
	public boolean setFecha(String fecha) {
		
		if(ingreso == null)
			ingreso = new Ingreso();
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
			ingreso.setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);	
		} catch(Exception e) {
			
			return false;
		}
		return true;
	}		
	
	public boolean setDestino(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe definir el tipo de gasto.";
			return false;
		}
		ingreso.setConcepto(conceptos[pos - 1]);
		return true;
	}

	public boolean setFormaPago(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe elegir una forma de pago.";
			return false;
		}
		ingreso.setFormaCobro(new Transaccion());
		ingreso.getFormaCobro().setId(formasCobro[pos - 1].getId());
		return true;
	}
	
	public boolean setMonto(String monto) {
		
		try {
			
			ingreso.setMonto(Double.parseDouble(monto.replace(",", ".")));
		} catch (Exception e) {
			
			msgError = "El monto debe ser numérico.";
			return false;
		}
		return true;
	}
	
	public boolean setCotizacion(String cotizacion) {
		
		if(ingreso.getMoneda().equals("Pesos")) {
			
			ingreso.setCotizacion(1);
			return true;
		}
		
		try {

			ingreso.setCotizacion(Float.parseFloat(cotizacion));
		} catch (Exception e) {
			
			msgError = "La cotización debe ser numérica.";
			return false;
		}
		return true;
	}

	public boolean guardarIngreso() {
		
		if(ingreso.getFuente().getId() < 1) {
			
			msgError = "El proveedor no está definido.";
			return false;
		}
		
		if(ingreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!ingresosDAO.nuevo(ingreso)) {
		
			msgError = "Error al intentar guardar la información en la base de datos.";
			return false;
		}
		msgError = "La información se ha guardado.";
		return true;
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public void setIngreso(Ingreso info) {
		
		DtosIngresos.ingreso = info;
	}
	
	public String getFecha() {
		
		return ingreso.getFecha();
	}
	
	public String getFuente() {
		
		return ingreso.getFuente().getNombre();
	}
	
	public String getMonto() {
		
		return formatoResultado.format(ingreso.getMonto());
	}
	
	public String getMoneda() {
		
		return ingreso.getMoneda();
	}
	
	public String getCtizacion() {
		
		return formatoResultado.format(ingreso.getCotizacion());
	}
	
	public boolean actualizarIngreso() {
		
		if(ingreso.getFuente().getId() < 1) {
			
			msgError = "El proveedor no está definido.";
			return false;
		}
		
		if(ingreso.getMonto() <= 0) {
			
			msgError = "Debe especificar un moto.";
			return false;
		} 
		
		if(!ingresosDAO.update(ingreso)) {
		
			msgError = "Error al intentar guardar la información en la base de datos.";
			return false;
		}
		msgError = "La información se ha guardado.";
		return true;
	}
	
	public String getFormaCobroSeleccionado() {
		
		return ingreso.getFormaCobro().getDescripcion();
	}
	
	public String getConcepto() {
		
		return ingreso.getConcepto();
	}
	
	public boolean borrarEgreso() {
		
		if(!ingresosDAO.delete(ingreso)) {
		
			msgError = "Error al intentar eliminar la información de la base de datos.";
			return false;
		}
		msgError = "La información se ha eliminado.";
		return true;
	}
}