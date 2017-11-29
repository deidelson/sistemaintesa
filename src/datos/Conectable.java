package datos;

import java.sql.Connection;
import java.sql.SQLException;

public interface Conectable 
{
	public Connection getConnection() throws SQLException;
}
