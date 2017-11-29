package negocio;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datos.ConexionAccess;
import datos.ConexionMaria;
import interfaces.DAORubro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOMovimientoTest {
	DAOMovimientoImpl daoMov;
	ObservableList<Movimiento>resultado;
	
	@Before
	public void arrancarConexion() throws SQLException
	{
		daoMov=new DAOMovimientoImpl(new ConexionMaria().getConexionTest(),"Ingresos","ClientesMock");
		resultado=FXCollections.observableArrayList();
	}

	@Test
	public void agregar()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-09 00:00:00.000000", 1, "pago", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-09 00:00:00.000000", 2, "sarasa", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 2, "sarasa", new BigDecimal("100.24")));
		
		Movimiento i1=daoMov.obtenerMovimiento(1);
		Movimiento i2=daoMov.obtenerMovimiento(2);
	
		System.out.println(i1.getId());
		assertTrue(i1.getId().equals(new Integer(1)));
  		assertTrue(i1.getId_Rubro().equals(1));
		assertTrue(i1.getDescripcion().equals("pago"));
		assertTrue(i1.getMonto().equals(new BigDecimal("100.00")));
		
		assertTrue(i2.getId().equals(new Integer(2)));
		assertTrue(i2.getFecha().equals("2016-12-09"));
		assertTrue(i2.getId_Rubro().equals(2));
		assertTrue(i2.getDescripcion().equals("sarasa"));
		assertTrue(i2.getMonto().equals(new BigDecimal("100.24")));
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		
		assertTrue(daoMov.getMovimientos().size()==0);
	}

	@Test 
	public void getIngresos()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-09 00:00:00.000000", 1, "pago", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-09 00:00:00.000000", 2, "sarasa", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "sarasa", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>) daoMov.getMovimientos();
		
		assertTrue(resultado.contains(new Movimiento(1 ,"2016-12-09", 1, "pago", new BigDecimal("100.00"), "Arturo")));
		assertTrue(resultado.contains(new Movimiento(2 ,"2016-12-09", 2, "sarasa", new BigDecimal("100.24"), "Roman")));
		assertTrue(resultado.contains(new Movimiento(3 ,"2016-12-09", 3, "sarasa", new BigDecimal("100.24"), "Marcelo")));
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
	}
	
	@Test
	public void getIngresosSuma()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-09 00:00:00.000000", 1, "pago", new BigDecimal("100.00").setScale(2)));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-09 00:00:00.000000", 2, "sarasa", new BigDecimal("100.24").setScale(2)));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "sarasa", new BigDecimal("100.24").setScale(2)));
		
		BigDecimal comp=new BigDecimal("300.48");
		comp.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal total=daoMov.getMovimientosSuma();
		assertEquals(total, comp);
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
	}
	
	@Test 
	public void getIngresosEntre()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "no", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 2, "si", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "no", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "si", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>) daoMov.getMovimientosEntre("2016-12-10", "2016-12-11");
		
		assertTrue(resultado.size()==2);
		assertTrue(resultado.get(0).getDescripcion().equals("si"));
		assertTrue(resultado.get(0).getId().equals(2));
		assertTrue(resultado.get(0).getNombreRubro().equals("Roman"));
		assertTrue(resultado.get(1).getDescripcion().equals("si"));
		assertTrue(resultado.get(1).getId().equals(4));
		assertTrue(resultado.get(1).getNombreRubro().equals("Rodrigo"));
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
	}
	
	@Test 
	public void getIngresosEntreSuma()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "no", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 2, "si", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "no", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "si", new BigDecimal("100.24")));
		
		BigDecimal suma= daoMov.getMovimientosEntreSuma("2016-12-10", "2016-12-11");
		
		assertEquals(new BigDecimal("200.48").setScale(2), suma);
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
	}

	@Test
	public void getIngresosPorDescripcion()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "sidasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 2, "sino", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "sinodasdas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "sidsadsano", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-11 00:00:00.000000", 6, "snso", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>) daoMov.getMovimientosDescripcion("no");
		
		
		assertTrue(resultado.size()==4);
		assertTrue(resultado.get(0).getDescripcion().equals("sidasdnodas"));
		assertTrue(resultado.get(0).getId().equals(1));
		assertTrue(resultado.get(0).getNombreRubro().equals("Arturo"));
		assertTrue(resultado.get(1).getDescripcion().equals("sino"));
		assertTrue(resultado.get(1).getId().equals(2));
		assertTrue(resultado.get(1).getNombreRubro().equals("Roman"));
		assertTrue(resultado.get(2).getDescripcion().equals("sinodasdas"));
		assertTrue(resultado.get(2).getId().equals(3));
		assertTrue(resultado.get(2).getNombreRubro().equals("Marcelo"));
		assertTrue(resultado.get(3).getDescripcion().equals("sidsadsano"));
		assertTrue(resultado.get(3).getId().equals(4));
		assertTrue(resultado.get(3).getNombreRubro().equals("Rodrigo"));
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
		daoMov.eliminarMovimiento(5);
		daoMov.eliminarMovimiento(6);
	}
	
	@Test
	public void getIngresosPorDescripcionSuma()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "sidasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 2, "sino", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "sinodasdas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "sidsadsano", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-11 00:00:00.000000", 6, "snso", new BigDecimal("100.24")));
		
		BigDecimal suma= daoMov.getMovimientosDescripcionSuma("no");
		assertEquals(new BigDecimal("400.72").setScale(2), suma);
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
		daoMov.eliminarMovimiento(5);
		daoMov.eliminarMovimiento(6);
	}
	
	@Test
	public void getIngresosPorId_ClienteJoin()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 4, "primero", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "nodasdas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "segundo", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-11 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>) daoMov.getMovimientosIdRubro(4);
		
		
		assertTrue(resultado.size()==3);
		assertTrue(resultado.get(0).getDescripcion().equals("primero"));
		assertTrue(resultado.get(0).getId().equals(2));
		assertTrue(resultado.get(0).getNombreRubro().equals("Rodrigo"));
		assertTrue(resultado.get(1).getDescripcion().equals("segundo"));
		assertTrue(resultado.get(1).getId().equals(4));
		assertTrue(resultado.get(1).getNombreRubro().equals("Rodrigo"));
		assertTrue(resultado.get(2).getDescripcion().equals("tercero"));
		assertTrue(resultado.get(2).getId().equals(6));
		assertTrue(resultado.get(2).getNombreRubro().equals("Rodrigo"));
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
		daoMov.eliminarMovimiento(5);
		daoMov.eliminarMovimiento(6);
	}
	
	@Test
	public void getIngresosPorId_ClienteSuma()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-10 00:00:00.000000", 4, "primero", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-12 00:00:00.000000", 3, "nodasdas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-11 00:00:00.000000", 4, "segundo", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-11 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		BigDecimal suma=daoMov.getMovimientosIdRubroSuma(4);

		assertEquals(new BigDecimal("300.72").setScale(2), suma);
		
		daoMov.eliminarMovimiento(1);
		daoMov.eliminarMovimiento(2);
		daoMov.eliminarMovimiento(3);
		daoMov.eliminarMovimiento(4);
		daoMov.eliminarMovimiento(5);
		daoMov.eliminarMovimiento(6);
	}
	
	@Test
	public void getMovimientosDescripcionEntre()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-08 00:00:00.000000", 4, "primerosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "nodasidas", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-10 00:00:00.000000", 4, "segundosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsiadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-09 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>)daoMov.getMovimientosDescripcionEntre("si", "2016-12-08", "2016-12-10");
		
		assertTrue(resultado.size()==3);
		assertTrue(resultado.get(0).equals(new Movimiento(2 ,"2016-12-08", 4, "primerosi", new BigDecimal("100.24"),"Rodrigo")));
		assertTrue(resultado.get(1).equals(new Movimiento(3 ,"2016-12-09", 3, "nodasidas", new BigDecimal("100.24"),"Marcelo")));
		assertTrue(resultado.get(2).equals(new Movimiento(4 ,"2016-12-10", 4, "segundosi", new BigDecimal("100.24"),"Rodrigo")));
		
		BigDecimal suma= daoMov.getMovimientosDescripcionEntreSuma("si", "2016-12-08", "2016-12-10");
		assertEquals(suma, new BigDecimal("300.72").setScale(2));
	}
	
	@Test
	public void getMovimientosIdRubroEntre()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-08 00:00:00.000000", 4, "primerosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "nodasidas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-10 00:00:00.000000", 4, "segundosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsiadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-12 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>)daoMov.getMovimientosIdRubroEntre(4, "2016-12-07", "2016-12-11");
	
		assertTrue(resultado.size()==2);
		assertTrue(resultado.get(0).equals(new Movimiento(2 ,"2016-12-08", 4, "primerosi", new BigDecimal("100.24"), "Rodrigo")));
		assertTrue(resultado.get(1).equals(new Movimiento(4 ,"2016-12-10", 4, "segundosi", new BigDecimal("100.24"),"Rodrigo")));
		
		BigDecimal suma = daoMov.getMovimientosIdRubroEntreSuma(4, "2016-12-07", "2016-12-11");
		
		assertTrue(suma.equals(new BigDecimal("200.48").setScale(2)));
	}
	
	@Test
	public void getMovimientosIdRubroDescripcion()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-08 00:00:00.000000", 4, "primerosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "nodasidas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-10 00:00:00.000000", 4, "segundosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsiadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-12 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>)daoMov.getMovimientosIdRubroDescripcion(4, "si");
		
		assertTrue(resultado.get(0).equals(new Movimiento(2 ,"2016-12-08", 4, "primerosi", new BigDecimal("100.24"), "Rodrigo")));
		assertTrue(resultado.get(1).equals(new Movimiento(4 ,"2016-12-10", 4, "segundosi", new BigDecimal("100.24"),"Rodrigo")));
		
		BigDecimal suma=daoMov.getMovimientosIdRubroDescripcionSuma(4, "si");
		assertEquals(suma, new BigDecimal("200.48").setScale(2));
	}
	
	@Test
	public void getMovimientosPorIdRubroDescripcionEntre()
	{
		daoMov.agregarIngresoMock(new Movimiento(1 ,"2016-12-07 00:00:00.000000", 1, "dasdnodas", new BigDecimal("100.00")));
		daoMov.agregarIngresoMock(new Movimiento(2 ,"2016-12-08 00:00:00.000000", 4, "primerosi", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(3 ,"2016-12-09 00:00:00.000000", 3, "nodasidas", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(4 ,"2016-12-10 00:00:00.000000", 4, "segundosi", new BigDecimal("100.24")));//si
		daoMov.agregarIngresoMock(new Movimiento(5 ,"2016-12-11 00:00:00.000000", 5, "dsiadsanso", new BigDecimal("100.24")));
		daoMov.agregarIngresoMock(new Movimiento(6 ,"2016-12-12 00:00:00.000000", 4, "tercero", new BigDecimal("100.24")));
		
		resultado=(ObservableList<Movimiento>)daoMov.getMovimientosIdRubroDescripcionEntre(4, "si", "2016-12-09", "2016-12-11");

		assertTrue(resultado.size()==1);
		assertTrue(resultado.get(0).equals(new Movimiento(4 ,"2016-12-10", 4, "segundosi", new BigDecimal("100.24"),"Rodrigo")));
		
		BigDecimal suma=daoMov.getMovimientosIdRubroDescripcionEntreSuma(4, "si", "2016-12-09", "2016-12-11");
		assertEquals(suma, new BigDecimal("100.24").setScale(2));
	}
	
	@After
	public void borrarTodo()
	{
		try
		{
			Statement	st = new ConexionMaria().getConexionTest().createStatement();
			st.executeUpdate("DELETE from Ingresos");
			st.executeUpdate("DELETE from ClientesMock");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	@Before
	public void borrarTodoAntes()
	{
		try
		{
			Statement	st = new ConexionMaria().getConexionTest().createStatement();
			st.executeUpdate("DELETE from Ingresos");
			st.executeUpdate("DELETE from ClientesMock");
			this.agregarClientes();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	private void agregarClientes() throws SQLException {
		DAORubroImpl daoClientes=new DAORubroImpl(new ConexionMaria().getConexionTest(), "ClientesMock");
		daoClientes.agregarClienteMock(new Rubro(1, "Arturo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(2, "Roman", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(3, "Marcelo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(4, "Rodrigo", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(5, "Dinio", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
		daoClientes.agregarClienteMock(new Rubro(6, "Messi", "4455658", "456465", "deidelson@algo", "danilo.iede", "la pinta"));
	}

}
