package dao;

import modelo.Cotizacion;
import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getA�osCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListado(String filtro);
	public boolean update(Valores valor);
	public boolean newCotizaciones(Cotizacion cotizaciones[]);
}
