package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.Inmobiliario;
import modelo.Proveedor;

public class MercadoInmobiliarioMySQL extends ConexiónMySQL implements MercadoInmobiliarioDAO {
	
	@Override
	public String [] getAñosCargados() {

		String respuesta[] = null;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT YEAR(fecha) FROM gpiygdb.operaciones WHERE idInmobiliario IS NOT NULL "
											+ "GROUP BY YEAR(fecha) ORDER BY YEAR(fecha) DESC LIMIT 20");
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
			System.err.println("MercadoInmobiliarioMySQL, getAñosCargados");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public Inmobiliario [] getListado(String filtro) {
		
		Inmobiliario respuesta[] = null;
		String cmdStm = "SELECT inmobiliario.id, descripcion, lugar, idOperador, nombre, direccion, cuit, comentario, mercado "
						+ "FROM gpiygdb.inmobiliario JOIN gpiygdb.proveedores ON proveedores.id = idOperador "
						+ "WHERE nombre LIKE ? OR descripcion LIKE ? OR lugar LIKE ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, "%" + filtro + "%");
			stm.setString(2, "%" + filtro + "%");
			stm.setString(3, "%" + filtro + "%");
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Inmobiliario[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				respuesta[i] = new Inmobiliario();
				respuesta[i].setId(rs.getInt("inmobiliario.id"));
				respuesta[i].setDescripcion(rs.getString("descripcion"));
				respuesta[i].setLugar(rs.getString("lugar"));
				respuesta[i].setOperador(new Proveedor());
				respuesta[i].getOperador().setId(rs.getInt("idOperador"));
				respuesta[i].getOperador().setNombre(rs.getString("nombre"));
				respuesta[i].getOperador().setDireccion(rs.getString("direccion"));
				respuesta[i].getOperador().setCuit(rs.getString("cuit"));
				respuesta[i].getOperador().setComentario(rs.getString("comentario"));
				respuesta[i].getOperador().setMercado(rs.getString("mercado"));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("MercadoInmobiliarioMySQL, getListado");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
		
		

}