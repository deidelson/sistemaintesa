package negocio;

public class Rubro
{
	private Integer id;
	private String nombre;
	private String telefono1;
	private String telefono2;
	private String mail;
	private String facebook;
	private String direccion;
	
	public Rubro(String nombre, String telefono1, String telefono2, String mail, String facebook, String direccion) 
	{
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.mail = mail;
		this.facebook = facebook;
		this.direccion = direccion;
	}
	
	//para mock
	public Rubro(Integer id_cliente, String nombre, String telefono1, String telefono2, String mail, String facebook,
			String direccion) 
	{
		this.id = id_cliente;
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.mail = mail;
		this.facebook = facebook;
		this.direccion = direccion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((facebook == null) ? 0 : facebook.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((telefono1 == null) ? 0 : telefono1.hashCode());
		result = prime * result + ((telefono2 == null) ? 0 : telefono2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rubro other = (Rubro) obj;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (facebook == null) {
			if (other.facebook != null)
				return false;
		} else if (!facebook.equals(other.facebook))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (telefono1 == null) {
			if (other.telefono1 != null)
				return false;
		} else if (!telefono1.equals(other.telefono1))
			return false;
		if (telefono2 == null) {
			if (other.telefono2 != null)
				return false;
		} else if (!telefono2.equals(other.telefono2))
			return false;
		return true;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id_cliente) 
	{
		this.id = id_cliente;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono1()
	{
		return telefono1;
	}

	public void setTelefono1(String telefono1)
	{
		this.telefono1 = telefono1;
	}

	public String getTelefono2()
	{
		return telefono2;
	}

	public void setTelefono2(String telefono2)
	{
		this.telefono2 = telefono2;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getFacebook() 
	{
		return facebook;
	}

	public void setFacebook(String facebook) 
	{
		this.facebook = facebook;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}



	
}
