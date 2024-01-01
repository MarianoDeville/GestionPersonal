package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import dao.MercadoValoresDAO;
import dao.MercadoValoresMySQL;
import dao.OperacionesDAO;
import dao.OperacionesMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;

public class DtosMercadoValores {
	
	MercadoValoresDAO mercadoValoresDAO = new MercadoValoresMySQL();
	OperacionesDAO operacionDAO = new OperacionesMySQL();
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	private Proveedor custodios[];
	private Proveedor custodia;
	private Instrumento instrumentos[];
	private static Valores valores[];
	private Valores valor;
	private Operacion operacion;
	private String msgError;
	
	private Calendar calendario;
		
	
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = mercadoValoresDAO.getAñosCargados();
		
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////// solo debe poderse editar la última columna cuando agrego una para la carga de cotizaciones del día//////////////////////////////////////////////////////
	public DefaultTableModel getTablaValores(String año, int mes) {////////////////////////////////////////// debo armar la tabla con las cotizaciones cargadas ////////////////////
		
		valores = mercadoValoresDAO.getListado("");
		String titulo[] = {"Cant.", "Nombre", "Mercado"};
		String tabla[][] = new String[valores.length][5];
		
	
		for(int i = 0; i < valores.length; i++) {
			
			tabla[i][0] = formatoResultado.format(valores[i].getCant());
			tabla[i][1] = valores[i].getNombre();
			tabla[i][2] = valores[i].getCustodia().getMercado();
			
			
		}
		
		
		
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getFechaActual() {
		
		calendario = new GregorianCalendar();
		return calendario.get(Calendar.DAY_OF_MONTH) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
	}
	
	public String [] getListaCustodias() {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		custodios = proveedoresDAO.getListado("", "Mercado");
		String respuesta[] = new String [custodios.length + 1];
		respuesta[0] = "Selecciones una opción";
		int i = 1;
		
		for(Proveedor prov: custodios) {
			
			respuesta[i] = prov.getNombre() + " - " + prov.getMercado();
			i++;
		}
		return respuesta;
	}
	
	public String [] getListaInstrumentos() {
		
		instrumentos = mercadoValoresDAO.getIntrumentos();
		String respuesta[] = new String [instrumentos.length + 1];
		respuesta[0] = "Selecciones una opción";
		int i = 1;
		
		for(Instrumento prov: instrumentos) {
			
			respuesta[i] = prov.getNombre();
			i++;
		}
		return respuesta;
	}
	
	public DefaultTableModel getListadoValores(String filtro) {
		
		valores = mercadoValoresDAO.getListado(filtro);
		String tabla[][] = new String [valores.length][2];
		int i = 0;
		
		for(Valores val: valores) {
			
			tabla[i][0] = val.getNombre();
			tabla[i][1] = val.getCustodia().getNombre() + " - " + val.getCustodia().getMercado();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre", "Mercado"});
		return tablaModelo;
	}
	
	public void setValor(int pos) {
		
		valor = valores[pos];
	}
	
	public String getCustodiaValor() {
		
		return valor.getCustodia().getNombre() + " - " + valor.getCustodia().getMercado();
	}
	
	public String getTipoInstrumento() {
	
		return valor.getInstrumento().getNombre();
	}
	
	public void resetValor() {
		
		valor = null;
		operacion = null;
	}

	public boolean setFecha(String fecha) {
		
		if(operacion == null)
			operacion = new Operacion();
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
			operacion.setFecha(partes[2] + "/" + partes[1] + "/" + partes[0]);	
		} catch(Exception e) {
			
			return false;
		}
		msgError = "";
		return true;
	}
	
	public boolean setNombre(String nombre) {
		
		if(nombre.length() < 3) {
			
			msgError = "No ha definido el nombre de la especie.";
			return false;
		}
		
		if(valor == null)
			valor = new Valores();
		valor.setNombre(nombre);
		return true;
	}
	
	public boolean setPrecio(String precio) {
		
		try {
			
			operacion.setPrecio(Double.parseDouble(precio));
		} catch (Exception e) {

			msgError = "El precio debe ser un valor numérico.";
			return false;
		}
		return true;		
	}
	
	public boolean setCantidad(String cant) {
		
		try {
		
			operacion.setCant(Double.parseDouble(cant));
			valor.setCant(Double.parseDouble(cant));
		} catch (Exception e) {

			msgError = "La cantidad debe ser un valor numérico.";
			return false;
		}
		return true;
	}

	public void setMoneda(String moneda) {
	
		if(operacion == null)
			operacion = new Operacion();
		operacion.setMoneda(moneda);
	}
	
	public boolean setCustodia(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe seleccionar una custodia.";
			return false;
		}
		custodia = custodios[pos - 1];
		return true;
	}
	
	public boolean setComision(String comision) {
		
		try {
			
			operacion.setComision(Double.parseDouble(comision));
		} catch (Exception e) {

			msgError = "El valor de la comisión debe ser numérico.";
			return false;
		}
		return true;
	}
	
	public void setComentario(String comentario) {
	
		operacion.setComentario(comentario);
	}
	
	public void setTipoOperacion(String operacion) {
		
		this.operacion.setOperacion(operacion);
	}
	
	public boolean setTipoInstrumento(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe seleccionar el tipo de intrumento que se está operando.";
			return false;
		}
		valor.setInstrumento(instrumentos[pos -1]);
		return true;
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean guardarOperacion() {  
				
		if(valor.getCustodia() == null)
			valor.setCustodia(custodia);
		operacion.setIdCustodia(custodia.getId());

		if(mercadoValoresDAO.newValor(valor)) {

			operacion.setIdValores(valor.getId());
			
			if(operacionDAO.newCompra(operacion)) {
				
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
}