package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Cotizacion;
import modelo.Fiat;
import modelo.Moneda;
import modelo.Proveedor;
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
			System.err.println("CotizacionesMySQL, getCotizaciones(Valores)");
		} finally {
		
			this.cerrar();
		}
		return fechas.length;
	}
	
	@Override
	public Fiat [] getCotizaciones(String año, int mes) {

		Fiat monedas[] = null;
		String fechas[] = null;
		String cmdStm = "SELECT moneda.id, moneda.nombre, SUM(cant), idCustodia, proveedores.nombre "
						+ "FROM gpiygdb.fiat "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idCustodia "
						+ "JOIN gpiygdb.moneda ON moneda.id = idMoneda "
						+ "WHERE cant > 0 GROUP BY idMoneda ORDER BY idCustodia";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet  rs = stm.executeQuery();
			rs.last();
			monedas = new Fiat[rs.getRow()];
			rs.beforeFirst();
			int i = 0;			
			
			while(rs.next()) {
				
				monedas[i] = new Fiat();
				monedas[i].getMoneda().setId(rs.getInt("moneda.id"));
				monedas[i].getMoneda().setNombre(rs.getString("moneda.nombre"));
				monedas[i].setCant(rs.getDouble("SUM(cant)"));
				monedas[i].setCustodia(new Proveedor());
				monedas[i].getCustodia().setId(rs.getInt("idCustodia"));
				monedas[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));									
				i++;
			}
			cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') FROM gpiygdb.cotizaciones WHERE " 
					+ (mes != 0? "YEAR(fecha) = ? AND MONTH(fecha) = ? ": "fecha = NOW() ")
					+ "AND idFiat IS NOT NULL GROUP BY fecha";
			stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			if(mes != 0) {
				stm.setString(1, año);
				stm.setInt(2, mes);
			}
			rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			i = 0;			
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}

			for(i = 0; i < monedas.length; i++) {

				monedas[i].setCotizaciones(new Cotizacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					monedas[i].getCotizaciones()[e] = new Cotizacion();
					
					cmdStm = "SELECT cotizaciones.id, valor FROM gpiygdb.cotizaciones "
							+ "WHERE DATE_FORMAT(fecha, '%d/%m/%Y') = ? AND idFiat = ?";
					
					stm = conexion.prepareStatement(cmdStm);
					stm.setString(1, fechas[e]);
					stm.setInt(2, monedas[i].getMoneda().getId());
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
			System.err.println("CotizacionesMySQL, getCotizaciones(FIAT)");
		} finally {
		
			this.cerrar();
		}
		return monedas;
	}
	
	@Override
	public boolean update(Fiat monedas[]) {
		
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.cotizaciones (fecha, valor, idFiat) "
						+ "VALUES (STR_TO_DATE(?, '%d/%m/%Y'), ? ,?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, monedas[0].getCotizaciones()[0].getFecha());
			
			for(Fiat moneda: monedas) {
			
				stm.setDouble(2, moneda.getCotizaciones()[0].getValor());
				stm.setInt(3,  moneda.getMoneda().getId());
				stm.executeUpdate();
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("CotizacionesMySQL, update(Fiat)");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public double getUltima(String moneda) {
		
		double valor = 1;
		String cmdStm = "SELECT valor FROM gpiygdb.cotizaciones "
						+ "JOIN gpiygdb.fiat ON fiat.id = idFiat "
						+ "WHERE moneda = ? ORDER BY fecha DESC LIMIT 1";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, moneda);
			ResultSet  rs = stm.executeQuery();
			
			if(rs.next()) 
				valor = rs.getDouble(1);
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("CotizacionesMySQL, getUltima()");
		} finally {
		
			this.cerrar();
		}
		return valor;
	}
	
	@Override
	public Moneda [] getMonedas() {
		
		Moneda monedas[] = null;
		String cmdStm = "SELECT id, nombre FROM gpiygdb.moneda ORDER BY id asc";
		
		try {
			
			this.conectar();
			Statement stm = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet  rs = stm.executeQuery(cmdStm);
			rs.last();
			monedas = new Moneda[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				monedas[i] = new Moneda();
				monedas[i].setId(rs.getInt("id"));
				monedas[i].setNombre(rs.getString("nombre"));
				i++;
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("CotizacionesMySQL, getUltima()");
		} finally {
		
			this.cerrar();
		}
		return monedas;
	}
}