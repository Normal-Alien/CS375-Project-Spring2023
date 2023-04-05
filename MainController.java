import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
 
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
	private void toTresMain(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("tresMain.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
