package dao;

import modelo.Cripto;
import modelo.Fiat;
import modelo.Inmobiliario;
import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean update(Operacion operacion);
	public Operacion [] getListado(Valores valor);
	public Fiat [] getListadoFiat(String a�o, int mes);
	public Cripto [] getListadoCripto(String a�o, int mes);
	public Valores [] getListadoValores(String a�o, int mes, boolean existente);
	public Inmobiliario [] getListadoPropiedades(String a�o, boolean activo);
}