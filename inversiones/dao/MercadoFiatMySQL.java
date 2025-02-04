package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Fiat;

public class MercadoFiatMySQL extends ConexiónMySQL implements MercadoFiatDAO {

	@Override
	public String [] getAñosCargados() {
		
		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.operaciones WHERE idFiat IS NOT NULL "
											+ "GROUP BY YEAR(fecha) ORDER BY YEAR(fecha) DESC LIMIT 20");
			rs.last();	
			respuesta = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = rs.getString(1);
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("MercadoFiatMySQL, getAñosCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public boolean update(Fiat fiat) {

		boolean bandera = true;
		String cmdStm = "SELECT id FROM gpiygdb.fiat WHERE idCustodia = ? AND idMoneda = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			ResultSet rs;
			
			if(fiat.getId() == 0) {
				
				stm.setInt(1, fiat.getCustodia().getId());
				stm.setInt(2, fiat.getMoneda().getId());
				rs = stm.executeQuery();
				
				if(rs.next())
					fiat.setId(rs.getInt("id"));
			}
			if(fiat.getId() == 0)
				cmdStm = "INSERT INTO gpiygdb.fiat (idMoneda, cant, idCustodia) VALUES (?, ?, ?)";
			else
				cmdStm = "UPDATE gpiygdb.fiat SET idMoneda = ?, cant = cant + ?, idCustodia = ? WHERE id = ?";
			stm = conexion.prepareStatement(cmdStm);
			stm.setInt(1, fiat.getMoneda().getId());
			stm.setDouble(2, fiat.getCant());
			stm.setInt(3, fiat.getCustodia().getId());
			
			if(fiat.getId() != 0)
				stm.setInt(4, fiat.getId());
			stm.executeUpdate();
			
			if(fiat.getId() == 0) {
				
				cmdStm = "SELECT id FROM gpiygdb.fiat ORDER BY id DESC LIMIT 1";
				rs = stm.executeQuery(cmdStm);
				
				if(rs.next())
					fiat.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoFiatMySQL, update");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public double getSaldo(Fiat moneda) {

		double disponible = 0;
		String cmdStm = "SELECT cant FROM gpiygdb.fiat WHERE idCustodia = ? AND idMoneda = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setInt(1, moneda.getCustodia().getId());
			stm.setInt(2, moneda.getMoneda().getId());
			ResultSet rs = stm.executeQuery();
			
			if(rs.next())
				disponible = rs.getDouble(1);
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoFiatMySQL, getSaldo");
		} finally {
		
			this.cerrar();
		}
		return disponible;
	}
}