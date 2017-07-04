package negocio;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datos.ConexionAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAORubroTest
{
	DAORubroImpl daoClientes;
	ObservableList<Rubro>clientes;
	
	@Before
	public void inicializar()
	{
		daoClientes=new DAORubroImpl(new ConexionAccess("jdbc:ucanaccess://bdMock.accdb").getConnection(), "ClientesMock");
		clientes=FXCollections.observableArrayList();
	}

	@Test
	public void testAgregar()//tambien testeamos getRubros
	{
		daoClientes.agregarClienteMock(new Rubro(0, "Danilo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(1, "Erika", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(2, "Danilo2", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		
		clientes=(ObservableList<Rubro>) daoClientes.getRubros();
		
		assertTrue(clientes.size()==3);
		assertTrue(clientes.get(0).getNombre().equals("Danilo"));
		assertTrue(clientes.get(1).getNombre().equals("Erika"));
		assertTrue(clientes.get(2).getNombre().equals("Danilo2"));
		assertTrue(clientes.get(0).getDireccion().equals(clientes.get(1).getDireccion()));
		
		Rubro r= daoClientes.getRubro(0);
		assertEquals(clientes.get(0), r);
		
		daoClientes.borrarRubro(0);
		daoClientes.borrarRubro(1);
		daoClientes.borrarRubro(2);
		
		clientes=(ObservableList<Rubro>) daoClientes.getRubros();
		assertTrue(clientes.size()==0);
	}
	
	@Test
	public void testEditar()
	{
		daoClientes.agregarClienteMock(new Rubro(0, "Danilo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(1, "Erika", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(2, "Danilo2", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		
		daoClientes.editarRubro(0, new Rubro("Danilo Eidelson", "44556583", "4564653", "deidelson@g", "danilo.eide", "rio"));
		
		clientes=(ObservableList<Rubro>) daoClientes.getRubros();
		
		assertTrue(clientes.size()==3);
		System.out.println(clientes.get(0).getNombre()+" "+clientes.get(0).getFacebook()+" "+clientes.get(0).getTelefono1());
		assertTrue(clientes.get(0).equals(new Rubro(0,"Danilo Eidelson", "44556583", "4564653", "deidelson@g", "danilo.eide", "rio")));
		
		
		daoClientes.borrarRubro(0);
		daoClientes.borrarRubro(1);
		daoClientes.borrarRubro(2);
		
		clientes=(ObservableList<Rubro>) daoClientes.getRubros();
		assertTrue(clientes.size()==0);
	}
	
	@Test
	public void getRubrosPorNombre()
	{
		daoClientes.agregarClienteMock(new Rubro(0, "Danilo Eidelson", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(1, "Erika Eidelson", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(2, "Danilo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		
		clientes=(ObservableList<Rubro>) daoClientes.getRubrosPorNombre("Eidel");
		
		assertTrue(clientes.size()==2);
		assertEquals(clientes.get(0), new Rubro(0, "Danilo Eidelson", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		assertEquals(clientes.get(1), new Rubro(1, "Erika Eidelson", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
	}
	
	@After
	public void borrarTodo()
	{
		try
		{
			Statement	st =  new ConexionAccess("jdbc:ucanaccess://bdMock.accdb").getConnection().createStatement();
			st.executeUpdate("DELETE from ClientesMock");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

}
