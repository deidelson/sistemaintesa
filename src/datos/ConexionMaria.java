package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMaria {
	private static final String url="jdbc:mariadb://intesa.ddns.net/intesadb";
	private static final String urlTest="jdbc:mariadb://intesa.ddns.net/intesadbTest";
	private static final String user="usuario";
	private static final String pass="talveztal";
	
	public Connection getConexion() throws SQLException {
		Connection ret=null;
		ret=DriverManager.getConnection(url, user, pass);
		return ret;
	}
	
	public Connection getConexionTest() throws SQLException {
		Connection ret=null;
		ret=DriverManager.getConnection(urlTest, user, pass);
		return ret;
	}
	
	public static void main(String[] args) {
		try {
			new ConexionMaria().getConexionTest();
			System.out.println("[INFO] La conexion fue exitosa");
		} catch (Exception e) {
			System.err.println("Fallo en la conexion");
			e.printStackTrace();
		}
	}

}
