import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

public class MainController {
	@FXML Button button1;
	
	@FXML
	private void OnActionEvent(ActionEvent event) {
		button1.setText("Beans");
	}
}
