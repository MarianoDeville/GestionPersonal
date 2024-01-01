package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.Cotizacion;
import modelo.Instrumento;
import modelo.Proveedor;
import modelo.Valores;

public class MercadoValoresMySQL extends Conexi�nMySQL implements MercadoValoresDAO {
	
	@Override
	public String [] getA�osCargados() {

		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.operaciones GROUP BY YEAR(fecha) ORDER BY fecha DESC LIMIT 20");
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
			System.err.println("MercadoValoresMySQL, getA�osCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Instrumento [] getIntrumentos() {
		
		Instrumento respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, nombre, descripcion FROM gpiygdb.instrumento");
			rs.last();	
			respuesta = new Instrumento[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
				
				respuesta[i] = new Instrumento();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setDescripcion(rs.getString(3));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getIntrumentos");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Valores [] getListado(String filtro) {
		
		Valores respuesta[] = null;
		String cmdStm = "SELECT valores.id, valores.nombre, cant, idTipo, idCustodia, instrumento.nombre, proveedores.nombre, mercado "
						+ "FROM gpiygdb.valores "
						+ "JOIN gpiygdb.instrumento ON instrumento.id = idTipo "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idCustodia "
						+ "WHERE valores.nombre LIKE ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Valores[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
				
				respuesta[i] = new Valores();
				respuesta[i].setId(rs.getInt("valores.id"));
				respuesta[i].setNombre(rs.getString("valores.nombre"));
				respuesta[i].setCant(rs.getDouble("cant"));
				respuesta[i].setInstrumento(new Instrumento());
				respuesta[i].getInstrumento().setId(rs.getInt("idTipo"));
				respuesta[i].getInstrumento().setNombre(rs.getString("instrumento.nombre"));
				respuesta[i].setCustodia(new Proveedor());
				respuesta[i].getCustodia().setId(rs.getInt("idCustodia"));
				respuesta[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));
				respuesta[i].getCustodia().setMercado(rs.getString("mercado"));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getListado");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Cotizacion [] getCotizaciones(String a�o, int mes) {
		
		Cotizacion respuesta[] = null;
		
		
		
		return respuesta;
	}////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean newValor(Valores valor) {

		boolean bandera = true;
		String cmdStm = null;
		if(valor.getId() == 0)
			cmdStm = "INSERT INTO gpiygdb.valores (nombre, cant, idTipo, idCustodia) VALUES (?, ?, ?, ?)";
		else
			cmdStm = "UPDATE gpiygdb.valores SET nombre = ?, cant = cant + ?, idTipo = ?, idCustodia = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, valor.getNombre());
			stm.setDouble(2, valor.getCant());
			stm.setInt(3, valor.getInstrumento().getId());
			stm.setInt(4, valor.getCustodia().getId());
			
			if(valor.getId() != 0)
				stm.setInt(5, valor.getId());
			stm.executeUpdate();
			
			if(valor.getId() == 0) {
				
				cmdStm = "SELECT id FROM gpiygdb.valores ORDER BY id DESC LIMIT 1";
				ResultSet rs = stm.executeQuery(cmdStm);
				
				if(rs.next()) {

					valor.setId(rs.getInt(1));
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
