package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.ClasificacionEgreso;
import modelo.Egreso;
import modelo.Pago;
import modelo.Proveedor;

public class EgresosMySQL extends ConexiónMySQL implements EgresosDAO {
	
	@Override
	public String [] getAñosCargados() {

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
			System.err.println("EgresosMySQL, getAñosCargados");
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
	public Pago [] getMetodosPagos() {
		
		Pago respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, descripcion FROM gpiygdb.formaPago");
			rs.last();	
			respuesta = new Pago[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Pago();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getMetodosPagos");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Proveedor [] getListaProveedores(String filtro) {
		
		Proveedor respuesta[] = null;
		String cmdStm = "SELECT id, nombre, direccion, cuit, rubro FROM gpiygdb.proveedores WHERE nombre LIKE ?";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Proveedor[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Proveedor();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setDireccion(rs.getString(3));
				respuesta[i].setCuit(rs.getString(4));
				respuesta[i].setRubro(rs.getString(5));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getListaProveedores");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Egreso [] getListadoEgresos(String año, int mes, int idTipoConsumo, int idFormaPago, String moneda, String filtro) {

		Egreso respuesta[] = new Egreso[0];
		String cmdStm = "SELECT egresos.id, DATE_FORMAT(fecha, '%d/%m/%Y') AS fecha, ROUND(monto, 2) AS monto, moneda, cotizacion, "
							+ "proveedores.nombre, proveedores.id, formaPago.descripcion, destino.descripcion "
						+ "FROM gpiygdb.egresos "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idProveedor "
						+ "JOIN gpiygdb.formaPago ON formaPago.id = idFormPago "
						+ "JOIN gpiygdb.destino ON destino.id = idTipoGasto "
						+ "WHERE (YEAR(fecha) = ?";
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		
		if(idTipoConsumo != 0)
			cmdStm += " AND idTipoGasto = " + idTipoConsumo;
		
		if(idFormaPago != 0)
			cmdStm += " AND idFormPago = " + idFormaPago;
		
		switch (moneda) {
		case "P":
			
			cmdStm += " AND moneda = 'Pesos'";
			break;

		case "PU":
			
			cmdStm += " AND (moneda = 'Pesos' OR moneda = 'Dólares')";
			break;
			
		case "PE":
			
			cmdStm += " AND (moneda = 'Pesos' OR moneda = 'Euros')";
			break;
			
		case "UE":
			
			cmdStm += " AND (moneda = 'Euros' OR moneda = 'Dólares')";
			break;
		case "E":
			
			cmdStm += " AND moneda = 'Euros'";
			break;
			
		case "U":
			
			cmdStm += " AND moneda = 'Dólares'";
			break;
		}
		cmdStm += " AND proveedores.nombre LIKE ?) ORDER BY egresos.id DESC"; 

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
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
				respuesta[i].setProveedor(new Proveedor());
				respuesta[i].getProveedor().setNombre(rs.getString("proveedores.nombre"));
				respuesta[i].getProveedor().setId(rs.getInt("proveedores.id"));
				respuesta[i].setFormaPago(new Pago());
				respuesta[i].getFormaPago().setDescripcion(rs.getString("formaPago.descripcion"));
				respuesta[i].setTipoConsumo(new ClasificacionEgreso());
				respuesta[i].getTipoConsumo().setDescripcion(rs.getString("destino.descripcion"));
				i++;
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getListadoEgresos");
		} finally {

			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public boolean nuevoEgreso(Egreso egreso) {
		
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.egresos "
						+ "(fecha, monto, moneda, cotizacion, idProveedor, idFormPago, idTipoGasto) "
						+ "VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setString(3, egreso.getMoneda());
			stm.setDouble(4, egreso.getCotizacion());
			stm.setInt(5, egreso.getProveedor().getId());
			stm.setInt(6, egreso.getFormaPago().getId());
			stm.setInt(7, egreso.getTipoConsumo().getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, newEgreso");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public boolean updateEgreso(Egreso egreso) {
		
		boolean bandera = true;
		String cmdStm = "UPDATE gpiygdb.egresos SET fecha = ?, monto = ROUND(?, 2), moneda = ?, cotizacion = ?, idProveedor = ?, idFormPago = ?, idTipoGasto = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setString(3, egreso.getMoneda());
			stm.setDouble(4, egreso.getCotizacion());
			stm.setInt(5, egreso.getProveedor().getId());
			stm.setInt(6, egreso.getFormaPago().getId());
			stm.setInt(7, egreso.getTipoConsumo().getId());
			stm.setInt(8, egreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, updateEgreso");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
}