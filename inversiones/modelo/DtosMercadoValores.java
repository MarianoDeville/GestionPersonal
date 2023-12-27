package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dao.MercadoValoresDAO;
import dao.MercadoValoresMySQL;
import dao.ProveedorDAO;
import dao.ProveedorMySQL;

public class DtosMercadoValores {
	
	MercadoValoresDAO mercadoValoresDAO = new MercadoValoresMySQL();
	private Proveedor custodios[];
	private Instrumento instrumentos[];
	private static Valores valores[];
	
	
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
		
		if(valores != null || valores.length == 0)
			valores = mercadoValoresDAO.getListadoValores(filtro);
		
		String tabla[][] = new String [valores.length][1];
		int i = 0;
		
		for(Valores valor: valores) {
			
			tabla[i][0] = valor.getNombre() + " - " + valor.getCustodia().getNombre() + " " + valor.getCustodia().getMercado();
			i++;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, new String [] {"Nombre empresa"});
		return tablaModelo;
		
		
	}
	
	
	
	
	
}