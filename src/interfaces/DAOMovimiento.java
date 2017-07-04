package interfaces;

import java.math.BigDecimal;
import java.util.List;

import negocio.Movimiento;

public interface DAOMovimiento 
{
	public Movimiento obtenerMovimiento(Integer id);
	public void eliminarMovimiento(Integer id);
	public void agregarMovimiento(Movimiento i);
	public void editarMovimiento(Integer id_cliente, Movimiento i);
	public List<Movimiento> getMovimientos();
	public BigDecimal getMovimientosSuma();
	public List<Movimiento> getMovimientosEntre(String fechaInicio, String fechaFin);//1--2
	public BigDecimal getMovimientosEntreSuma(String fechaInicio, String fechaFin);
	public List<Movimiento> getMovimientosDescripcion(String descripcion);//1--3
	public BigDecimal getMovimientosDescripcionSuma(String descripcion);
	public List<Movimiento> getMovimientosIdRubro(Integer id);//2--3
	public BigDecimal getMovimientosIdRubroSuma(Integer id);
	public List<Movimiento> getMovimientosDescripcionEntre(String descripcion, String fechaInicio, String fechaFin);
	public BigDecimal getMovimientosDescripcionEntreSuma(String descripcion, String fechaInicio, String fechaFin);
	public List<Movimiento> getMovimientosIdRubroEntre(Integer id, String fechaIncio, String fechaFin);
	public BigDecimal getMovimientosIdRubroEntreSuma(Integer id, String fechaIncio, String fechaFin);
	public List<Movimiento> getMovimientosIdRubroDescripcion(Integer id, String descripcion);
	public BigDecimal getMovimientosIdRubroDescripcionSuma(Integer id, String descripcion);
	public List<Movimiento> getMovimientosIdRubroDescripcionEntre(Integer id, String descripcion, String fI, String fF);
	public BigDecimal getMovimientosIdRubroDescripcionEntreSuma(Integer id, String descripcion, String fI, String fF);
}
