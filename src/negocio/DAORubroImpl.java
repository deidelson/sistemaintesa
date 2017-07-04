package negocio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import interfaces.DAORubro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAORubroImpl implements DAORubro
{
	Connection conexion;
	String tablaRubro;
	
	public DAORubroImpl(Connection conexion, String tablaRubro)
	{
		this.conexion=conexion;
		this.tablaRubro=tablaRubro;
	}

	@Override
	public Rubro getRubro(Integer id_c)
	{
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from "+tablaRubro+" where Id_Rubro = "+id_c.toString());
			Integer id=null;
			String nombre=null;
			String telefono1 = null;
			String telefono2 = null;
			String mail = null;
			String facebook = null;
			String direccion = null;
			
			while(rs.next())
			{
				id=Integer.parseInt(rs.getString("Id_Rubro"));
				nombre=rs.getString("Nombre");
				telefono1=rs.getString("Telefono1");
				telefono2=rs.getString("Telefono2");
				mail=rs.getString("Mail");
				facebook=rs.getString("Facebook");
				direccion=rs.getString("Direccion");
			}
		return new Rubro(id, nombre, telefono1, telefono2, mail, facebook, direccion);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void agregarRubro(Rubro c)
	{
		try
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("INSERT INTO "+tablaRubro+" (Nombre, Telefono1, Telefono2,"
					+ " Direccion, Mail, Facebook) VALUES ('"+c.getNombre()+"', '"+c.getTelefono1()+
					"', '"+c.getTelefono2()+"', '"+c.getDireccion()+"', '"+c.getMail()+"', '"+c.getFacebook()+"' )");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}
	
	//para mock
	public void agregarClienteMock(Rubro c)
	{
		try
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("INSERT INTO "+tablaRubro+" (Id_Rubro, Nombre, Telefono1, Telefono2,"
					+ " Direccion, Mail, Facebook) VALUES ("+c.getId()+", '"+c.getNombre()+"', '"+c.getTelefono1()+
					"', '"+c.getTelefono2()+"', '"+c.getDireccion()+"', '"+c.getMail()+"', '"+c.getFacebook()+"' )");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public void borrarRubro(Integer id)
	{
		try 
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("DELETE from "+tablaRubro+" where Id_Rubro="+id.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public void editarRubro(Integer id, Rubro r)
	{
		try 
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("UPDATE "+tablaRubro+" "
					+"SET "
					+"Nombre = '"+r.getNombre()+"', "
					+"Telefono1 = '"+r.getTelefono1()+"', "
					+"Telefono2 = '"+r.getTelefono2()+"', "
					+"Mail = '"+r.getMail()+"', "
					+"Facebook = '"+r.getFacebook()+"', "
					+"Direccion = '"+r.getDireccion()+"' "
					+"WHERE Id_Rubro  = "+id.toString());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public List<Rubro> getRubros()
	{
		try 
		{
			ObservableList<Rubro>clientes=FXCollections.observableArrayList();
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from "+tablaRubro);
			Integer id=null;
			String nombre=null;
			String telefono1 = null;
			String telefono2 = null;
			String mail = null;
			String facebook = null;
			String direccion = null;
			
			while(rs.next())
			{
				id=Integer.parseInt(rs.getString("Id_Rubro"));
				nombre=rs.getString("Nombre");
				telefono1=rs.getString("Telefono1");
				telefono2=rs.getString("Telefono2");
				mail=rs.getString("Mail");
				facebook=rs.getString("Facebook");
				direccion=rs.getString("Direccion");
				
				clientes.add(new Rubro(id, nombre, telefono1, telefono2, mail, facebook, direccion));
			}
			return clientes;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Rubro> getRubrosPorNombre(String nom)
	{
		try 
		{
			ObservableList<Rubro>clientes=FXCollections.observableArrayList();
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from "+tablaRubro+" WHERE Nombre LIKE '%"+nom+"%'");
			Integer id=null;
			String nombre=null;
			String telefono1 = null;
			String telefono2 = null;
			String mail = null;
			String facebook = null;
			String direccion = null;
			
			while(rs.next())
			{
				id=Integer.parseInt(rs.getString("Id_Rubro"));
				nombre=rs.getString("Nombre");
				telefono1=rs.getString("Telefono1");
				telefono2=rs.getString("Telefono2");
				mail=rs.getString("Mail");
				facebook=rs.getString("Facebook");
				direccion=rs.getString("Direccion");
				
				clientes.add(new Rubro(id, nombre, telefono1, telefono2, mail, facebook, direccion));
			}
			return clientes;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	
}
