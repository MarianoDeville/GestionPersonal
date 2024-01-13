package modelo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DtosComunes {

	private static Calendar calendario;
	
	public static String [] getListaMeses(String comienzo) {
		
		return new String [] {comienzo, "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public static int getMesActual() {
		
		calendario = new GregorianCalendar();
		return calendario.get(Calendar.MONTH) + 1;
	}
	
	public static String getFechaActual() {
		
		calendario = new GregorianCalendar();
		DecimalFormat formato = new DecimalFormat("00");
		String dia = formato.format(calendario.get(Calendar.DAY_OF_MONTH));
		String mes = formato.format((calendario.get(Calendar.MONTH) + 1));
		return dia + "/" + mes + "/" + calendario.get(Calendar.YEAR);
	}	
}