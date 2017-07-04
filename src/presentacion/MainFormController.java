package presentacion;

import datos.Conectable;

import interfaces.DAOMovimiento;
import interfaces.DAORubro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import negocio.DAORubroImpl;
import servicios.Propiedades;
import negocio.DAOMovimientoImpl;

public class MainFormController 
{
	Conectable con;
	Alert alert = new Alert(AlertType.INFORMATION);
	@FXML Button btnPlanilla;
	@FXML Button btnEgresos;
	@FXML Button btnClientes;
	@FXML Button btnGastos;
	
	public void setearConexion(Conectable con)
	{
		this.con=con;
	}
	
	@FXML
	public void abrir(ActionEvent event)
	{
		try
		{
			Stage planillaStage=new Stage();
			planillaStage.getIcons().add(new Image("file:"+Propiedades.getPropiedades("ubicacion.icono")));
			planillaStage.setTitle("Planilla de Ingresos");
			FXMLLoader loader= new FXMLLoader();
			
			//agregamos el openStream (no se para que)
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("PlanillaMovimientos.fxml").openStream());
			
			//ahora creo una instancia del controlador del form que voy a abrir casteando
			PlanillaMovimientosController planillaIngresosController= (PlanillaMovimientosController)loader.getController();
			
			DAOMovimiento di=new DAOMovimientoImpl(con.getConnection(), "Ingresos", "Clientes");
			DAORubro dr=new DAORubroImpl(con.getConnection(), "Clientes");
			
			planillaIngresosController.setearDAOMovimiento(di);
			planillaIngresosController.setearDAORubro(dr);
			planillaIngresosController.setearPlanillaIngresos();
			
			Scene scene = new Scene(root,950,700);
			
			planillaStage.setScene(scene);
			planillaStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error cargar");
			alert.setContentText("Hay algun error, intente nuevamente, si el problema persiste contactacte a su programador");
			
			alert.showAndWait();
		}
	}
	
	public void abrirEgresos(ActionEvent event)
	{
		try
		{
			Stage planillaStage=new Stage();
			planillaStage.getIcons().add(new Image("file:"+Propiedades.getPropiedades("ubicacion.icono")));
			planillaStage.setTitle("Planilla de Egresos");
			FXMLLoader loader= new FXMLLoader();
			
			//agregamos el openStream (no se para que)
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("PlanillaMovimientos.fxml").openStream());
			
			//ahora creo una instancia del controlador del form que voy a abrir casteando
			PlanillaMovimientosController planillaIngresosController= (PlanillaMovimientosController)loader.getController();
			
			DAOMovimiento di=new DAOMovimientoImpl(con.getConnection(), "Egresos", "Gastos");
			DAORubro dr=new DAORubroImpl(con.getConnection(), "Gastos");
			
			planillaIngresosController.setearDAOMovimiento(di);
			planillaIngresosController.setearDAORubro(dr);
			planillaIngresosController.setearPlanillaEgresos();
			
			Scene scene = new Scene(root,950,700);
			
			planillaStage.setScene(scene);
			planillaStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error cargar");
			alert.setContentText("Hay algun error, intente nuevamente, si el problema persiste contactacte a su programador");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void abrirClientes(ActionEvent event)
	{
		try
		{
			Stage planillaStage=new Stage();
			//planillaStage.getIcons().add(new Image("file:INTESA_icono.jpg"));
			//"file:"+Propiedades.getPropiedades("ubicacion.icono")
			planillaStage.getIcons().add(new Image("file:"+Propiedades.getPropiedades("ubicacion.icono")));
			planillaStage.setTitle("Planilla de Clientes");
			FXMLLoader loader= new FXMLLoader();
			
			//agregamos el openStream (no se para que)
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("PlanillaRubro.fxml").openStream());
			
			//ahora creo una instancia del controlador del form que voy a abrir casteando
			PlanillaRubroController planillaRubrosController= (PlanillaRubroController)loader.getController();
			
			DAORubro dc = new DAORubroImpl(con.getConnection(), "Clientes");
			planillaRubrosController.setearDAORubro(dc);
			planillaRubrosController.setearComoClientes();
			
			Scene scene = new Scene(root,950,700);
			
			planillaStage.setScene(scene);
			planillaStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error cargar");
			alert.setContentText("Hay algun error, intente nuevamente, si el problema persiste contactacte a su programador");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void abrirGastos(ActionEvent event)
	{
		try
		{
			Stage planillaStage=new Stage();
			planillaStage.getIcons().add(new Image("file:"+Propiedades.getPropiedades("ubicacion.icono")));
			planillaStage.setTitle("Planilla de Gastos");
			FXMLLoader loader= new FXMLLoader();
			
			//agregamos el openStream (no se para que)
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("PlanillaRubro.fxml").openStream());
			
			//ahora creo una instancia del controlador del form que voy a abrir casteando
			PlanillaRubroController planillaRubrosController= (PlanillaRubroController)loader.getController();
			
			DAORubro dc = new DAORubroImpl(con.getConnection(), "Gastos");
			planillaRubrosController.setearDAORubro(dc);
			planillaRubrosController.setearComoGastos();
			
			Scene scene = new Scene(root,950,700);
			
			planillaStage.setScene(scene);
			planillaStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			alert.setTitle("Error");
			alert.setHeaderText("Error cargar");
			alert.setContentText("Hay algun error, intente nuevamente, si el problema persiste contactacte a su programador");
			alert.showAndWait();
		}
	}
	
}
