package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.*;
 
public class ItemController {
	// @FXML tag to be used for every item that has to be used with the fxml file
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML 
	public static ScrollPane Addons1;
	
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
	private void addOrder(ActionEvent event) throws IOException{
		/*
		root = FXMLLoader.load(getClass().getResource("menuitemtemplate.fxml"));
		Text title = (Text)root.lookup("#Title");
		ScrollPane pane = (ScrollPane) root.lookup("#Addons1");
		List<CheckBox> checkBoxes = pane.lookupAll(".checkbox")
				.stream()
		        .map(node -> (CheckBox) node)
		        .collect(Collectors.toList());
		HashMap<String, Object> 
		for (CheckBox checkBox : checkBoxes) {
		    if (checkBox.isSelected()) {
		        // the CheckBox is checked
		    	for (Map<String, Object> entry : Main.ADDONS) {
	        		if (checkBox.getText() == (entry.get("Name") + " ($" + entry.get("Cost") + ")")) {
			    		
			    	}
		        }
		    } else {
		        // the CheckBox is not checked
		    	checkBoxes.remove(checkBox);
		    }
		}
		HashMap<String, Object> data = null;
        for (Map<String, Object> entry : Main.ITEMS) {
        	if (entry.get("Name") == title) {
        		data = (HashMap<String, Object>) entry;
        	}
        }
        System.out.println(pane.getChildrenUnmodifiable());
        */
		
		toMain(event);
	}
}
