package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Transaccion;

public class TransaccionMySQL extends ConexiónMySQL implements TransaccionDAO {

	@Override
	public Transaccion [] getMetodos() {
		
		Transaccion respuesta[] = null;
		String cmdStm = "SELECT id, descripcion FROM gpiygdb.transaccion WHERE (egresoIngreso = 'E' OR egresoIngreso = 'A')";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			respuesta = new Transaccion[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Transaccion();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println(cmdStm);
			System.err.println("EgresosMySQL, getMetodosPagos");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
}
