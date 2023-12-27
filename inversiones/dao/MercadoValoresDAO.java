package dao;

import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getA�osCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListadoValores(String filtro);
}
