package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.ClasificacionEgreso;
import modelo.Egreso;
import modelo.Proveedor;
import modelo.Transaccion;

public class EgresosMySQL extends Conexi�nMySQL implements EgresosDAO {
	
	@Override
	public String [] getA�osCargados() {

		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.egresos GROUP BY YEAR(fecha) ORDER BY fecha DESC LIMIT 10");
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
			System.err.println("EgresosMySQL, getA�osCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public ClasificacionEgreso [] getDestino() {
	
		ClasificacionEgreso respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, descripcion FROM gpiygdb.destino");
			rs.last();	
			respuesta = new ClasificacionEgreso[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new ClasificacionEgreso();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getDestino");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public Egreso [] getListado(String a�o, int mes, int idTipoConsumo, int idFormaPago, String moneda, String filtro) {

		Egreso respuesta[] = new Egreso[0];
		String cmdStm = "SELECT egresos.id, DATE_FORMAT(fecha, '%d/%m/%Y') AS fecha, ROUND(monto, 2) AS monto, moneda, cotizacion, "
							+ "proveedores.nombre, proveedores.id, transaccion.descripcion, destino.descripcion, egresos.comentario "
						+ "FROM gpiygdb.egresos "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idProveedor "
						+ "JOIN gpiygdb.transaccion ON transaccion.id = idFormaPago "
						+ "JOIN gpiygdb.destino ON destino.id = idTipoGasto "
						+ "WHERE (YEAR(fecha) = ? ";
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		
		if(idTipoConsumo != 0)
			cmdStm += " AND idTipoGasto = " + idTipoConsumo;
		
		if(idFormaPago != 0)
			cmdStm += " AND idFormaPago = " + idFormaPago;
		
		switch (moneda) {
		case "P":
			
			cmdStm += " AND moneda = 'Pesos'";
			break;

		case "PU":
			
			cmdStm += " AND (moneda = 'Pesos' OR moneda = 'D�lares')";
			break;
			
		case "PE":
			
			cmdStm += " AND (moneda = 'Pesos' OR moneda = 'Euros')";
			break;
			
		case "UE":
			
			cmdStm += " AND (moneda = 'Euros' OR moneda = 'D�lares')";
			break;
		case "E":
			
			cmdStm += " AND moneda = 'Euros'";
			break;
			
		case "U":
			
			cmdStm += " AND moneda = 'D�lares'";
			break;
		}
		cmdStm += " AND proveedores.nombre LIKE ?) ORDER BY fecha DESC, egresos.id DESC"; 

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, a�o);
			stm.setString(2, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Egreso[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while (rs.next()) {
				
				respuesta[i] = new Egreso();
				respuesta[i].setId(rs.getInt("egresos.id"));
				respuesta[i].setFecha(rs.getString("fecha"));
				respuesta[i].setMonto(rs.getDouble("monto"));
				respuesta[i].setMoneda(rs.getNString("moneda"));
				respuesta[i].setCotizacion(rs.getFloat("cotizacion"));
				respuesta[i].setComentario(rs.getString("egresos.comentario"));
				respuesta[i].setProveedor(new Proveedor());
				respuesta[i].getProveedor().setNombre(rs.getString("proveedores.nombre"));
				respuesta[i].getProveedor().setId(rs.getInt("proveedores.id"));
				respuesta[i].setFormaPago(new Transaccion());
				respuesta[i].getFormaPago().setDescripcion(rs.getString("transaccion.descripcion"));
				respuesta[i].setTipoConsumo(new ClasificacionEgreso());
				respuesta[i].getTipoConsumo().setDescripcion(rs.getString("destino.descripcion"));
				i++;
			}
		} catch (Exception e) {

			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getListado");
		} finally {

			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public boolean nuevo(Egreso egreso) {
		
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.egresos "
						+ "(fecha, monto, moneda, cotizacion, comentario, idProveedor, idFormaPago, idTipoGasto) "
						+ "VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setString(3, egreso.getMoneda());
			stm.setDouble(4, egreso.getCotizacion());
			stm.setString(5, egreso.getComentario());
			stm.setInt(6, egreso.getProveedor().getId());
			stm.setInt(7, egreso.getFormaPago().getId());
			stm.setInt(8, egreso.getTipoConsumo().getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, nuevo");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public boolean update(Egreso egreso) {
		
		boolean bandera = true;
		String cmdStm = "UPDATE gpiygdb.egresos SET fecha = ?, monto = ROUND(?, 2), moneda = ?, cotizacion = ?, "
						+ "comentario = ?, idProveedor = ?, idFormaPago = ?, idTipoGasto = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setString(3, egreso.getMoneda());
			stm.setDouble(4, egreso.getCotizacion());
			stm.setString(5, egreso.getComentario());
			stm.setInt(6, egreso.getProveedor().getId());
			stm.setInt(7, egreso.getFormaPago().getId());
			stm.setInt(8, egreso.getTipoConsumo().getId());
			stm.setInt(9, egreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, update");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public boolean delete(Egreso egreso) {
		
		boolean bandera = true;

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement("DELETE FROM gpiygdb.egresos WHERE id = ?");
			stm.setInt(1, egreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, delete");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
}