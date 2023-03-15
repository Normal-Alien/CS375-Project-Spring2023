import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
 
public class MainController {
	// @FXML tag to be used for every item that has to be used with the fxml file
	@FXML Button button1;
	
	/* Method names can be changed according to the function
	 * WARNING: MUST ALSO CHANGE THE REFERENCED NAMES THROUGH SCENEBUILDER OR THE 
	 * FXML FILE ITSELF
	 */
	@FXML
	private void OnActionEvent(ActionEvent event) {
		button1.setText("Beans");
	}
}
