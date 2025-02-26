package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import modelo.Egreso;
import modelo.Proveedor;
import modelo.Transaccion;

public class EgresosMySQL extends ConexiónMySQL implements EgresosDAO {
	
	@Override
	public String [] getAñosCargados() {

		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.egresos GROUP BY YEAR(fecha) ORDER BY YEAR(fecha) DESC LIMIT 10");
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
	public Egreso [] getListado(String año, int mes, String tipoConsumo, int idFormaPago, String moneda, String filtro, int gastoFijo) {

		Egreso respuesta[] = new Egreso[0];
		String cmdStm = "SELECT egresos.id, DATE_FORMAT(fecha, '%d/%m/%Y') AS fecha, ROUND(monto, 2) AS monto, moneda, cotizacion, "
							+ "proveedores.nombre, proveedores.id, transaccion.descripcion, tipoGasto, egresos.comentario, fijo, cuotas, financiado "
						+ "FROM gpiygdb.egresos "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idProveedor "
						+ "JOIN gpiygdb.transaccion ON transaccion.id = idFormaPago "
						+ "WHERE (YEAR(fecha) = ? ";
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		
		if(!tipoConsumo.equals("Todos"))
			cmdStm += " AND tipoGasto = '" + tipoConsumo + "'";
		
		if(idFormaPago != 0)
			cmdStm += " AND idFormaPago = " + idFormaPago;
		
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
		cmdStm += " AND proveedores.nombre LIKE ? AND fijo >= ?) ORDER BY fecha DESC, egresos.id DESC"; 

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			stm.setString(2, "%" + filtro + "%");
			stm.setInt(3, gastoFijo);
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
				respuesta[i].setGastoFijo(rs.getInt("fijo"));
				respuesta[i].setCuotas(rs.getInt("cuotas"));
				respuesta[i].setProveedor(new Proveedor());
				respuesta[i].setTipoConsumo(rs.getString("tipoGasto"));
				respuesta[i].getProveedor().setNombre(rs.getString("proveedores.nombre"));
				respuesta[i].getProveedor().setId(rs.getInt("proveedores.id"));
				respuesta[i].setFormaPago(new Transaccion());
				respuesta[i].getFormaPago().setDescripcion(rs.getString("transaccion.descripcion"));
				respuesta[i].getFormaPago().setFinanciado(rs.getInt("financiado"));
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
		
		if(egreso.getMonto() == 0)
			return false;
		boolean bandera = true;
		int i = 1;
		String cmdStm = "INSERT INTO gpiygdb.egresos "
						+ "(fecha, monto, moneda, cotizacion, comentario, tipoGasto, fijo, cuotas, idProveedor, idFormaPago) "
						+ "VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(i++, egreso.getFecha());
			stm.setDouble(i++, egreso.getMonto());
			stm.setString(i++, egreso.getMoneda());
			stm.setDouble(i++, egreso.getCotizacion());
			stm.setString(i++, egreso.getComentario());
			stm.setString(i++, egreso.getTipoConsumo());
			stm.setInt(i++, egreso.getGastoFijo());
			stm.setInt(i++, egreso.getCuotas());
			stm.setInt(i++, egreso.getProveedor().getId());
			stm.setInt(i++, egreso.getFormaPago().getId());
			stm.executeUpdate();
			cmdStm = "SELECT id FROM gpiygdb.egresos ORDER BY id DESC LIMIT 1";
			ResultSet rs = stm.executeQuery(cmdStm);
			
			if(rs.next())
				egreso.setId(rs.getInt(1));
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
		int i = 1;
		String cmdStm = "UPDATE gpiygdb.egresos SET fecha = ?, monto = ROUND(?, 2), moneda = ?, cotizacion = ?, "
						+ "comentario = ?, tipoGasto = ?, fijo = ?, cuotas = ?, idProveedor = ?, idFormaPago = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(i++, egreso.getFecha());
			stm.setDouble(i++, egreso.getMonto());
			stm.setString(i++, egreso.getMoneda());
			stm.setDouble(i++, egreso.getCotizacion());
			stm.setString(i++, egreso.getComentario());
			stm.setString(i++, egreso.getTipoConsumo());
			stm.setInt(i++, egreso.getGastoFijo());
			stm.setInt(i++, egreso.getCuotas());
			stm.setInt(i++, egreso.getProveedor().getId());
			stm.setInt(i++, egreso.getFormaPago().getId());
			stm.setInt(i++, egreso.getId());
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
	
	@Override
	public String [][] getResumen(String año, int mes, String destinos[]){
		
		String respuesta[][] = new String[destinos.length + 1][2];
		String cmdStm = "SELECT SUM(monto) FROM gpiygdb.egresos WHERE (YEAR(fecha) = ? AND MONTH(fecha) = ? AND tipoGasto = ?)";
		DecimalFormat formatoResultado = new DecimalFormat("###,###,##0.00");
		double suma = 0;
		
		try {
			
			this.conectar();
			ResultSet rs = null;
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			stm.setInt(2, mes);
			int i = 0;

			for(String dest: destinos) {
				
				stm.setString(3, dest);
				respuesta[i][0] = dest;
				rs = stm.executeQuery();
				
				if(rs.next()) {
					
					respuesta[i][1] = formatoResultado.format(rs.getDouble(1));
					suma += rs.getDouble(1);
				}
				i++;	
			}
			respuesta[i][1] = formatoResultado.format(suma);
		} catch (Exception e) {

			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("EgresosMySQL, getResumen");
		} finally {

			this.cerrar();
		}
		return respuesta;
	}
}