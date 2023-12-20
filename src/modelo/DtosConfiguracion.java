package modelo;

public class DtosConfiguracion {

	private static String directorio;
	private static String servidor;
	private static String usuarioBD;
	private static String passBD;
	
	public static String getDirectorio() {
		
		return directorio;
	}
	
	public void setDirectorio(String directorio) {
		
		DtosConfiguracion.directorio = directorio;
	}
	
	public static String getServidor() {
		
		return servidor;
	}
	
	public void setServidor(String servidor) {
		
		DtosConfiguracion.servidor = servidor;
	}
	
	public static String getUsuarioBD() {
		
		return usuarioBD;
	}
	
	public void setUsuarioBD(String usuarioBD) {
		
		DtosConfiguracion.usuarioBD = usuarioBD;
	}
	
	public static String getPassBD() {
		
		return passBD;
	}
	
	public void setPassBD(String passDB) {
		
		DtosConfiguracion.passBD = passDB;
	}
}