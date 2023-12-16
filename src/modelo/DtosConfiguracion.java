package modelo;

public class DtosConfiguracion {

	private static String directorio = "C:\\LECSys 2.0\\";
	private static String servidor = "localhost";
	private static String usuarioBD = "aplicacion";
	private static String passDB = "proyectoLEC";

	public static String getDirectorio() {
		
		return directorio;
	}

	public static String getServidor() {
		
		return servidor;
	}

	public static String getUsuarioBD() {
		
		return usuarioBD;
	}

	public static String getPassDB() {
		
		return passDB;
	}
}
