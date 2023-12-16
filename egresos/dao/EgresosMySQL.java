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
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.egresos GROUP BY YEAR(fecha) ORDER BY YEAR(fecha)");
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
	public Proveedor [] getListaProveedores() {
		
		Proveedor respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, nombre, direccion, cuit, rubro FROM gpiygdb.proveedores");
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
	public Egreso [] getListadoEgresos(String año, int mes, int idTipoConsumo, int idFormaPago) {
		
		Egreso respuesta[] = new Egreso[0];
		String cmdStm = "SELECT id, fecha, monto, idProveedor, idFormPago, idTipoGasto FROM gpiygdb.egresos";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
				respuesta[i].setIdProveedor(rs.getInt(4));
				respuesta[i].setIdFormaPago(rs.getInt(5));
				respuesta[i].setIdTipoConsumo(rs.getInt(6));
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
}
