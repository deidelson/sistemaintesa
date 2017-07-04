package negocio;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import interfaces.DAOMovimiento;
import javafx.collections.FXCollections;

public class DAOMovimientoImpl implements DAOMovimiento 
{
	Connection conexion;
	String tablaMovimiento;
	String tablaRubro;
	
	public DAOMovimientoImpl(Connection con, String tm,String tr)
	{
		conexion=con;
		tablaMovimiento=tm;
		tablaRubro=tr;
	}

	@Override
	public Movimiento obtenerMovimiento(Integer id)
	{
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT * from "+tablaMovimiento+" where Id = "+id.toString());
			Integer idd=null;
			String fecha=null;
			Integer idC=null;
			String desc=null;
			BigDecimal monto=null;
			while(rs.next())
			{
				idd=Integer.parseInt(rs.getString("Id"));
				fecha=rs.getString("Fecha");
				idC=Integer.parseInt(rs.getString("Id_Rubro"));
				desc=rs.getString("Descripcion");
				monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		
			
			return new Movimiento(idd, fecha, idC, desc, monto);
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void eliminarMovimiento(Integer id)
	{
		try
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("DELETE from "+tablaMovimiento+" where Id="+id.toString());
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}

	@Override
	public void agregarMovimiento(Movimiento i) 
	{
		try 
		{
			Statement	st = conexion.createStatement();
			if(i.getFecha().length()<12)
			{
				String fTotal=i.getFecha()+" 00:00:00.000000";
				i.setFecha(fTotal);
			}
			
			st.executeUpdate("INSERT INTO "+tablaMovimiento+" (Fecha, Id_Rubro, Descripcion, Monto)"
					+"VALUES('"+i.getFecha()+"', "+i.getId_Rubro().toString()+", '"+i.getDescripcion()+"', "+
					i.getMonto().toString()+")");
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	void agregarIngresoMock(Movimiento i)
	{
		
		try 
		{
			Statement	st = conexion.createStatement();
			st.executeUpdate("INSERT INTO "+tablaMovimiento+" (Id, Fecha, Id_Rubro, Descripcion, Monto)"
					+"VALUES("+i.getId()+", +'"+i.getFecha()+"', "+i.getId_Rubro().toString()+", '"+i.getDescripcion()+"', "+
					i.getMonto().toString()+")");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void editarMovimiento(Integer id, Movimiento i)
	{
		try 
		{
			Statement	st = conexion.createStatement();
			if(i.getFecha().length()<12)
			{
				String fTotal=i.getFecha()+" 00:00:00.000000";
				i.setFecha(fTotal);
			}
			st.executeUpdate("UPDATE "+tablaMovimiento+" "
					+"SET "
					+"Fecha = '"+i.getFecha()+"', "
					+"Id_Rubro = "+i.getId_Rubro().toString()+", "
					+"Descripcion = '"+i.getDescripcion()+"', "
					+"Monto = "+i.getMonto().toString()
					+" WHERE Id = "+i.getId().toString());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientos()
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "
					+ ""+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "
							+ ""+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString(""+tablaMovimiento+".Id"));
				String fecha=rs.getString(""+tablaMovimiento+".Fecha");
				Integer idC=Integer.parseInt(rs.getString(""+tablaMovimiento+".Id_Rubro"));
				String desc=rs.getString(""+tablaMovimiento+".Descripcion");
				BigDecimal monto=new BigDecimal(rs.getString(""+tablaMovimiento+".Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(""+tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		return ingresos;
	}
	
	@Override
	public BigDecimal getMovimientosSuma()
	{
		try 
		{
			BigDecimal suma=null;
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento);	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosEntre(String fechaInicio, String fechaFin)
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";

			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha,"
					+ " "+tablaMovimiento+".Id_Rubro, "+ ""+tablaMovimiento+".Descripcion, "
					+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public BigDecimal getMovimientosEntreSuma(String fechaInicio, String fechaFin)
	{
		
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			
			BigDecimal suma=null;
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+"WHERE Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosDescripcion(String descripcion) 
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Descripcion like '%"+descripcion+"%'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public BigDecimal getMovimientosDescripcionSuma(String descripcion) 
	{
		BigDecimal suma=null;
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Descripcion like '%"+descripcion+"%'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosIdRubro(Integer id) 
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ " WHERE Id_Rubro = "+id.toString());
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BigDecimal getMovimientosIdRubroSuma(Integer id) 
	{
		BigDecimal suma=null;
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Id_Rubro = "+id.toString());	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosDescripcionEntre(String descripcion, String fechaInicio, String fechaFin)
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Descripcion like '%"+descripcion+"%' and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BigDecimal getMovimientosDescripcionEntreSuma(String descripcion, String fechaInicio, String fechaFin)
	{
		BigDecimal suma=null;
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Descripcion like '%"+descripcion+"%' and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosIdRubroEntre(Integer id, String fechaInicio, String fechaFin) 
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Id_Rubro ="+id.toString()+" and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BigDecimal getMovimientosIdRubroEntreSuma(Integer id, String fechaInicio, String fechaFin) 
	{
		BigDecimal suma=null;
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Id_Rubro = "+id.toString()+" and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosIdRubroDescripcion(Integer id, String descripcion) 
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Id_Rubro ="+id.toString()+" and Descripcion like '%"+descripcion+"%'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BigDecimal getMovimientosIdRubroDescripcionSuma(Integer id, String descripcion)
	{
		BigDecimal suma=null;
		try 
		{
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Id_Rubro ="+id.toString()+" and Descripcion like '%"+descripcion+"%'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<Movimiento> getMovimientosIdRubroDescripcionEntre(Integer id, String descripcion, String fechaInicio,
			String fechaFin)
	{
		List<Movimiento>ingresos=FXCollections.observableArrayList();
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT "+tablaMovimiento+".Id, "+tablaMovimiento+".Fecha, "+tablaMovimiento+".Id_Rubro, "
					+ ""+tablaMovimiento+".Descripcion, "+tablaMovimiento+".Monto, "+tablaRubro+".Nombre FROM "+tablaMovimiento+" inner join "+tablaRubro+" "
					+ "on "+tablaMovimiento+".Id_Rubro = "+tablaRubro+".Id_Rubro "
					+ "WHERE Id_Rubro ="+id.toString()+" and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"' "
							+ "and Descripcion like '%"+descripcion+"%'");
		
			while(rs.next())
			{
				Integer idd=Integer.parseInt(rs.getString("Id"));
				String fecha=rs.getString("Fecha");
				Integer idC=Integer.parseInt(rs.getString("Id_Rubro"));
				String desc=rs.getString("Descripcion");
				BigDecimal monto= new BigDecimal(rs.getString("Monto"));
				monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
				String nombre=rs.getString(tablaRubro+".Nombre");
				ingresos.add(new Movimiento(idd, fecha, idC, desc, monto, nombre));
			}
			return ingresos;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BigDecimal getMovimientosIdRubroDescripcionEntreSuma(Integer id, String descripcion, String fechaInicio,
			String fechaFin) 
	{
		BigDecimal suma=null;
		try 
		{
			if(fechaInicio.length() < 12)
				fechaInicio+=" 00:00:00.000000";
			if(fechaFin.length() <12)
				fechaFin+=" 00:00:00.000000";
			
			Statement st=conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT sum(Monto) from "+tablaMovimiento+" "
					+ "WHERE Id_Rubro = "+id.toString()+" and Fecha >= '"+fechaInicio+"' and Fecha <= '"+fechaFin+"' "
							+ "and Descripcion like '%"+descripcion+"%'");	
			if(rs.next() && rs.getString(1) != null)
			{
				suma=new BigDecimal(rs.getString(1));
				suma=suma.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			else
				suma=new BigDecimal("0.00");
			return suma;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	
}
