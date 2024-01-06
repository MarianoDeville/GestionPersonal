package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTable;
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
	private static Valores valor;
	private Operacion operacion;
	private String msgError;
	private double suma;
	private Calendar calendario;
		
	public String [] getListaA�os() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = mercadoValoresDAO.getA�osCargados();
		
		if(respuesta.length == 0)
			return new String[] {calendario.get(Calendar.YEAR) + ""};

		if(respuesta[0].equals(calendario.get(Calendar.YEAR) + ""))
			return respuesta;
		String temp[] = new String[respuesta.length + 1];
		temp[0] = calendario.get(Calendar.YEAR) + "";
		System.arraycopy(respuesta, 0, temp, 1, respuesta.length);
		return temp;
	}

	public String [] getListaMeses(String primero) {
		
		return new String [] {primero, "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public int getMesActual() {
		
		calendario = new GregorianCalendar();
		return calendario.get(Calendar.MONTH) + 1;
	}

	public DefaultTableModel getTablaValores(String a�o, int mes, boolean agregar) {
		
		valores = mercadoValoresDAO.getListado("");
		int tama�o = mercadoValoresDAO.getCotizaciones(a�o, mes, valores);
		String tabla[][] = new String[valores.length + 1][5];
		String titulo[] = new String[tama�o + (agregar? 4: 3)];
		System.arraycopy(new String[]{"Nombre", "Cant.", "Custodio"}, 0, titulo, 0, 3);
		double totales[] = new double[tama�o];
		
		for(int i = 0; i < valores.length; i++) {
			
			tabla[i][0] = valores[i].getNombre();
			tabla[i][1] = formatoResultado.format(valores[i].getCant());
			tabla[i][2] = valores[i].getCustodia().getNombre();
			
			for(int e = 0; e < tama�o; e++) {
			
				if(valores[i].getCotizaciones()[e].getValor() > 0)
					tabla[i][e + 3] = formatoResultado.format(valores[i].getCotizaciones()[e].getValor());
				else
					tabla[i][e + 3] = "-";
				titulo[e + 3] = valores[i].getCotizaciones()[e].getFecha();
				totales[e] += valores[i].getCotizaciones()[e].getValor() * valores[i].getCant();
				tabla[i + 1][e + 3] = formatoResultado.format(totales[e]);
				
				if(e == tama�o - 1)
					suma = totales[e];
			}
		}
		
		if(agregar)
			titulo[titulo.length - 1] = getFechaActual();
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
	
	public String getFechaActual() {
		
		calendario = new GregorianCalendar();
		DecimalFormat formato = new DecimalFormat("00");
		String dia = formato.format(calendario.get(Calendar.DAY_OF_MONTH));
		String mes = formato.format((calendario.get(Calendar.MONTH) + 1));
		return dia + "/" + mes + "/" + calendario.get(Calendar.YEAR);
	}
	
	public String [] getListaCustodias() {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		custodios = proveedoresDAO.getListado("", "M");
		String respuesta[] = new String [custodios.length + 1];
		respuesta[0] = "Selecciones una opci�n";
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
		respuesta[0] = "Selecciones una opci�n";
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
				
				msgError = "El valor del d�a est� comprendido entre 1 y 31";
				return false;
			}
			
			if(Integer.parseInt(partes[1]) < 1 || Integer.parseInt(partes[1]) > 12) {
				
				msgError = "El valor del mes est� comprendido entre 1 y 12";
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

			msgError = "El precio debe ser un valor num�rico.";
			return false;
		}
		return true;		
	}
	
	public boolean setCantidad(String cant) {
		
		try {
		
			operacion.setCant(Double.parseDouble(cant));
			valor.setCant(Double.parseDouble(cant));
		} catch (Exception e) {

			msgError = "La cantidad debe ser un valor num�rico.";
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

			msgError = "El valor de la comisi�n debe ser num�rico.";
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
			
			msgError = "Debe seleccionar el tipo de intrumento que se est� operando.";
			return false;
		}
		valor.setInstrumento(instrumentos[pos -1]);
		return true;
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean guardarCompra() {  
				
		if(valor.getCustodia() == null)
			valor.setCustodia(custodia);
		operacion.setIdCustodia(custodia.getId());

		if(mercadoValoresDAO.newValor(valor)) {

			operacion.setIdValores(valor.getId());
			
			if(operacionDAO.newCompra(operacion)) {
				
				msgError = "Se guard� correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}
	
	public boolean guardarCotizaciones(JTable tablaCotizaciones) {
		
		int ultimaColumna = tablaCotizaciones.getColumnCount() - 1;
		Cotizacion cot[] = new Cotizacion[valores.length];
		
		try {
			
			for(int i = 0; i < valores.length; i++) {
				
				cot[i] = new Cotizacion();
				cot[i].setFecha(getFechaActual());
				cot[i].setValor(Double.parseDouble((String)tablaCotizaciones.getValueAt(i, ultimaColumna)));
				cot[i].setIdValores(valores[i].getId());
			}			
		} catch (Exception e) {

			msgError = "Verifique la informaci�n de los campos.";
			return false;
		}
		
		if(mercadoValoresDAO.newCotizaciones(cot))
			return true;
		msgError = "Error al intentar guardar la informaci�n en la base de datos.";
		return false;
	}

	public String getCantValores() {
		
		return valores.length + "";
	}

	public String getSuma() {
		
		return formatoResultado.format(suma);
	}
	
	public String getNombreValor() {
		
		return valor.getNombre();
	}
	
	public String getCantValor() {
		
		return String.format("%.2f", valor.getCant());
	}
	
	public DefaultTableModel getListadoOperaciones() {
		
		if(!operacionDAO.getListaOperaciones(valor))
			System.out.println("Algo sali� mal");
		String titulo[] = {"Fecha", "Operaci�n", "Cantidad", "Precio", "Comisi�n", "Comentario"};
		String tabla[][] = new String [valor.getCotizaciones().length][7];
		int i = 0;
		
		for(Operacion oper: valor.getOperaciones()) {
			
			tabla[i][0] = oper.getFecha();
			tabla[i][1] = oper.getOperacion();
			tabla[i][2] = formatoResultado.format(oper.getCant());
			tabla[i][3] = formatoResultado.format(oper.getPrecio());
			tabla[i][4] = formatoResultado.format(oper.getComision());
			tabla[i][5] = oper.getComentario();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public boolean guardarVenta() {
				
		if(valor.getId() == 0) {

			msgError = "No se ha defino el valor a operar.";
			return false;
		}
		
		if(valor.getCustodia() == null)
			valor.setCustodia(custodia);
		operacion.setIdCustodia(custodia.getId());
		operacion.setIdValores(valor.getId());
			
		if(operacionDAO.newVenta(operacion)) {
			
			msgError = "Se guard� correctamente la operacion.";
			return true;
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}
}