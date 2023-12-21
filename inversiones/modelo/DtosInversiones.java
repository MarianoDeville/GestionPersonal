package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import dao.InversionesDAO;
import dao.InversionesMySQL;

public class DtosInversiones {
	
	InversionesDAO inversionesDAO = new InversionesMySQL();
	private Inversion inversiones[];
	
	
	private Calendar calendario;
		
	public String [] getListaAños() {
		
		String respuesta[] = null;
		calendario = new GregorianCalendar();
		respuesta = inversionesDAO.getAñosCargados();
		
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
	
	
	
	
	
	
	
	
	
	
	
}