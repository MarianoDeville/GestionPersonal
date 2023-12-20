package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.Cobro;
import modelo.Concepto;
import modelo.Fuente;
import modelo.Ingreso;

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
	public Cobro [] getMetodosCobros() {
		
		Cobro respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT id, descripcion FROM gpiygdb.formaCobro");
			rs.last();	
			respuesta = new Cobro[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Cobro();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setDescripcion(rs.getString(2));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, getMetodosCobros");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Ingreso [] getListadoIngresos(String año, int mes, int idConcepto, int idFormaCobro, String moneda, String filtro) {

		Ingreso respuesta[] = new Ingreso[0];
		String cmdStm = "SELECT ingresos.id, DATE_FORMAT(fecha, '%d/%m/%Y') AS fecha, ROUND(monto, 2) AS monto, moneda, cotizacion, "
							+ "fuente.nombre, fuente.id, formaCobro.descripcion, concepto.descripcion "
						+ "FROM gpiygdb.ingresos "
						+ "JOIN gpiygdb.fuente ON fuente.id = idFuente "
						+ "JOIN gpiygdb.formaCobro ON formaCobro.id = idFormaCobro "
						+ "JOIN gpiygdb.concepto ON concepto.id = idConcepto "
						+ "WHERE (YEAR(fecha) = ?";
		
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
		cmdStm += " AND fuente.nombre LIKE ?) ORDER BY ingresos.id DESC"; 

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
				respuesta[i].setFuente(new Fuente());
				respuesta[i].getFuente().setNombre(rs.getString("fuente.nombre"));
				respuesta[i].getFuente().setId(rs.getInt("fuente.id"));
				respuesta[i].setFormaCobro(new Cobro());
				respuesta[i].getFormaCobro().setDescripcion(rs.getString("formaCobro.descripcion"));
				respuesta[i].setConcepto(new Concepto());
				respuesta[i].getConcepto().setDescripcion(rs.getString("concepto.descripcion"));
				i++;
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, getListadoIngresos");
		} finally {

			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Fuente [] getListaFuentes(String filtro) {
		
		Fuente respuesta[] = null;
		String cmdStm = "SELECT id, nombre, direccion, cuit, comentario FROM gpiygdb.fuente WHERE nombre LIKE ?";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Fuente[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Fuente();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setDireccion(rs.getString(3));
				respuesta[i].setCuit(rs.getString(4));
				respuesta[i].setComentario(rs.getString(5));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, getListaFuentes");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}	
	
 	@Override
	public boolean nuevoIngreso(Ingreso ingreso) {
		
		boolean bandera = true;
		String cmdStm = "INSERT INTO gpiygdb.ingresos "
						+ "(fecha, monto, moneda, cotizacion, idFuente, idFormaCobro, idConcepto) "
						+ "VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, ingreso.getFecha());
			stm.setDouble(2, ingreso.getMonto());
			stm.setString(3, ingreso.getMoneda());
			stm.setDouble(4, ingreso.getCotizacion());
			stm.setInt(5, ingreso.getFuente().getId());
			stm.setInt(6, ingreso.getFormaCobro().getId());
			stm.setInt(7, ingreso.getConcepto().getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, nuevoIngreso");
		} finally {

			this.cerrar();
		}
		return bandera;
	}

	@Override
	public boolean updateIngreso(Ingreso ingreso) {
		
		boolean bandera = true;
		String cmdStm = "UPDATE gpiygdb.ingresos "
						+ "SET fecha = ?, monto = ROUND(?, 2), moneda = ?, cotizacion = ?, "
						+ "idFuente = ?, idFormaCobro = ?, idConcepto = ? WHERE id = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(1, ingreso.getFecha());
			stm.setDouble(2, ingreso.getMonto());
			stm.setString(3, ingreso.getMoneda());
			stm.setDouble(4, ingreso.getCotizacion());
			stm.setInt(5, ingreso.getFuente().getId());
			stm.setInt(6, ingreso.getFormaCobro().getId());
			stm.setInt(7, ingreso.getConcepto().getId());
			stm.setInt(8, ingreso.getId());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			System.err.println(e.getMessage());
			System.err.println("IngresosMySQL, updateIngreso");
		} finally {

			this.cerrar();
		}
		return bandera;
	}
 }