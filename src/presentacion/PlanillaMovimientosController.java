package presentacion;


import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import interfaces.DAORubro;
import interfaces.DAOMovimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import negocio.Rubro;
import negocio.Movimiento;


public class PlanillaMovimientosController implements Initializable
{
	private DAOMovimiento daoMovimientos;
	private DAORubro daoRubro;
	private ObservableList<Rubro>resultadosRubros=FXCollections.observableArrayList();
	private ObservableList<Movimiento>resultadosMovimientos=FXCollections.observableArrayList();
	private Alert alert = new Alert(AlertType.INFORMATION);
	

	private Integer indiceRubros=null;
	private Integer indiceMovimientos=null;
	private BigDecimal suma=new BigDecimal("0.00");
	
	@FXML AnchorPane apBusqueda;
	@FXML AnchorPane apDatos;
	@FXML Label lblMovimientos;
	@FXML Label lblRubros;
	@FXML Label lblSuma;
	@FXML Label lblRubroAP;
	@FXML TableView<Movimiento>tablaMovimientos; 
	@FXML TableColumn<Movimiento, String>iColFecha;
	@FXML TableColumn<Movimiento, Integer>iColIdRubro;
	@FXML TableColumn<Movimiento, String>iColRubro;
	@FXML TableColumn<Movimiento, String>iColDescripcion;
	@FXML TableColumn<Movimiento, BigDecimal>iColMonto;
	
	@FXML TableView<Rubro>tablaRubros;
	@FXML TableColumn<Rubro, Integer>cColId;
	@FXML TableColumn<Rubro, String>cColNombre;
	
	@FXML TextField tfRubro;
	@FXML TextField tfFecha;
	@FXML CheckBox cbFecha;
	@FXML DatePicker dpIFecha;
	@FXML TextField tfDescripcion;
	@FXML TextField tfMonto;
	
	@FXML Button btnAgregarMovimiento;
	@FXML Button btnEditarMovimiento;
	@FXML Button btnEliminarMovimiento;
	@FXML Button btnLimpiarCampos;
	@FXML Button btnIActualizar;
	
	@FXML Button btnCActualizar;
	@FXML Button btnCFiltrar;
	@FXML TextField tfCNombre;
	
	@FXML TextField tfFDescripcion;
	@FXML TextField tfFRubro;
	@FXML TextField tfFFechaInicio;
	@FXML TextField tfFFechaFin;
	@FXML DatePicker dpFInicio;
	@FXML DatePicker dpFFin;
	@FXML CheckBox cbDescripcion;
	@FXML CheckBox cbRubro;
	@FXML CheckBox cbFechas;
	@FXML Button btnBuscar;
	@FXML Button btnFLimpiar;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		iColFecha.setCellValueFactory(new PropertyValueFactory<Movimiento, String>("fecha"));
		iColIdRubro.setCellValueFactory(new PropertyValueFactory<Movimiento, Integer>("id_Rubro"));
		iColRubro.setCellValueFactory(new PropertyValueFactory<Movimiento, String>("nombreRubro"));
		iColDescripcion.setCellValueFactory(new PropertyValueFactory<Movimiento, String>("descripcion"));
		iColMonto.setCellValueFactory(new PropertyValueFactory<Movimiento, BigDecimal>("monto"));
		iColMonto.setStyle( "-fx-alignment: CENTER-RIGHT;");
		
		cColId.setCellValueFactory(new PropertyValueFactory<Rubro, Integer>("id"));
		cColId.setSortType(TableColumn.SortType.DESCENDING);
		cColNombre.setCellValueFactory(new PropertyValueFactory<Rubro, String>("nombre"));
		
