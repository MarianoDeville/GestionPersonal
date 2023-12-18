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
long tiempo = System.currentTimeMillis();
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
System.out.println("getAñosCargados() - " + (System.currentTimeMillis() - tiempo));				
		return respuesta;
	}

	@Override
	public ClasificacionEgreso [] getDestino() {
long tiempo = System.currentTimeMillis();	
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
System.out.println("getDestino() - " + (System.currentTimeMillis() - tiempo));
		return respuesta;
	}
	
	@Override
	public Pago [] getMetodosPagos() {
long tiempo = System.currentTimeMillis();		
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
System.out.println("getMetodosPagos() - " + (System.currentTimeMillis() - tiempo));
		return respuesta;
	}
	
	@Override
	public Proveedor [] getListaProveedores(String filtro) {
long tiempo = System.currentTimeMillis();		
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
System.out.println("getListaProveedores() - " + (System.currentTimeMillis() - tiempo));
		return respuesta;
	}
	
	@Override
	public Egreso [] getListadoEgresos(String año, int mes, int idTipoConsumo, int idFormaPago) {
long tiempo = System.currentTimeMillis();
		Egreso respuesta[] = new Egreso[0];
		String cmdStm = "SELECT egresos.id, DATE_FORMAT(fecha, '%d/%m/%Y'), monto, proveedores.nombre, proveedores.id, formaPago.descripcion, destino.descripcion FROM gpiygdb.egresos "
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
		
		cmdStm += ")"; 

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Egreso[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while (rs.next()) {
				
				respuesta[i] = new Egreso();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setFecha(rs.getString(2));
				respuesta[i].setMonto(rs.getFloat(3));
				respuesta[i].setProveedor(new Proveedor());
				respuesta[i].getProveedor().setNombre(rs.getString(4));
				respuesta[i].getProveedor().setId(rs.getInt(5));
				respuesta[i].setFormaPago(new Pago());
				respuesta[i].getFormaPago().setDescripcion(rs.getString(6));
				respuesta[i].setTipoConsumo(new ClasificacionEgreso());
				respuesta[i].getTipoConsumo().setDescripcion(rs.getString(7));
				i++;
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getListadoEgresos");
		} finally {

			this.cerrar();
		}
System.out.println("getListadoEgresos() - " + (System.currentTimeMillis() - tiempo));
		return respuesta;
	}
	
	@Override
	public boolean nuevoEgreso(Egreso egreso) {
		
		long tiempo = System.currentTimeMillis();
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.egresos (fecha, monto, idProveedor, idFormPago, idTipoGasto) VALUES (?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setInt(3, egreso.getProveedor().getId());
			stm.setInt(4, egreso.getFormaPago().getId());
			stm.setInt(5, egreso.getTipoConsumo().getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, newEgreso");
		} finally {

			this.cerrar();
		}
System.out.println("getListadoEgresos() - " + (System.currentTimeMillis() - tiempo));
		return bandera;
	}
	
	@Override
	public boolean updateEgreso(Egreso egreso) {
		
		long tiempo = System.currentTimeMillis();
		boolean bandera = true;
		String cmdStm = "UPDATE gpiygdb.egresos SET fecha = ?, monto = ?, idProveedor = ?, idFormPago = ?, idTipoGasto = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, egreso.getFecha());
			stm.setDouble(2, egreso.getMonto());
			stm.setInt(3, egreso.getProveedor().getId());
			stm.setInt(4, egreso.getFormaPago().getId());
			stm.setInt(5, egreso.getTipoConsumo().getId());
			stm.setInt(6, egreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, updateEgreso");
		} finally {

			this.cerrar();
		}
System.out.println("getListadoEgresos() - " + (System.currentTimeMillis() - tiempo));
		return bandera;
	}
}
