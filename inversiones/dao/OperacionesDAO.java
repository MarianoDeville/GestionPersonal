package dao;

import modelo.Cripto;
import modelo.Fiat;
import modelo.Inmobiliario;
import modelo.Operacion;
import modelo.Valores;

public interface OperacionesDAO {

	public boolean update(Operacion operacion);
	public Operacion [] getListado(Valores valor);
	public Fiat [] getListadoFiat(String año, int mes);
	public Cripto [] getListadoCripto(String año, int mes);
	public Valores [] getListadoValores(String año, int mes, boolean existente);
	public Inmobiliario [] getListadoPropiedades(String año, boolean activo);
}