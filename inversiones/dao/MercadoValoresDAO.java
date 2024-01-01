package dao;

import modelo.Cotizacion;
import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getA�osCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListado(String filtro);
	public Cotizacion [] getCotizaciones(String a�o, int mes);
	public boolean newValor(Valores valor);
}
