package application;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.fxml.*;

//start application with proper css stylesheet
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//use fxml as resource for root
			Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
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
		launch(args);
	}
}
