package dao;

import java.sql.ResultSet;
import java.sql.Statement;

import modelo.Instrumento;
import modelo.Valores;

public class MercadoValoresMySQL extends ConexiónMySQL implements MercadoValoresDAO {
	
	@Override
	public String [] getAñosCargados() {

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
			System.err.println("MercadoValoresMySQL, getAñosCargados");
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
	public Valores [] getListadoValores(String filtro) {
		
		
		
		
		return null;
	}
	
	
	
	
}
