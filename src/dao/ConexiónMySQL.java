package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import modelo.DtosConfiguracion;

public class ConexiónMySQL {
	
	protected Connection conexion;

	protected Connection conectar() {
		
		String url= "jdbc:mysql://" + DtosConfiguracion.getServidor() + ":3306/gpiygdb?serverTimezone=UTC";
	
		try {
			
			conexion = DriverManager.getConnection(url, DtosConfiguracion.getUsuarioBD(), DtosConfiguracion.getPassDB());
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			
			System.err.println("Error al acceder a la base de datos.");
			System.err.println(url);
			System.err.println(e.getMessage());
		}
		return conexion;
	}
	
	protected void cerrar() {
		
		try {
			
			if(conexion != null && !conexion.isClosed()) {
				
				conexion.close();
			}
		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
	}
}