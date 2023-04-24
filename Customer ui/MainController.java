package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
 
public class MainController {
	// @FXML tag to be used for every item that has to be used with the fxml file
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/* Method names can be changed according to the function
	 * WARNING: MUST ALSO CHANGE THE REFERENCED NAMES THROUGH SCENEBUILDER OR THE 
	 * FXML FILE ITSELF
	 */

	@FXML
	private void toMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toTresMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("tresMain.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toTresEntrees(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("tresEntrees.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toTresSides(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("tresSides.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void toSubMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("subMain.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toSubEntrees(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("subEntrees.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toSubSides(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("subSides.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toGrillMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("grillMain.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toGrillEntrees(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("grillentrees.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toGrillSides(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("grillSides.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toJazzMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("jazzMain.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toJazzDrinks(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("jazzDrinks.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toJazzFood(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("jazzFood.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
