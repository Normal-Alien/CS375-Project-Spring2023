package fwlk.cookui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.*;

 
public class CookUI extends Application {
	// @FXML tag to be used for every item that has to be used with the fxml file
	
	
	/* Method names can be changed according to the function
	 * WARNING: MUST ALSO CHANGE THE REFERENCED NAMES THROUGH SCENEBUILDER OR THE 
	 * FXML FILE ITSELF
	 */

	
        @Override
	public void start(Stage primaryStage) {
		try {
			//use fxml as resource for root
			/*
                        used ChatGPT with the question:
                        "use javafx to display a dynamically created button"
                        for prototyping dynamic button declaration
                        */
                        Parent root = FXMLLoader.load(getClass().getResource("CookuiV0.1.fxml"));
			
                        
                        
                        Scene scene = new Scene(root);
			//unsure if this line is needed as the fxml file should load it for us
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//title change needed
			primaryStage.setTitle("GUI Thingy");
                        
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
            //System.out.println("yes");
            //MenuNav controller = new MenuNav();
            launch(args);
	}
        
}



