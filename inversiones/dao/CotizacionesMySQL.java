package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Cotizacion;
import modelo.Fiat;
import modelo.Valores;

public class CotizacionesMySQL extends ConexiónMySQL implements CotizacioonesDAO {
	
	@Override
	public int getCotizaciones(String año, int mes, Valores valores[]) {

		String fechas[] = null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') FROM gpiygdb.cotizaciones "
						+ "WHERE (YEAR(fecha) = ? AND MONTH(fecha) = ? AND idValores IS NOT NULL) GROUP BY fecha";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			stm.setInt(2, mes);
			ResultSet  rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;			
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}
		
			for(i = 0; i < valores.length; i++) {

				valores[i].setCotizaciones(new Cotizacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					valores[i].getCotizaciones()[e] = new Cotizacion();
					cmdStm = "SELECT id, valor FROM gpiygdb.cotizaciones WHERE DATE_FORMAT(fecha, '%d/%m/%Y') = ? AND idValores = ?";
					stm = conexion.prepareStatement(cmdStm);
					stm.setString(1, fechas[e]);
					stm.setInt(2, valores[i].getId());
					rs = stm.executeQuery();
					
					if(rs.next()) {
						
						valores[i].getCotizaciones()[e].setId(rs.getInt(1));
						valores[i].getCotizaciones()[e].setValor(rs.getDouble(2));
					}
					valores[i].getCotizaciones()[e].setFecha(fechas[e]);
				}
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getCotizaciones(Valores)");
		} finally {
		
			this.cerrar();
		}
		return fechas.length;
	}
	
	@Override
	public int getCotizaciones(String año, int mes, Fiat monedas[]) {

		String fechas[] = null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') FROM gpiygdb.cotizaciones "
						+ "WHERE (YEAR(fecha) = ? AND MONTH(fecha) = ? AND idFiat IS NOT NULL) GROUP BY fecha";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			stm.setInt(2, mes);
			ResultSet  rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;			
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}
		
			for(i = 0; i < monedas.length; i++) {

				monedas[i].setCotizaciones(new Cotizacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					monedas[i].getCotizaciones()[e] = new Cotizacion();
					cmdStm = "SELECT id, valor FROM gpiygdb.cotizaciones JOIN  gpiygdb.fiat ON idFiat = fiat.id WHERE DATE_FORMAT(fecha, '%d/%m/%Y') = ? AND moneda = ?";
					stm = conexion.prepareStatement(cmdStm);
					stm.setString(1, fechas[e]);
					stm.setString(2, monedas[i].getMoneda());
					rs = stm.executeQuery();
					
					if(rs.next()) {
						
						monedas[i].getCotizaciones()[e].setId(rs.getInt(1));
						monedas[i].getCotizaciones()[e].setValor(rs.getDouble(2));
					}
					monedas[i].getCotizaciones()[e].setFecha(fechas[e]);
				}
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getCotizaciones(FIAT)");
		} finally {
		
			this.cerrar();
		}
		return fechas.length;
	}
}
