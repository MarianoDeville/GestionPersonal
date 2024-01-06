package dao;

import modelo.Cotizacion;
import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getA�osCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListado(String filtro);
	public int getCotizaciones(String a�o, int mes, Valores valores[]);
	public boolean newValor(Valores valor);
	public boolean newCotizaciones(Cotizacion cotizaciones[]);
}
