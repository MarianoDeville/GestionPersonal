package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Concepto;
import modelo.Ingreso;
import modelo.Proveedor;
import modelo.Transaccion;

public class IngresosMySQL extends ConexiónMySQL implements IngresosDAO {

	@Override
	public String [] getAñosCargados() {

		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.ingresos GROUP BY YEAR(fecha) ORDER BY fecha DESC LIMIT 10");
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
			System.err.println("IngresosMySQL, getAñosCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public Concepto [] getConcepto() {
	
		Concepto respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, descripcion FROM gpiygdb.concepto");
			rs.last();	
			respuesta = new Concepto[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Concepto();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, getConcepto");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public Ingreso [] getListado(String año, int mes, int idConcepto, int idFormaCobro, String moneda, String filtro) {

		Ingreso respuesta[] = new Ingreso[0];
		String cmdStm = "SELECT ingresos.id, DATE_FORMAT(fecha, '%d/%m/%Y') AS fecha, ROUND(monto, 2) AS monto, moneda, cotizacion, "
							+ "proveedores.nombre, proveedores.id, transaccion.descripcion, concepto.descripcion, ingresos.comentario "
						+ "FROM gpiygdb.ingresos "
						+ "JOIN gpiygdb.proveedores ON proveedores.id = idFuente "
						+ "JOIN gpiygdb.transaccion ON transaccion.id = idFormaCobro "
						+ "JOIN gpiygdb.concepto ON concepto.id = idConcepto "
						+ "WHERE (YEAR(fecha) = ? ";
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		
		if(idConcepto != 0)
			cmdStm += " AND idConcepto = " + idConcepto;
		
		if(idFormaCobro != 0)
			cmdStm += " AND idFormaCobro = " + idFormaCobro;
		
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
		cmdStm += " AND proveedores.nombre LIKE ?) ORDER BY fecha DESC, ingresos.id DESC"; 

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			stm.setString(2, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Ingreso[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while (rs.next()) {
				
				respuesta[i] = new Ingreso();
				respuesta[i].setId(rs.getInt("ingresos.id"));
				respuesta[i].setFecha(rs.getString("fecha"));
				respuesta[i].setMonto(rs.getDouble("monto"));
				respuesta[i].setMoneda(rs.getNString("moneda"));
				respuesta[i].setCotizacion(rs.getFloat("cotizacion"));
				respuesta[i].setComentario(rs.getString("ingresos.comentario"));
				respuesta[i].setFuente(new Proveedor());
				respuesta[i].getFuente().setNombre(rs.getString("proveedores.nombre"));
				respuesta[i].getFuente().setId(rs.getInt("proveedores.id"));
				respuesta[i].setFormaCobro(new Transaccion());
				respuesta[i].getFormaCobro().setDescripcion(rs.getString("transaccion.descripcion"));
				respuesta[i].setConcepto(new Concepto());
				respuesta[i].getConcepto().setDescripcion(rs.getString("concepto.descripcion"));
				i++;
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, getListado");
		} finally {

			this.cerrar();
		}
		return respuesta;
	}
	
 	@Override
	public boolean nuevo(Ingreso ingreso) {
		
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.ingresos "
						+ "(fecha, monto, moneda, cotizacion, comentario, idFuente, idFormaCobro, idConcepto) "
						+ "VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, ingreso.getFecha());
			stm.setDouble(2, ingreso.getMonto());
			stm.setString(3, ingreso.getMoneda());
			stm.setDouble(4, ingreso.getCotizacion());
			stm.setString(5, ingreso.getComentario());
			stm.setInt(6, ingreso.getFuente().getId());
			stm.setInt(7, ingreso.getFormaCobro().getId());
			stm.setInt(8, ingreso.getConcepto().getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, nuevo");
		} finally {

			this.cerrar();
		}
		return bandera;
	}

	@Override
	public boolean update(Ingreso ingreso) {
		
		boolean bandera = true;
		String cmdStm = "UPDATE gpiygdb.ingresos "
						+ "SET fecha = ?, monto = ROUND(?, 2), moneda = ?, cotizacion = ?, comentario = ?, "
						+ "idFuente = ?, idFormaCobro = ?, idConcepto = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, ingreso.getFecha());
			stm.setDouble(2, ingreso.getMonto());
			stm.setString(3, ingreso.getMoneda());
			stm.setDouble(4, ingreso.getCotizacion());
			stm.setString(5, ingreso.getComentario());
			stm.setInt(6, ingreso.getFuente().getId());
			stm.setInt(7, ingreso.getFormaCobro().getId());
			stm.setInt(8, ingreso.getConcepto().getId());
			stm.setInt(9, ingreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, update");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public boolean delete(Ingreso ingreso) {
		
		boolean bandera = true;

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement("DELETE FROM gpiygdb.ingresos WHERE id = ?");
			stm.setInt(1, ingreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, delete");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
 }