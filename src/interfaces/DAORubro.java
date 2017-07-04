package interfaces;

import java.util.List;

import negocio.Rubro;

public interface DAORubro 
{
	public Rubro getRubro(Integer id_cliente);
	public void agregarRubro(Rubro c);
	public void borrarRubro(Integer id_cliente);
	public void editarRubro(Integer id_cliente, Rubro c);
	public List<Rubro> getRubros();
	public List<Rubro> getRubrosPorNombre(String nombre);
}
