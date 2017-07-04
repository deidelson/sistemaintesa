package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionAccess implements Conectable
{		
	
	private  String url;
	//"jdbc:ucanaccess://C:\\Users\\Escritorio\\Dropbox\\planillaDeCaja.accdb"
	// "jdbc:ucanaccess://planillaDeCaja.accdb"
	
	public ConexionAccess(String url)
	{
		this.url=url;
	}
	
	@Override
	public Connection getConnection() 
	{
		Connection con=null;
		
		try 
		{
			con=DriverManager.getConnection(url);				
		} 
		catch (SQLException e)
		{
			throw new NullPointerException();
		}
		return con;
	}

}