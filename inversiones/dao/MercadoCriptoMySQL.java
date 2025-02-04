package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Cripto;

public class MercadoCriptoMySQL extends ConexiónMySQL implements MercadoCriptoDAO {
	
	@Override
	public String [] getAñosCargados() {
		
		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.operaciones WHERE idCripto IS NOT NULL "
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
			System.err.println("MercadoCriptoMySQL, getAñosCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public boolean update(Cripto cripto) {
		
		boolean bandera = true;
		String cmdStm = "SELECT id FROM gpiygdb.cripto WHERE idCustodia = ? AND idCriptoMoneda = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			ResultSet rs;
			
			if(cripto.getId() == 0) {
				
				stm.setInt(1, cripto.getCustodia().getId());
				stm.setInt(2, cripto.getMoneda().getId());
				rs = stm.executeQuery();
				
				if(rs.next())
					cripto.setId(rs.getInt("id"));
			}
			
			if(cripto.getId() == 0)
				cmdStm = "INSERT INTO gpiygdb.cripto (idCriptoMoneda, cant, idCustodia) VALUES (?, ?, ?)";
			else
				cmdStm = "UPDATE gpiygdb.cripto SET idCriptoMoneda = ?, cant = ROUND(cant + ?, 8), idCustodia = ? WHERE id = ?";
			stm = conexion.prepareStatement(cmdStm);
			stm.setInt(1, cripto.getMoneda().getId());
			stm.setDouble(2, cripto.getCant());
			stm.setInt(3, cripto.getCustodia().getId());
			
			if(cripto.getId() != 0)
				stm.setInt(4, cripto.getId());
			stm.executeUpdate();
			
			if(cripto.getId() == 0) {
				
				cmdStm = "SELECT id FROM gpiygdb.cripto ORDER BY id DESC LIMIT 1";
				rs = stm.executeQuery(cmdStm);
				
				if(rs.next())
					cripto.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoCriptoMySQL, update");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public double getSaldo(Cripto cripto) {

		double disponible = 0;
		String cmdStm = "SELECT cant FROM gpiygdb.cripto WHERE idCustodia = ? AND idCriptoMoneda = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setInt(1, cripto.getCustodia().getId());
			stm.setInt(2, cripto.getMoneda().getId());
			ResultSet rs = stm.executeQuery();
			
			if(rs.next())
				disponible = rs.getDouble(1);
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoFiatMySQL, newMovimiento");
		} finally {
		
			this.cerrar();
		}
		return disponible;
	}
}