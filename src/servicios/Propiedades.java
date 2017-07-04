package servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Propiedades {
	private static Properties p= new Properties();
	public static final String PARAM_FILE = "C:\\SistemaIntesa\\intesa.properties";
	
	public static String getPropiedades(String key){
		try {
			File f = new File(PARAM_FILE);
			//if (!f.exists()) setearParametros();
			
			FileInputStream fis = new FileInputStream(PARAM_FILE);
			p.load(fis);
		} catch (Exception e) {
			System.err.println(PARAM_FILE + ":archivo no encontrado.");
		}

		if (p.getProperty(key) == null){
			setearParametros();
		}
		return p.getProperty(key);
	}
	
	public static void setearParametros(){
		p.setProperty("ubicacion.bd", "");		
		p.setProperty("ubicacion.icono", "");
		try {
			p.store(new FileOutputStream(PARAM_FILE), "");
		} catch (Exception e) {
			System.err.println(PARAM_FILE + ": no se pudo escribir en el archivo.");
		}
	}
	


}
