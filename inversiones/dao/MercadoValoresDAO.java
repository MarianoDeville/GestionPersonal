package dao;

import modelo.Instrumento;
import modelo.Valores;

public interface MercadoValoresDAO {
	
	public String [] getAņosCargados();
	public Instrumento [] getIntrumentos();
	public Valores [] getListadoValores(String filtro);
}
