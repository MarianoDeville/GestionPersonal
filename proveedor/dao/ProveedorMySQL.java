package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Proveedor;

public class ProveedorMySQL extends ConexiónMySQL implements ProveedorDAO {
	
	@Override
	public Proveedor [] getListado(String filtro, String egresoIngreso) {
		
		Proveedor respuesta[] = null;
		String cmdStm = "SELECT id, nombre, direccion, cuit, comentario, mercado FROM gpiygdb.proveedores WHERE (nombre LIKE ? AND ";

		if(egresoIngreso.equals("M"))
			cmdStm += "(egresoIngreso = ?)) ";
		else
			cmdStm += "(egresoIngreso = ? OR egresoIngreso = 'A'  OR egresoIngreso = 'M')) ";
		cmdStm += "ORDER BY nombre";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, "%" + filtro + "%");
			stm.setString(2, egresoIngreso);
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
				respuesta[i].setComentario(rs.getString(5));
				respuesta[i].setMercado(rs.getString(6));
				i++;
			}
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			System.err.println("ProveedoresMySQL, getListado");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}