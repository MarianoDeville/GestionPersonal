package dao;

import modelo.Cotizacion;
import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getAñosCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListado(String filtro);
	public boolean newValor(Valores valor);
	public boolean newCotizaciones(Cotizacion cotizaciones[]);
}
