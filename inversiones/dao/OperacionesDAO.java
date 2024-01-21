package dao;

import modelo.Fiat;
import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean update(Operacion operacion);
	public Operacion [] getListado(Valores valor);
	public Fiat [] getListadoFiat(String a�o, int mes);
	public Valores [] getListadoValores(String a�o, int mes, boolean existente);
}