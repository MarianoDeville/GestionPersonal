package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Fiat;
import modelo.Proveedor;

public class MercadoFiatMySQL extends ConexiónMySQL implements MercadoFiatDAO {

	@Override
	public String [] getAñosCargados() {
		
		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.operaciones WHERE idFiat IS NOT NULL "
											+ "GROUP BY YEAR(fecha) ORDER BY fecha DESC LIMIT 20");
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
	public Fiat [] getListado(boolean agrupar) {
		
		Fiat respuesta[] = null;
		String cmdStm = "SELECT fiat.id, moneda, SUM(cant), idCustodia, proveedores.nombre "
						+ "FROM gpiygdb.fiat "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idCustodia "
						+ "WHERE cant > 0 ";
		if(agrupar)
			cmdStm += "GROUP BY moneda ";
		cmdStm += "ORDER BY idCustodia";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Fiat[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
				
				respuesta[i] = new Fiat();
				respuesta[i].setId(rs.getInt("fiat.id"));
				respuesta[i].setMoneda(rs.getString("moneda"));
				respuesta[i].setCant(rs.getDouble("SUM(cant)"));
				respuesta[i].setCustodia(new Proveedor());
				respuesta[i].getCustodia().setId(rs.getInt("idCustodia"));
				respuesta[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoFiatMySQL, getListado");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public boolean newFiat(Fiat fiat) {

		boolean bandera = true;
		String cmdStm = null;
		if(fiat.getId() == 0)
			cmdStm = "INSERT INTO gpiygdb.fiat (moneda, cant, idCustodia) VALUES (?, ?, ?)";
		else
			cmdStm = "UPDATE gpiygdb.fiat SET moneda = ?, cant = cant + ?, idCustodia = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, fiat.getMoneda());
			stm.setDouble(2, fiat.getCant());
			stm.setInt(3, fiat.getCustodia().getId());
			
			if(fiat.getId() != 0)
				stm.setInt(4, fiat.getId());
			stm.executeUpdate();
			
			if(fiat.getId() == 0) {
				
				cmdStm = "SELECT id FROM gpiygdb.fiat ORDER BY id DESC LIMIT 1";
				ResultSet rs = stm.executeQuery(cmdStm);
				
				if(rs.next()) {

					fiat.setId(rs.getInt(1));
				}
			}
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, createCompra");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}
}