package negocio;

import java.math.BigDecimal;

public class Movimiento
{
	private Integer id;
	private String fecha;
	private Integer id_Rubro;
	private String descripcion;
	private BigDecimal monto;
	private String nombreRubro;
	
	public Movimiento(Integer idd, String f, Integer idC, String d, BigDecimal m)
	{
		id=idd;
		fecha=f;
		id_Rubro=idC;
		descripcion=d;
		monto=m;
		nombreRubro=null;
	}
	
	public Movimiento(String f, Integer idC, String d, BigDecimal m)
	{
		id=null;
		fecha=f;
		id_Rubro=idC;
		descripcion=d;
		monto=m;
		nombreRubro=null;
	}
	
	public Movimiento(Integer idd, String f, Integer idC, String d, BigDecimal m, String nombre)
	{
		id=idd;
		fecha=f;
		id_Rubro=idC;
		descripcion=d;
		monto=m;
		nombreRubro=nombre;
	}
	
	public Movimiento(String f, Integer idC, String d, BigDecimal m, String nombre)
	{
		id=null;
		fecha=f;
		id_Rubro=idC;
		descripcion=d;
		monto=m;
		nombreRubro=nombre;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id) 
	{
		this.id = id;
	}

	public String getFecha()
	{
		return fecha;
	}
	
	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}
	
	public Integer getId_Rubro()
	{
		return id_Rubro;
	}
	
	public void setId_Rubro(Integer id_Rubro) 
	{
		this.id_Rubro = id_Rubro;
	}
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	public BigDecimal getMonto()
	{
		return monto;
	}

	public void setMonto(BigDecimal monto)
	{
		this.monto = monto;
	}
	
	public String getNombreRubro() 
	{
		return nombreRubro;
	}

	public void setNombreRubro(String nombreRubro)
	{
		this.nombreRubro = nombreRubro;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((id_Rubro == null) ? 0 : id_Rubro.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result + ((nombreRubro == null) ? 0 : nombreRubro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimiento other = (Movimiento) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (id_Rubro == null) {
			if (other.id_Rubro != null)
				return false;
		} else if (!id_Rubro.equals(other.id_Rubro))
			return false;
		if (monto == null) {
			if (other.monto != null)
				return false;
		} else if (!monto.equals(other.monto))
			return false;
		if (nombreRubro == null) {
			if (other.nombreRubro != null)
				return false;
		} else if (!nombreRubro.equals(other.nombreRubro))
			return false;
		return true;
	}
	
}
