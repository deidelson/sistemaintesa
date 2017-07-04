package presentacion;
	
import datos.Conectable;
import datos.ConexionAccess;
import javafx.application.Application;
import javafx.stage.Stage;
import servicios.Propiedades;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	Conectable con;
	Alert alert = new Alert(AlertType.INFORMATION);
	
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			//primaryStage.getIcons().add(new Image("file:INTESA_icono.jpg"));//file:images/pic.jpeg
			primaryStage.getIcons().add(new Image("file:"+Propiedades.getPropiedades("ubicacion.icono")));//file:images/pic.jpeg
			primaryStage.setTitle("Intesa servicios informaticos");
			//creo instancia del loader para posteriormente obtener el controller
			FXMLLoader loader = new FXMLLoader();
			//el openStream abre la conexion y retorna el input para guardarlo en una variable
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("MainForm.fxml").openStream());
			//instancio un controller del form que quiero abrir lo inicio con el controler del form que abro
			MainFormController mainFormController=loader.getController();
			//uso los setters del mismo
			
			//con=new ConexionAccess("jdbc:ucanaccess://bdPruebas.accdb");
			con=new ConexionAccess("jdbc:ucanaccess://"+Propiedades.getPropiedades("ubicacion.bd"));
			mainFormController.setearConexion(con);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e)
		{
			alert.setTitle("Error");
			alert.setHeaderText("Error cargar");
			alert.setContentText("Hay algun error, intente nuevamente, si el error persiste contactacte a su programador PRINCIPIO");
			alert.showAndWait();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
