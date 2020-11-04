package fxOlutKanta;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kanta.Olutkanta;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/** Olutkannan pääohjelma
 * @author Santeri Saarinen
 * @version 9.1.2019
 */
public class OlutKantaMain extends Application {

    
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("OlutKantaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final OlutKantaGUIController olutkantaCtrl = (OlutKantaGUIController)ldr.getController();
            final Scene scene = new Scene(root);
            
            scene.getStylesheets().add(getClass().getResource("olutkanta.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Olutkanta");
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !olutkantaCtrl.voikoSulkea() ) event.consume();
            });
            
            
            Olutkanta olutkanta = new Olutkanta();
            olutkantaCtrl.setOlutkanta(olutkanta);
            
            primaryStage.show();
            
           Application.Parameters params = getParameters();  
           if ( params.getRaw().size() > 0 )  
               olutkantaCtrl.lueTiedosto(params.getRaw().get(0));   
           else 
               if ( !olutkantaCtrl.avaaKanta() ) Platform.exit(); 
        } 
        
        catch(Exception e) {
            e.printStackTrace();
            }
	}
    
	
	/** Käynnistetään käyttöliittymä
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}