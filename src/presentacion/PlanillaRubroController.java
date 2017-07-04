package presentacion;

import java.net.URL;
import java.util.ResourceBundle;

import interfaces.DAORubro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import negocio.Rubro;

public class PlanillaRubroController implements Initializable
{
	private DAORubro daoRubro;
	private ObservableList<Rubro>resultados=FXCollections.observableArrayList();
	private Integer indiceRubro=null;
	private Alert alert = new Alert(AlertType.INFORMATION);
	
	@FXML AnchorPane apRubro;
	@FXML Label lblRubro;
	
	@FXML TextField tfBusquedaNombre;
	@FXML Button btnBuscarPorNombre;
	@FXML Button btnMostrarTodos;
	
	@FXML TableView<Rubro>tableRubro;
	@FXML TableColumn<Rubro, String>colNombre;
	@FXML TableColumn<Rubro, String>colTel1;
	@FXML TableColumn<Rubro, String>colTel2;
	@FXML TableColumn<Rubro, String>colMail;
	@FXML TableColumn<Rubro, String>colFacebook;
	@FXML TableColumn<Rubro, String>colDireccion;
	
	@FXML TextField tfNombre;
	@FXML TextField tfTel1;
	@FXML TextField tfTel2;
	@FXML TextField tfMail;
	@FXML TextField tfFacebook;
	@FXML TextField tfDireccion;
	
	@FXML Button btnAgregar;
	@FXML Button btnEditar;
	@FXML Button btnEliminar;
	@FXML Button btnLimpiarCampos;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try 
		{
			colNombre.setCellValueFactory(new PropertyValueFactory<Rubro, String>("nombre"));
			colTel1.setCellValueFactory(new PropertyValueFactory<Rubro, String>("telefono1"));
			colTel2.setCellValueFactory(new PropertyValueFactory<Rubro, String>("telefono2"));
			colMail.setCellValueFactory(new PropertyValueFactory<Rubro, String>("mail"));
			colFacebook.setCellValueFactory(new PropertyValueFactory<Rubro, String>("facebook"));
			colDireccion.setCellValueFactory(new PropertyValueFactory<Rubro, String>("direccion"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void setearDAORubro(DAORubro dao)
	{
		try
		{
			daoRubro=dao;
			mostrarDatos();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public void setearComoClientes()
	{
		apRubro.setStyle("-fx-background-color: #99c2ff");
		lblRubro.setText("Clientes");
		lblRubro.setTextFill(Paint.valueOf("Blue"));
	}
	
	public void setearComoGastos()
	{
		apRubro.setStyle("-fx-background-color: #F5A9A9");
		lblRubro.setText("Gastos");
		lblRubro.setTextFill(Paint.valueOf("Red"));
	}
	
	@FXML
	public void seleccionarRubro(MouseEvent event)
	{
		try 
		{
			Rubro rub=tableRubro.getSelectionModel().getSelectedItem();
			
			if(rub != null)
			{
				tfNombre.setText(rub.getNombre());
				tfTel1.setText(rub.getTelefono1());
				tfTel2.setText(rub.getTelefono2());
				tfMail.setText(rub.getMail());
				tfFacebook.setText(rub.getFacebook());
				tfDireccion.setText(rub.getDireccion());
				indiceRubro=rub.getId();
				seleccionarSiguiente(tfNombre);
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void agregarRubro(ActionEvent event)
	{
		agregarRubro();
		
		
		
	}

	@FXML 
	public void agregarRubroK(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
		{
			agregarRubro();
		}
	}
	
	@FXML
	public void editarRubro(ActionEvent event)
	{
		try 
		{
			String nombre=tfNombre.getText();
			String tel1=tfTel1.getText();
			String tel2=tfTel2.getText();
			String mail=tfMail.getText();
			String facebook=tfFacebook.getText();
			String direccion=tfDireccion.getText();
			
			daoRubro.editarRubro(indiceRubro, new Rubro(nombre, tel1, tel2, mail, facebook, direccion));
			mostrarDatos();
			limpiarTfs();
			alert.setTitle("Información");
			alert.setHeaderText("Editado correctamente"); 
			alert.setContentText("Editado correctamente");
			alert.showAndWait();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error al Editar"); 
			alert.setContentText("Error al Editar");
			alert.showAndWait();
		}
		
		
	}
	
	@FXML
	public void eliminarRubro(ActionEvent event)
	{
		try 
		{
			daoRubro.borrarRubro(indiceRubro);
			mostrarDatos();
			
			alert.setTitle("Información");
			alert.setHeaderText("Editado correctamente"); 
			alert.setContentText("Editado correctamente");
			alert.showAndWait();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error al Eliminar"); 
			alert.setContentText("Error al Eliminar");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void limpiarCampos(ActionEvent event)
	{
		limpiarTfs();
		seleccionarSiguiente(tfNombre);
	}
	
	@FXML
	public void buscarPorNombre(ActionEvent event)
	{
		buscarPorNombre();
	}

	@FXML 
	public void buscarPorNombreK(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
		{
			buscarPorNombre();
		}
	}
	
	@FXML
	public void mostrarTodos(ActionEvent event)
	{
		mostrarDatos();
	}
	
	@FXML
	public void seleccionarTel1(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			seleccionarSiguiente(tfTel1);
	}
	
	@FXML
	public void seleccionarTel2(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			seleccionarSiguiente(tfTel2);
	}
	
	@FXML
	public void seleccionarMail(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			seleccionarSiguiente(tfMail);
	}
	
	@FXML
	public void seleccionarFacebook(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			seleccionarSiguiente(tfFacebook);
	}
	
	@FXML
	public void seleccionarDireccion(KeyEvent event)
	{
		if(event.getCode().equals(KeyCode.ENTER))
			seleccionarSiguiente(tfDireccion);
	}
	
	//auxiliares
	
	public void mostrarDatos()
	{
		resultados=(ObservableList<Rubro>)daoRubro.getRubros();
		tableRubro.setItems(resultados);
	}
	
	public void limpiarTfs()
	{
		tfNombre.setText("");
		tfTel1.setText("");
		tfTel2.setText("");
		tfMail.setText("");
		tfFacebook.setText("");
		tfDireccion.setText("");
	}
	
	private void agregarRubro() {
		try 
		{
			String nombre=tfNombre.getText();
			String tel1=tfTel1.getText();
			String tel2=tfTel2.getText();
			String mail=tfMail.getText();
			String facebook=tfFacebook.getText();
			String direccion=tfDireccion.getText();
			
			Rubro rub=new Rubro(nombre, tel1, tel2, mail, facebook, direccion);
			daoRubro.agregarRubro(rub);
			limpiarTfs();
			mostrarDatos();
			alert.setTitle("Información");
			alert.setHeaderText("Agregado correctamente"); 
			alert.setContentText("Agregado correctamente");
			alert.showAndWait();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error al agregar"); 
			alert.setContentText("Error al agregar");
			alert.showAndWait();
		}
	}
	
	private void buscarPorNombre() 
	{
		try
		{
			resultados=(ObservableList<Rubro>)daoRubro.getRubrosPorNombre(tfBusquedaNombre.getText());
			tableRubro.setItems(resultados);
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
	
	private void seleccionarSiguiente(TextField siguiente)
	{
		siguiente.requestFocus();
	}
	
}