		String pattern="yyyy-MM-dd";
		StringConverter<LocalDate>converter=new StringConverter<LocalDate>() 
			{
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

				@Override
				public LocalDate fromString(String string) {
					 if (string != null && !string.isEmpty()) {
			             return LocalDate.parse(string, dateFormatter);
			         } else {
			             return null;
			         }
				}

				@Override
				public String toString(LocalDate date) {
					 if (date != null) {
			             return dateFormatter.format(date);
			         } else {
			             return "";
			         }
				}
			};
		dpIFecha.setConverter(converter);
		dpFFin.setConverter(converter);
		dpFInicio.setConverter(converter);
		dpIFecha.setEditable(false);
		dpFFin.setEditable(false);
		dpFInicio.setEditable(false);
		dpIFecha.getEditor().setVisible(false);
		dpFFin.getEditor().setVisible(false);
		dpFInicio.getEditor().setVisible(false);
		LocalDate fecha=LocalDate.now();
		tfFecha.setText(fecha.toString());
		cbFecha.setSelected(true);
		tfRubro.setEditable(false);
		tfFRubro.setEditable(false);
		suma=suma.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		tfFecha.setEditable(false);
	}
	
	public void setearDAOMovimiento( DAOMovimiento movimiento)
	{
		this.daoMovimientos=movimiento;
		mostrarDatosTablaMovimientos();
		suma=movimiento.getMovimientosSuma();
		lblSuma.setText("$ "+suma.toString());
	}
	
	public void setearDAORubro(DAORubro rubro)
	{
		this.daoRubro=rubro;
		mostrarTodoTablaRubros();
		if(resultadosRubros.size()!=0)
		{
			Rubro primero=resultadosRubros.get(0);
			if(primero!=null)
			{
				indiceRubros=primero.getId();
				tfFRubro.setText(primero.getId().toString()+" "+primero.getNombre());
				tfRubro.setText(primero.getId().toString()+" "+primero.getNombre());
			}
		}
	}
	
	public void setearPlanillaIngresos()
	{
		lblMovimientos.setText("Ingresos");
		lblRubros.setText("Clientes");
		apBusqueda.setBackground(new Background(new BackgroundFill(Color.web("#99c2ff"), CornerRadii.EMPTY, Insets.EMPTY)));
		apDatos.setBackground(new Background(new BackgroundFill(Color.web("#99c2ff"), CornerRadii.EMPTY, Insets.EMPTY)));
		lblSuma.setTextFill(Paint.valueOf("blue"));
		lblRubros.setTextFill(Paint.valueOf("blue"));
		lblMovimientos.setTextFill(Paint.valueOf("blue"));
		lblRubroAP.setText("Cliente ");
	}
	
	public void setearPlanillaEgresos()
	{
		lblMovimientos.setText("Egresos");
		lblRubros.setText("Gastos");
		apBusqueda.setStyle("-fx-background-color: #F5A9A9");
		apDatos.setStyle("-fx-background-color: #F5A9A9");
		lblSuma.setTextFill(Paint.valueOf("red"));
		lblRubros.setTextFill(Paint.valueOf("Red"));
		lblMovimientos.setTextFill(Paint.valueOf("Red"));
		lblRubroAP.setText("Gasto ");
	}
	
	@FXML
	public void seleccionarMovimiento()
	{
		Movimiento ing = tablaMovimientos.getSelectionModel().getSelectedItem();
		if(ing != null)
		{
			iLimpiarTFs();
			indiceMovimientos=ing.getId();
			indiceRubros=ing.getId_Rubro();
			tfFRubro.setText(ing.getId_Rubro().toString()+" "+ing.getNombreRubro());
			tfRubro.setText(ing.getId_Rubro().toString()+" "+ing.getNombreRubro());
			tfDescripcion.setText(ing.getDescripcion());
			tfFecha.setText(ing.getFecha());
			indiceRubros=ing.getId_Rubro();
			tfMonto.setText(ing.getMonto().toString());
		}
	}
	
	@FXML
	public void seleccionarRubro()
	{
		Rubro cli=tablaRubros.getSelectionModel().getSelectedItem();
		if(cli != null)
		{
			indiceRubros=cli.getId();
			tfFRubro.setText(cli.getId().toString()+" "+cli.getNombre());
			tfRubro.setText(cli.getId().toString()+" "+cli.getNombre());
			tfDescripcion.requestFocus();
		}
	}
	
	@FXML
	public void agregarMovimiento(ActionEvent event) 
	{
		agregarMovimiento();
	}

	@FXML
	public void agregarMovimientoK(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			agregarMovimiento();
	}
	
	@FXML
	public void editarMovimiento(ActionEvent event)
	{
		try
		{
			Integer id = indiceMovimientos;
			String fecha=tfFecha.getText();
			String descripcion=tfDescripcion.getText();
			Integer idc=indiceRubros;
			BigDecimal monto=new BigDecimal(tfMonto.getText());
			monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			daoMovimientos.editarMovimiento(id, new Movimiento(id, fecha, idc, descripcion, monto));
			
			mostrarDatosTablaMovimientos();
			alert.setTitle("Información");
			alert.setHeaderText("Editado correctamente"); 
			alert.setContentText("Editado correctamente");
			alert.showAndWait();
			iLimpiarTFs();
		} 
		catch (Exception e)
		{
			alert.setTitle("Error");
			alert.setHeaderText("Error al agregar ingreso");
			alert.setContentText("Algun dato es erroneo, corrija el error e intente nuevamente");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void eliminarMovimiento(ActionEvent event)
	{
		try
		{
			daoMovimientos.eliminarMovimiento(indiceMovimientos);
			mostrarDatosTablaMovimientos();
			iLimpiarTFs();
			alert.setTitle("Correcto");
			alert.setHeaderText("El ingreso se eliminó correctamente");
			alert.setContentText("El ingreso se eliminó correctamente");
			alert.showAndWait();
		}
		catch (Exception e)
		{
			alert.setTitle("Error");
			alert.setHeaderText("Error al eliminar ingreso");
			alert.setContentText("El error puede deberse a una falla en la conexion, actualize y pruebe nuevamente");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void limpiarCampos(ActionEvent event)
	{
		iLimpiarTFs();
		cbFecha.setSelected(true);
	}
	
	@FXML
	public void actualizarMovimientos(ActionEvent event)
	{
		mostrarDatosTablaMovimientos();
		suma=daoMovimientos.getMovimientosSuma();
		lblSuma.setText("$ "+suma.toString());
	}
	
	@FXML
	public void actualizarRubros(ActionEvent event)
	{
		mostrarTodoTablaRubros();
	}
	
	@FXML 
	public void ponerFecha(ActionEvent event)
	{
		if(cbFecha.isSelected())
		{
			LocalDate fecha=LocalDate.now();
			tfFecha.setText(fecha.toString());
		}
		else
			tfFecha.setText("");
		tfDescripcion.requestFocus();
	}
	
	@FXML 
	public void buscarRubroNombre(ActionEvent event)
	{
		String nombre=tfCNombre.getText();
		
		if(nombre.equals("")==false)
		{
			resultadosRubros=(ObservableList<Rubro>) daoRubro.getRubrosPorNombre(nombre);
			tablaRubros.setItems(resultadosRubros);
			tablaRubros.getSortOrder().add(cColId);
		}
		else
		{
			alert.setTitle("Cuidado");
			alert.setHeaderText("¡Cuidado!");
			alert.setContentText("Debe escribir un nombre, apellido o parte de los mismos para poder filtrar");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void buscarRubroNombreK(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
		{
			String nombre=tfCNombre.getText();
			
			if(nombre.equals("")==false)
			{
				resultadosRubros=(ObservableList<Rubro>) daoRubro.getRubrosPorNombre(nombre);
				tablaRubros.setItems(resultadosRubros);
				tablaRubros.getSortOrder().add(cColId);
			}
			else
			{
				alert.setTitle("Cuidado");
				alert.setHeaderText("¡Cuidado!");
				alert.setContentText("Debe escribir un nombre, apellido o parte de los mismos para poder filtrar");
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	public void iSeleccionarFecha(ActionEvent event)
	{
		//usar siempre getEditor! es mas facil! obtiene el tf
		try 
		{
			if(dpIFecha.getValue() != null)
			{
				tfFecha.setText(dpIFecha.getValue().toString());
				cbFecha.setSelected(false);
				tfDescripcion.requestFocus();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void fSeleccionarFechaInicio(ActionEvent event)
	{
		//usar siempre getEditor! es mas facil! obtiene el tf
		try 
		{
			if(dpFInicio.getValue() != null)
				tfFFechaInicio.setText(dpFInicio.getValue().toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void fSeleccionarFechaFin(ActionEvent event)
	{
		//usar siempre getEditor! es mas facil! obtiene el tf
		try 
		{
			//Siempre preguntamos si es null, ya que si no lo hacemos y resulta serlo se rompe
			if(dpFFin.getValue() != null)
				tfFFechaFin.setText(dpFFin.getValue().toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void buscar(ActionEvent event)
	{
		try 
		{
			if(cbDescripcion.isSelected()==true && cbRubro.isSelected()==false && cbFechas.isSelected()==false)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosDescripcion(tfFDescripcion.getText());
				suma=daoMovimientos.getMovimientosDescripcionSuma(tfFDescripcion.getText());
			}
			else if(cbDescripcion.isSelected()==false  && cbRubro.isSelected()==true && cbFechas.isSelected()==false)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosIdRubro(indiceRubros);
				suma=daoMovimientos.getMovimientosIdRubroSuma(indiceRubros);
			}
			else if(cbDescripcion.isSelected()==false  && cbRubro.isSelected()==false && cbFechas.isSelected()==true)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosEntre(tfFFechaInicio.getText(), tfFFechaFin.getText());
				suma=daoMovimientos.getMovimientosEntreSuma(tfFFechaInicio.getText(), tfFFechaFin.getText());
			}
			else if(cbDescripcion.isSelected()==true  && cbRubro.isSelected()==true && cbFechas.isSelected()==false)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosIdRubroDescripcion(indiceRubros, tfFDescripcion.getText());
				suma=daoMovimientos.getMovimientosIdRubroDescripcionSuma(indiceRubros, tfFDescripcion.getText());
			}
			else if(cbDescripcion.isSelected()==true  && cbRubro.isSelected()==false && cbFechas.isSelected()==true)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosDescripcionEntre(tfFDescripcion.getText(), tfFFechaInicio.getText(),
						tfFFechaFin.getText());
				suma=daoMovimientos.getMovimientosDescripcionEntreSuma(tfFDescripcion.getText(), tfFFechaInicio.getText(),
						tfFFechaFin.getText());
			}
			else if(cbDescripcion.isSelected()==false  && cbRubro.isSelected()==true && cbFechas.isSelected()==true)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosIdRubroEntre(indiceRubros, tfFFechaInicio.getText(),
						tfFFechaFin.getText());
				suma=daoMovimientos.getMovimientosIdRubroEntreSuma(indiceRubros, tfFFechaInicio.getText(),
						tfFFechaFin.getText());
			}
			else if(cbDescripcion.isSelected()==true  && cbRubro.isSelected()==true && cbFechas.isSelected()==true)
			{
				resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientosIdRubroDescripcionEntre(indiceRubros, tfFDescripcion.getText()
						, tfFFechaInicio.getText(), tfFFechaFin.getText());
				suma=daoMovimientos.getMovimientosIdRubroDescripcionEntreSuma(indiceRubros, tfFDescripcion.getText()
						, tfFFechaInicio.getText(), tfFFechaFin.getText());
			}
			else if(cbDescripcion.isSelected()==false  && cbRubro.isSelected()==false && cbFechas.isSelected()==false)
			{
				alert.setTitle("Error");
				alert.setHeaderText("Error al buscar");
				alert.setContentText("Debe seleccionar al menos un criterio");
				alert.showAndWait();
				mostrarDatosTablaMovimientos();
				suma=daoMovimientos.getMovimientosSuma();
				lblSuma.setText("$ "+suma.toString());
			}
			if(resultadosMovimientos != null)
			{
				lblSuma.setText("$ "+suma.toString());
				tablaMovimientos.setItems(resultadosMovimientos);
				ordenarTablaMovimientos();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error al buscar");
			alert.setContentText("Error al buscar");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	public void fLimpiarCampos(ActionEvent event)
	{
		fLimpiarCampos();
	}
	
	@FXML
	public void bajarDescripcion(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
		{
			tfMonto.requestFocus();
		}
	}

	//metodos auxiliares
	private void iLimpiarTFs()
	{
		LocalDate fecha=LocalDate.now();
		tfFecha.setText(fecha.toString());
		tfDescripcion.setText("");
		tfMonto.setText("");
		//esto limpia por completo la seleccion de un DatePicker
		dpIFecha.getEditor().clear();
		dpIFecha.setValue(null);
	}
	
	private void mostrarDatosTablaMovimientos() 
	{
		resultadosMovimientos=(ObservableList<Movimiento>) daoMovimientos.getMovimientos();
		tablaMovimientos.setItems(resultadosMovimientos);
		ordenarTablaMovimientos();
		suma=daoMovimientos.getMovimientosSuma();
		lblSuma.setText("$ "+suma.toString());
	}

	private void ordenarTablaMovimientos()
	{
		iColFecha.setSortType(TableColumn.SortType.DESCENDING);
		tablaMovimientos.getSortOrder().add(iColFecha);
	}
	
	private void mostrarTodoTablaRubros()
	{
		resultadosRubros=(ObservableList<Rubro>)daoRubro.getRubros();
		tablaRubros.setItems(resultadosRubros);
		tablaRubros.getSortOrder().add(cColId);
	}
	
	private void fLimpiarCampos() 
	{
		tfFDescripcion.setText("");
		tfFFechaFin.setText("");
		tfFFechaInicio.setText("");
		dpFInicio.getEditor().clear();
		dpFInicio.setValue(null);
		dpFFin.getEditor().clear();
		dpFFin.setValue(null);
		cbDescripcion.setSelected(false);
		cbFechas.setSelected(false);
		cbRubro.setSelected(false);
	}

	private void agregarMovimiento() 
	{
		try 
		{
			String fecha=tfFecha.getText();
			String descripcion=tfDescripcion.getText();
			Integer idc=indiceRubros;
			BigDecimal monto=new BigDecimal(tfMonto.getText());
			monto=monto.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			daoMovimientos.agregarMovimiento(new Movimiento(fecha, idc, descripcion, monto));
			mostrarDatosTablaMovimientos();
			alert.setTitle("Información");
			alert.setHeaderText("Agregado correctamente"); 
			alert.setContentText("Agregado correctamente");
			alert.showAndWait();
			//Esto pone el focus en el tf que lo llama
			tfDescripcion.requestFocus();
			iLimpiarTFs();
		} 
		catch (Exception e)
		{
			alert.setTitle("Error");
			alert.setHeaderText("Error al agregar ingreso");
			alert.setContentText("Algun dato es erroneo, corrija el error e intente nuevamente");
			alert.showAndWait();
		}
	}
}
