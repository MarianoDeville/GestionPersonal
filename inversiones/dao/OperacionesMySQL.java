package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Operacion;
import modelo.Valores;

public class OperacionesMySQL extends ConexiónMySQL implements OperacionesDAO {
	
	@Override
	public boolean newCompra(Operacion operacion){

		boolean bandera = true;
		String cmdStm = null;
		int idTransaccion = 0;
		int idConcepto = 0;
		cmdStm = "SELECT id FROM gpiygdb.transaccion WHERE descripcion = 'Débito en cuenta'";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				idTransaccion = rs.getInt(1);
			cmdStm = "SELECT id FROM gpiygdb.destino WHERE descripcion = 'Ahorro / Inversión'";
			rs = stm.executeQuery(cmdStm);
			
			if(rs.next())
				idConcepto = rs.getInt(1);
			cmdStm = "INSERT INTO gpiygdb.egresos "
					+ "(fecha, monto, moneda, cotizacion, comentario, idProveedor, idFormaPago, idTipoGasto) "
					+ "VALUES (?, ?, ?, 1, ?, ?, ?, ?)";
			stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, operacion.getFecha());
			stm.setDouble(2, (operacion.getCant() * operacion.getPrecio()) + operacion.getComision());
			stm.setString(3, operacion.getMoneda());
			stm.setString(4, operacion.getComentario());
			stm.setInt(5, operacion.getIdCustodia());
			stm.setInt(6, idTransaccion);
			stm.setInt(7, idConcepto);
			stm.executeUpdate();
			
			if(operacion.getOperacion().equals("Compra"))	
				cmdStm = "SELECT id FROM gpiygdb.egresos ORDER BY id DESC LIMIT 1";
			rs = stm.executeQuery(cmdStm);
			String campo = "idInversion";
			int idCampo = operacion.getIdValores();

			if(operacion.getIdCripto() != 0) {
				
				campo = "idCripto";
				idCampo = operacion.getIdCripto();
			}
			
			if(operacion.getIdFiat() != 0) {
				
				campo = "idFiat";
				idCampo = operacion.getIdFiat();
			}
			
			if(operacion.getIdInmobiliario() != 0) {
				
				campo = "idInmobiliario";
				idCampo = operacion.getIdInmobiliario();
			}			
			
			if(rs.next())
				operacion.setIdEgreso(rs.getInt(1));
			cmdStm = "INSERT INTO gpiygdb.operaciones ("
				+ "fecha, operacion, cant, precio, comision, comentario, idEgreso, " + campo
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, operacion.getFecha());
			stm.setString(2, operacion.getOperacion());
			stm.setDouble(3, operacion.getCant());
			stm.setDouble(4, operacion.getPrecio());
			stm.setDouble(5, operacion.getComision());
			stm.setString(6, operacion.getComentario());
			stm.setInt(7, operacion.getIdEgreso());
			stm.setInt(8, idCampo);
			stm.executeUpdate();
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, newCompra");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}

	@Override
	public boolean newVenta(Operacion operacion){

		boolean bandera = true;
		String cmdStm = null;
		int idTransaccion = 0;
		int idConcepto = 0;
		cmdStm = "SELECT id FROM gpiygdb.transaccion WHERE descripcion = 'Acreditación en cuenta'";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				idTransaccion = rs.getInt(1);
			cmdStm = "SELECT id FROM gpiygdb.concepto WHERE descripcion = 'Venta activos/pasivos'";
			rs = stm.executeQuery(cmdStm);
			
			if(rs.next())
				idConcepto = rs.getInt(1);

			cmdStm = "INSERT INTO gpiygdb.ingresos "
					+ "(fecha, monto, moneda, cotizacion, comentario, idFuente, idFormaCobro, idConcepto) "
						+ "VALUES (?, ?, ?, 1, ?, ?, ?, ?)";
			stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, operacion.getFecha());
			stm.setDouble(2, (operacion.getCant() * operacion.getPrecio()) + operacion.getComision());
			stm.setString(3, operacion.getMoneda());
			stm.setString(4, operacion.getComentario());
			stm.setInt(5, operacion.getIdCustodia());
			stm.setInt(6, idTransaccion);
			stm.setInt(7, idConcepto);
			stm.executeUpdate();
			cmdStm = "SELECT id FROM gpiygdb.ingresos ORDER BY id DESC LIMIT 1";
			String campo = "idInversion";
			int idCampo = operacion.getIdValores();

			if(operacion.getIdCripto() != 0) {
				
				campo = "idCripto";
				idCampo = operacion.getIdCripto();
			}
			
			if(operacion.getIdFiat() != 0) {
				
				campo = "idFiat";
				idCampo = operacion.getIdFiat();
			}
			
			if(operacion.getIdInmobiliario() != 0) {
				
				campo = "idInmobiliario";
				idCampo = operacion.getIdInmobiliario();
			}	
			
			if(rs.next())
				operacion.setIdIngreso(rs.getInt(1));
			cmdStm = "INSERT INTO gpiygdb.operaciones ("
					+ "fecha, operacion, cant, precio, comision, comentario, idIngreso, " + campo
					+ ")VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, operacion.getFecha());
			stm.setString(2, operacion.getOperacion());
			stm.setDouble(3, operacion.getCant());
			stm.setDouble(4, operacion.getPrecio());
			stm.setDouble(5, operacion.getComision());
			stm.setString(6, operacion.getComentario());
			stm.setInt(7, operacion.getIdIngreso());
			stm.setInt(8, idCampo);
			stm.executeUpdate();
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, newVenta");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}

	@Override
	public boolean getListaOperaciones(Valores valor) {
		
		boolean bandera = true;
		String cmdStm = "SELECT id, DATE_FORMAT(fecha, '%d/%m/%Y'), operacion, cant, precio, comision, comentario "
						+ "FROM gpiygdb.operaciones WHERE idInversion = ? ORDER BY fecha DESC";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, valor.getId());
			ResultSet rs = stm.executeQuery();
			rs.last();
			valor.setOperaciones(new Operacion[rs.getRow()]);
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {

				valor.getOperaciones()[i] = new Operacion();
				valor.getOperaciones()[i].setId(rs.getInt(1));
				valor.getOperaciones()[i].setFecha(rs.getString(2));
				valor.getOperaciones()[i].setOperacion(rs.getString(3));
				valor.getOperaciones()[i].setCant(rs.getDouble(4));
				valor.getOperaciones()[i].setPrecio(rs.getDouble(5));
				valor.getOperaciones()[i].setComision(rs.getDouble(6));
				valor.getOperaciones()[i].setComentario(rs.getString(7));
				i++;
			}
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getListaOperaciones");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}
	
	
	
	
	
}