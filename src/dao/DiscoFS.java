package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import modelo.DtosConfiguracion;

public class DiscoFS {

	private static DiscoFS accesoAlDisco;
	
	private DiscoFS() {
		
		getConfiguracion();
	}
	
	public static void recuperarConfiguracion() {
		
		if(accesoAlDisco == null)
			accesoAlDisco = new DiscoFS();
	}
	
	private void getConfiguracion() {

		DtosConfiguracion config = new DtosConfiguracion();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			archivo = new File("GPIyG.dam");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String lineaLeida;
			
			while((lineaLeida=br.readLine())!=null) {
					
				String[] partes = lineaLeida.split("=");
				if(partes.length > 1) {
				
					switch(partes[0]) {
					
					case "DLOG":
						config.setDirectorio(partes[1]);
						break;
						
					case "IPBD":
						config.setServidor(partes[1]);
						break;	
						
					case "USR":
						config.setUsuarioBD(decodifico(partes[1]));
						break;

					case "UPAS":
						config.setPassBD(decodifico(partes[1]));
						break;
					}
				}
			}
		} catch(Exception e){
			
			e.printStackTrace();
		} finally {

			try {
				
				if(fr != null)   
					fr.close();     
			} catch (Exception e2) {
				
				System.err.println(e2.getMessage());
			}
		}
	}
	
	public static void escribirLog(String msg) {
		
		Calendar fechaSistema = new GregorianCalendar();
		String cuerpo = fechaSistema.getTime().toString() + " - " + msg;
		BufferedWriter bw = null;
	    FileWriter fw = null;

	    try {
	    	
	        File archivo = new File(DtosConfiguracion.getDirectorio() + "\\log.txt");

	        if (!archivo.exists())
	        	archivo.createNewFile();
	        fw = new FileWriter(archivo.getAbsoluteFile(), true);		// flag true, indica adjuntar información al archivo.
	        bw = new BufferedWriter(fw);
	        bw.write(cuerpo + "\r\n");
	    } catch (IOException e) {
	    	
	    	System.err.println("No se pudo escribir en el archivo.");
	    } finally {
	    	
	        try {

	            if (bw != null)
	                bw.close();
	            if (fw != null)
	                fw.close();
	        } catch (IOException ex) {
	        	
	            ex.printStackTrace();
	        }
	    }
	}

	private String decodifico(String cuerpo) {

		char temp[] = cuerpo.toCharArray();
		String respuesta = "";
		
		for(int i = 0; i < temp.length; i++) {
			
			respuesta+=(char)((i% 2==0)?(int)temp[i]+3:(int)temp[i]-5);
		}
		return respuesta;
	}
}