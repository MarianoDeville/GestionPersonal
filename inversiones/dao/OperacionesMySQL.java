package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Cripto;
import modelo.Fiat;
import modelo.Inmobiliario;
import modelo.Instrumento;
import modelo.Operacion;
import modelo.Proveedor;
import modelo.Valores;

public class OperacionesMySQL extends ConexiónMySQL implements OperacionesDAO {
	
	@Override
	public boolean update(Operacion operacion) {
		
		boolean bandera = true;
		String campo = "";
		String interrogantes = "";
		int idCampo = 0;

		if(operacion.getIdIngreso() != 0) {
			
			campo = ", idIngreso";
			interrogantes = ", ?";
		}
		
		if(operacion.getIdEgreso() != 0) {
			
			campo += ", idEgreso";
			interrogantes += ", ?";
		}
		
		if(operacion.getIdValores() != 0) {
			
			campo += ", idInversion";
			idCampo = operacion.getIdValores();
		} else if(operacion.getIdCripto() != 0) {
			
			campo += ", idCripto";
			idCampo = operacion.getIdCripto();
		} else if(operacion.getIdFiat() != 0) {
			
			campo += ", idFiat";
			idCampo = operacion.getIdFiat();
		}else if(operacion.getIdInmobiliario() != 0) {
			
			campo += ", idInmobiliario";
			idCampo = operacion.getIdInmobiliario();
		}
		interrogantes += ", ?)";
		String cmdStm = "INSERT INTO gpiygdb.operaciones ("
						+ "fecha, operacion, cant, precio, comision, comentario" + campo
						+ ") VALUES (?, ?, ?, ?, ?, ?" + interrogantes;

		try {
			
			int i = 1;
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm);
			stm.setString(i++, operacion.getFecha());
			stm.setString(i++, operacion.getOperacion());
			stm.setDouble(i++, operacion.getCant());
			stm.setDouble(i++, operacion.getPrecio());
			stm.setDouble(i++, operacion.getComision());
			stm.setString(i++, operacion.getComentario());
			
			if(operacion.getIdIngreso() != 0)
				stm.setInt(i++, operacion.getIdIngreso());
			
			if(operacion.getIdEgreso() != 0)
				stm.setInt(i++, operacion.getIdEgreso());
			stm.setInt(i++, idCampo);
			stm.executeUpdate();
		} catch (Exception e) {
		
			bandera = false;
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, update");
		} finally {
		
			this.cerrar();
		}
		return bandera;
	}

	@Override
	public Operacion [] getListado(Valores valor) {
		
		Operacion operaciones[] = null;
		String cmdStm = "SELECT id, DATE_FORMAT(fecha, '%d/%m/%Y'), operacion, cant, precio, comision, comentario "
						+ "FROM gpiygdb.operaciones WHERE idInversion = ? ORDER BY fecha DESC";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, valor.getId());
			ResultSet rs = stm.executeQuery();
			rs.last();
			operaciones = new Operacion[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {

				operaciones[i] = new Operacion();
				operaciones[i].setId(rs.getInt(1));
				operaciones[i].setFecha(rs.getString(2));
				operaciones[i].setOperacion(rs.getString(3));
				operaciones[i].setCant(rs.getDouble(4));
				operaciones[i].setPrecio(rs.getDouble(5));
				operaciones[i].setComision(rs.getDouble(6));
				operaciones[i].setComentario(rs.getString(7));
				i++;
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("MercadoValoresMySQL, getListado");
		} finally {
		
			this.cerrar();
		}
		return operaciones;
	}
	
	@Override
	public Fiat [] getListadoFiat(String año, int mes) {
	
		Fiat monedas[] = null;
		String fechas[] = null;
		String cmdStm = "SELECT idMoneda, moneda.nombre, SUM(operaciones.cant), proveedores.nombre, idCustodia "
						+ "FROM gpiygdb.operaciones "
						+ "JOIN gpiygdb.fiat ON idFiat = fiat.id "
						+ "JOIN gpiygdb.proveedores ON idCustodia = proveedores.id "
						+ "JOIN gpiygdb.moneda ON idMoneda = moneda.id "
						+ "WHERE YEAR(fecha) <= ? " + (mes != 0? "AND MONTH(fecha) <= ? ":"")
						+ "GROUP BY idCustodia, idMoneda "
						+ "HAVING SUM(operaciones.cant) > 0 "
						+ "ORDER BY moneda.nombre, proveedores.nombre";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			
			if(mes != 0)
				stm.setInt(2, mes);
			ResultSet rs = stm.executeQuery();
			rs.last();
			monedas = new Fiat[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				monedas[i] = new Fiat();
				monedas[i].getMoneda().setId(rs.getInt("idMoneda"));
				monedas[i].getMoneda().setNombre(rs.getString("moneda.nombre"));
				monedas[i].setCant(rs.getDouble("SUM(operaciones.cant)"));
				monedas[i].setCustodia(new Proveedor());
				monedas[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));
				monedas[i].getCustodia().setId(rs.getInt("idCustodia"));
				i++;
			}
			cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') "
					+ "FROM gpiygdb.operaciones "
					+ "WHERE idFiat IS NOT NULL AND YEAR(fecha) = ? " + (mes != 0? "AND MONTH(fecha) = ? ":"")
					+ "GROUP BY fecha ORDER BY fecha";
			stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			
			if(mes != 0)
				stm.setInt(2, mes);
			rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			i = 0;
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}

			for(i = 0; i < monedas.length; i++) {

				monedas[i].setOperaciones(new Operacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					monedas[i].getOperaciones()[e] = new Operacion();
					
					cmdStm = "SELECT operaciones.id, operacion, SUM(operaciones.cant), precio, comision, comentario "
							+ "FROM gpiygdb.operaciones "
							+ "JOIN gpiygdb.fiat ON idFiat = fiat.id "
							+ "JOIN gpiygdb.moneda ON idMoneda = moneda.id "
							+ "WHERE (idCustodia = ? AND idMoneda = ? AND DATE_FORMAT(fecha, '%d/%m/%Y') = ?)";
					stm = conexion.prepareStatement(cmdStm);
					stm.setInt(1, monedas[i].getCustodia().getId());
					stm.setInt(2, monedas[i].getMoneda().getId());
					stm.setString(3, fechas[e]);
					rs = stm.executeQuery();
					
					if(rs.next()) {
						
						monedas[i].getOperaciones()[e] = new Operacion();
						monedas[i].getOperaciones()[e].setId(rs.getInt(1));
						monedas[i].getOperaciones()[e].setOperacion(rs.getString(2));
						monedas[i].getOperaciones()[e].setCant(rs.getDouble(3));
						monedas[i].getOperaciones()[e].setPrecio(rs.getDouble(4));
						monedas[i].getOperaciones()[e].setComision(rs.getDouble(5));
						monedas[i].getOperaciones()[e].setComentario(rs.getString(6));
					}
					monedas[i].getOperaciones()[e].setFecha(fechas[e]);
				}
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, getListadoFiat()");
		} finally {
		
			this.cerrar();
		}
		return monedas;	
	}
	
	@Override
	public Cripto [] getListadoCripto(String año, int mes) {
	
		Cripto monedas[] = null;
		String fechas[] = null;
		String cmdStm = "SELECT idCripto, criptoMoneda.nombre, simbolo, estable, SUM(operaciones.cant), proveedores.nombre, idCustodia "
						+ "FROM gpiygdb.operaciones "
						+ "JOIN gpiygdb.cripto ON idCripto = cripto.id "
						+ "JOIN gpiygdb.proveedores ON idCustodia = proveedores.id "
						+ "JOIN  gpiygdb.criptoMoneda ON idCriptoMoneda = criptoMoneda.id "
						+ "WHERE YEAR(fecha) <= ? " + (mes != 0? "AND MONTH(fecha) <= ? ":"")
						+ "GROUP BY idCustodia, idCripto "
						+ "HAVING SUM(operaciones.cant) > 0 "
						+ "ORDER BY idCriptoMoneda, idCustodia";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			
			if(mes != 0)
				stm.setInt(2, mes);
			ResultSet rs = stm.executeQuery();
			rs.last();
			monedas = new Cripto[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				monedas[i] = new Cripto();
				monedas[i].setId(rs.getInt("idCripto"));
				monedas[i].getMoneda().setNombre(rs.getString("criptoMoneda.nombre"));
				monedas[i].getMoneda().setSimbolo(rs.getString("simbolo"));
				monedas[i].getMoneda().setEstable(rs.getInt("estable"));
				monedas[i].setCant(rs.getDouble("SUM(operaciones.cant)"));
				monedas[i].setCustodia(new Proveedor());
				monedas[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));
				monedas[i].getCustodia().setId(rs.getInt("idCustodia"));
				i++;
			}
			cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') "
					+ "FROM gpiygdb.operaciones "
					+ "WHERE idCripto IS NOT NULL AND YEAR(fecha) = ? " + (mes != 0? "AND MONTH(fecha) = ? ":"")
					+ "GROUP BY fecha ORDER BY fecha";
			stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			
			if(mes != 0)
				stm.setInt(2, mes);
			rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			i = 0;
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}

			for(i = 0; i < monedas.length; i++) {

				monedas[i].setOperaciones(new Operacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					monedas[i].getOperaciones()[e] = new Operacion();
					cmdStm = "SELECT operaciones.id, operacion, SUM(operaciones.cant), precio, comision, comentario "
							+ "FROM gpiygdb.operaciones "
							+ "JOIN gpiygdb.cripto ON idCripto = cripto.id "
							+ "WHERE (idCripto = ? AND DATE_FORMAT(fecha, '%d/%m/%Y') = ?) GROUP BY operaciones.id";
					stm = conexion.prepareStatement(cmdStm);
					stm.setInt(1, monedas[i].getId());
					stm.setString(2, fechas[e]);
					rs = stm.executeQuery();
		
					if(rs.next()) {
						
						monedas[i].getOperaciones()[e] = new Operacion();
						monedas[i].getOperaciones()[e].setId(rs.getInt(1));
						monedas[i].getOperaciones()[e].setOperacion(rs.getString(2));
						monedas[i].getOperaciones()[e].setCant(rs.getDouble(3));
						monedas[i].getOperaciones()[e].setPrecio(rs.getDouble(4));
						monedas[i].getOperaciones()[e].setComision(rs.getDouble(5));
						monedas[i].getOperaciones()[e].setComentario(rs.getString(6));
					}
					monedas[i].getOperaciones()[e].setFecha(fechas[e]);
				}
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, getListadoCripto()");
		} finally {
		
			this.cerrar();
		}
		return monedas;	
	}
	
	@Override
	public Valores [] getListadoValores(String año, int mes, boolean existente) {
	
		Valores valores[] = null;
		String cmdStm = "SELECT idInversion, valores.nombre, valores.cant, idTipo, instrumento.nombre, descripcion, idCustodia, proveedores.nombre, mercado "
						+ "FROM gpiygdb.operaciones "
						+ "JOIN gpiygdb.valores ON idInversion = valores.id "
						+ "JOIN gpiygdb.proveedores ON idCustodia = proveedores.id "
						+ "JOIN gpiygdb.instrumento ON idTipo = instrumento.id "
						+ "WHERE " + (mes == 0? "YEAR(fecha) <= ? ":"DATE_FORMAT(fecha, '%Y/%m') <= ? ")
						+ "GROUP BY idCustodia, valores.nombre, idInversion "
						+ (existente? "HAVING valores.cant > 0 ": "") 
						+ "ORDER BY proveedores.nombre, valores.nombre";
	
		try {
	
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, mes == 0? año: año + "/" + mes);
			ResultSet rs = stm.executeQuery();
			rs.last();
			valores = new Valores[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
	
			while(rs.next()) {
				
				valores[i] = new Valores();
				valores[i].setId(rs.getInt("idInversion"));
				valores[i].setNombre(rs.getString("valores.nombre"));
				valores[i].setCant(rs.getDouble("valores.cant"));
				valores[i].setInstrumento(new Instrumento());
				valores[i].getInstrumento().setId(rs.getInt("idTipo"));
				valores[i].getInstrumento().setNombre(rs.getString("instrumento.nombre"));
				valores[i].getInstrumento().setDescripcion(rs.getString("descripcion"));
				valores[i].setCustodia(new Proveedor());
				valores[i].getCustodia().setId(rs.getInt("idCustodia"));
				valores[i].getCustodia().setNombre(rs.getString("proveedores.nombre"));
				valores[i].getCustodia().setMercado(rs.getString("mercado"));
				i++;
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, getListadoValores()");
		} finally {
		
			this.cerrar();
		}
		return valores;	
	}
	
	@Override
	public Inmobiliario [] getListadoPropiedades(String año, boolean activo) {
		
		Inmobiliario propiedades[] = null;
		String fechas[] = null;
		String cmdStm = "SELECT inmobiliario.id, descripcion, lugar, proveedores.id, nombre, direccion, cuit, comentario "
						+ "FROM gpiygdb.inmobiliario "
						+ "JOIN gpiygdb.proveedores ON idOperador = proveedores.id "
						+ "WHERE activo = " + (activo? "1 ": "0 ")
						+ "ORDER BY proveedores.nombre";
		
		try {
			
			this.conectar();
			PreparedStatement stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery();
			rs.last();
			propiedades = new Inmobiliario[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				propiedades[i] = new Inmobiliario();
				propiedades[i].setId(rs.getInt("inmobiliario.id"));
				propiedades[i].setDescripcion(rs.getString("descripcion"));
				propiedades[i].setLugar(rs.getString("lugar"));
				propiedades[i].setOperador(new Proveedor());
				propiedades[i].getOperador().setId(rs.getInt("proveedores.id"));
				propiedades[i].getOperador().setNombre(rs.getString("nombre"));
				propiedades[i].getOperador().setDireccion(rs.getString("direccion"));
				propiedades[i].getOperador().setCuit(rs.getString("cuit"));
				propiedades[i].getOperador().setComentario(rs.getString("comentario"));
				i++;
			}
			cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y') "
					+ "FROM gpiygdb.operaciones "
					+ "WHERE idInmobiliario IS NOT NULL AND YEAR(fecha) = ? "
					+ "GROUP BY fecha ORDER BY fecha";
			stm = conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			rs = stm.executeQuery();
			rs.last();
			fechas = new String[rs.getRow()];
			rs.beforeFirst();
			i = 0;
			
			while(rs.next()) {
				
				fechas[i] = rs.getString(1);
				i++;
			}

			for(i = 0; i < propiedades.length; i++) {

				propiedades[i].setOperaciones(new Operacion[fechas.length]);
				
				for(int e = 0; e < fechas.length; e++) {
					
					propiedades[i].getOperaciones()[e] = new Operacion();
					cmdStm = "SELECT id, operacion, cant, precio, comision, comentario, idEgreso "
							+ "FROM gpiygdb.operaciones "
							+ "WHERE (idInmobiliario = ? AND DATE_FORMAT(fecha, '%d/%m/%Y') = ?)";
					stm = conexion.prepareStatement(cmdStm);
					stm.setInt(1, propiedades[i].getId());
					stm.setString(2, fechas[e]);
					rs = stm.executeQuery();
					
					if(rs.next()) {
						
						propiedades[i].getOperaciones()[e] = new Operacion();
						propiedades[i].getOperaciones()[e].setId(rs.getInt("id"));
						propiedades[i].getOperaciones()[e].setOperacion(rs.getString("operacion"));
						propiedades[i].getOperaciones()[e].setCant(rs.getDouble("cant"));
						propiedades[i].getOperaciones()[e].setPrecio(rs.getDouble("precio"));
						propiedades[i].getOperaciones()[e].setComision(rs.getDouble("comision"));
						propiedades[i].getOperaciones()[e].setIdEgreso(rs.getInt("idEgreso"));
					}
					propiedades[i].getOperaciones()[e].setFecha(fechas[e]);
				}
			}
		} catch (Exception e) {
		
			System.err.println(cmdStm);
			System.err.println(e.getMessage());
			System.err.println("OperacionesMySQL, getListadoPropiedades()");
		} finally {
		
			this.cerrar();
		}
		return propiedades;
	}
}