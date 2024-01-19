package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Transaccion;

public class TransaccionMySQL extends ConexiónMySQL implements TransaccionDAO {

	@Override
	public Transaccion [] getMetodos(String egresoIngreso) {
		
		Transaccion respuesta[] = null;
		String cmdStm = "SELECT id, descripcion, financiado FROM gpiygdb.transaccion WHERE (egresoIngreso = ? OR egresoIngreso = 'A')";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, egresoIngreso);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Transaccion[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while(rs.next()) {
					
				respuesta[i] = new Transaccion();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				respuesta[i].setFinanciado(rs.getInt(3));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println(cmdStm);
			System.err.println("TransaccionMySQL, getMetodos");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Transaccion getId(String nombre) {
		
		Transaccion respuesta = new Transaccion();
		String cmdStm = "SELECT id, descripcion, financiado FROM gpiygdb.transaccion WHERE descripcion = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, nombre);
			ResultSet rs = stm.executeQuery();

			if(rs.next()) {
					
				respuesta.setId(rs.getInt(1));
				respuesta.setDescripcion(rs.getString(2));
				respuesta.setFinanciado(rs.getInt(3));
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println(cmdStm);
			System.err.println("TransaccionMySQL, getId");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}