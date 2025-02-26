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
import dao.MercadoValoresDAO;
import dao.MercadoValoresMySQL;
import dao.OperacionesDAO;
import dao.OperacionesMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;
import dao.TransaccionDAO;
import dao.TransaccionMySQL;

public class DtosMercadoValores {
	
	private MercadoValoresDAO mercadoValoresDAO = new MercadoValoresMySQL();
	private OperacionesDAO operacionDAO = new OperacionesMySQL();
	private DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
	private Proveedor custodios[];
	private Proveedor custodia;
	private Instrumento instrumentos[];
	private static Valores valores[];
	private static Valores valor;
	private Operacion operacion;
	private Ingreso ingreso;
	private Egreso egreso;
	private String msgError;
	private double suma;
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
	
	public DefaultTableModel getTablaValores(String año, int mes, boolean agregar, boolean existente, boolean trading) {
		
		CotizacioonesDAO cotizacionesDAO = new CotizacionesMySQL();
		valores = operacionDAO.getListadoValores(año, mes, existente, trading);
		int tamaño = cotizacionesDAO.getCotizaciones(año, mes, valores);
		String tabla[][] = new String[valores.length + 1][tamaño + (agregar? 4: 3)];
		String titulo[] = new String[tamaño + (agregar? 4: 3)];
		System.arraycopy(new String[]{"Nombre", "Custodio", "Cant."}, 0, titulo, 0, 3);
		double totales[] = new double[tamaño];
		
		for(int i = 0; i < valores.length; i++) {
			
			tabla[i][0] = valores[i].getNombre();
			tabla[i][1] = valores[i].getCustodia().getNombre();
			tabla[i][2] = formatoResultado.format(valores[i].getCant());
			
			for(int e = 0; e < tamaño; e++) {
			
				if(valores[i].getCotizaciones()[e].getValor() > 0)
					tabla[i][e + 3] = formatoResultado.format(valores[i].getCotizaciones()[e].getValor());
				else
					tabla[i][e + 3] = "-";
				titulo[e + 3] = valores[i].getCotizaciones()[e].getFecha();
				totales[e] += valores[i].getCotizaciones()[e].getValor() * valores[i].getCant();
				tabla[i + 1][e + 3] = formatoResultado.format(totales[e]);
				
				if(e == tamaño - 1)
					suma = totales[e];
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

	public String getSuma() {
		
		return formatoResultado.format(suma);
	}
	
	public String getCantValores() {
		
		return valores.length + "";
	}

	public void setValor(int pos) {
		
		valor = valores[pos];
	}
	
	public boolean guardarCotizaciones(JTable tablaCotizaciones) {
		
		int ultimaColumna = tablaCotizaciones.getColumnCount() - 1;
		Cotizacion cot[] = new Cotizacion[valores.length];
		
		try {
			
			for(int i = 0; i < valores.length; i++) {
				
				cot[i] = new Cotizacion();
				cot[i].setFecha(DtosComunes.getFechaActual());
				cot[i].setValor(Double.parseDouble((String)tablaCotizaciones.getValueAt(i, ultimaColumna)));
				cot[i].setIdValores(valores[i].getId());
			}			
		} catch (Exception e) {

			msgError = "Verifique la información de los campos.";
			return false;
		}
		
		if(mercadoValoresDAO.newCotizaciones(cot))
			return true;
		msgError = "Error al intentar guardar la información en la base de datos.";
		return false;
	}
	
	public String getMsgError() {
		
		return msgError;
	}

	public String [] getListaCustodias() {
		
		ProveedorDAO proveedoresDAO = new ProveedorMySQL();
		custodios = proveedoresDAO.getListado("", "M");
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
	
	public String getCustodiaValor() {
		
		return valor.getCustodia().getNombre() + " - " + valor.getCustodia().getMercado();
	}

	public String getTipoInstrumento() {
	
		return valor.getInstrumento().getNombre();
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

	public void setMonedaCompra(String moneda) {
	
		if(egreso == null)
			egreso = new Egreso();
		egreso.setMoneda(moneda);
	}
	
	public void setComentario(String comentario) {
	
		operacion = new Operacion();
		operacion.setComentario(comentario);
	}

	public void setTipoOperacion(String operacion) {
		
		this.operacion.setOperacion(operacion);
	}
	
	public boolean setNombre(String nombre) {
		
		if(nombre.length() < 2) {
			
			msgError = "No ha definido el nombre de la especie.";
			return false;
		}

		valor.setNombre(nombre);
		return true;
	}
	
	public void setPlazo(boolean plazo) {
		
		if(valor == null)
			valor = new Valores();
		valor.setPlazo(plazo? 1:0);
	}

	public boolean getPlazo() {
	
		return valor.getPlazo()==1? true: false;
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

	public boolean setCustodia(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe seleccionar una custodia.";
			return false;
		}
		custodia = custodios[pos - 1];
		return true;
	}

	public boolean setTipoInstrumento(int pos) {
		
		if(pos == 0) {
			
			msgError = "Debe seleccionar el tipo de intrumento que se está operando.";
			return false;
		}
		valor.setInstrumento(instrumentos[pos -1]);
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

	public boolean setCantidad(String cant) {
		
		try {
			
			if(cant.contains(",")) {
				
				cant = cant.replace(".", "");
				cant = cant.replace(",", ".");
			}
			operacion.setCant(Double.parseDouble(cant));
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

			msgError = "El valor de la comisión debe ser numérico.";
			return false;
		}
		return true;
	}

	public boolean guardarCompra() {  // como es una compra solo debo cargar el egreso
		
		valor.setCustodia(custodia);
		valor.setCant(operacion.getCant());

		if(mercadoValoresDAO.update(valor)) {
			
			TransaccionDAO transaccionDAO = new TransaccionMySQL();
			EgresosDAO egresosDAO = new EgresosMySQL();			
			egreso.setFecha(operacion.getFecha());
			egreso.setMonto((operacion.getCant() * operacion.getPrecio()) + operacion.getComision());
			egreso.setCotizacion(operacion.getPrecio());
			egreso.setComentario(operacion.getComentario());
			egreso.setTipoConsumo("Ahorro / Inversión");
			egreso.setProveedor(custodia);
			egreso.setFormaPago(transaccionDAO.getId("Débito en cuenta"));
			egresosDAO.nuevo(egreso);
			operacion.setIdValores(valor.getId());
			operacion.setIdEgreso(egreso.getId());
			
			if(operacionDAO.update(operacion)) {
		
				valor = null;
				custodia = null;
				egreso = null;
				ingreso = null;
				operacion = null;
				msgError = "Se guardó correctamente la operacion.";
				return true;
			}
		}
		msgError = "Error al intentar guardar la compra.";
		return false;
	}

	public void resetValor() {
		
		valor = null;
		custodia = null;
	}

	public String getCantValor() {
		
		return String.format("%.2f", valor.getCant());
	}

	public void setMonedaVenta(String moneda) {
		
		if(ingreso == null)
			ingreso = new Ingreso();
		ingreso.setMoneda(moneda);
	}
			
	public boolean guardarVenta() {
		
		if(operacion.getCant() > valor.getCant()) {
			
			msgError = "No tiene saldo suficiente.";
			return false;			
		}
		
		if(valor.getId() == 0) {

			msgError = "No se ha defino el valor a operar.";
			return false;
		}
		valor.setCant(- operacion.getCant());
		
		if(!mercadoValoresDAO.update(valor)){

			msgError = "Error al intentar actualizar los valores.";
			return false;
		}
		IngresosDAO ingresosDAO = new IngresosMySQL();
		TransaccionDAO transaccionDAO = new TransaccionMySQL();
		ingreso.setFecha(operacion.getFecha());
		ingreso.setMonto((operacion.getCant() * operacion.getPrecio()) - operacion.getComision() );
		ingreso.setCotizacion(ingreso.getMoneda().equals("Pesos")? 1: operacion.getPrecio());
		ingreso.setComentario(operacion.getComentario());
		ingreso.setConcepto("Ahorro / Inversión");
		ingreso.setFuente(valor.getCustodia());
		ingreso.setFormaCobro(transaccionDAO.getId("Acreditación en cuenta"));
		ingresosDAO.nuevo(ingreso);
		operacion.setIdValores(valor.getId());
		operacion.setIdIngreso(ingreso.getId());
		operacion.setCant(valor.getCant());
		
		if(operacionDAO.update(operacion)) {
			
			valor = null;
			custodia = null;
			egreso = null;
			ingreso = null;
			operacion = null;
			msgError = "Se guardó correctamente la operacion.";
			return true;
		}
		msgError = "Error al intentar guardar la operación.";
		return false;
	}

	public String getNombreValor() {
		
		return valor.getNombre();
	}
	
	public DefaultTableModel getListadoOperaciones() {
	
		valor.setOperaciones(operacionDAO.getListado(valor));
		String titulo[] = {"Fecha", "Operación", "Cantidad", "Precio", "Comisión", "Comentario"};
		String tabla[][] = new String [valor.getOperaciones().length][6];
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
}