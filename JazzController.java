package application;

import java.io.IOException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
 
public class JazzController {
	@FXML //tag to be used for every item that has to be used with the fxml file
	private Stage stage;
	private Scene scene;
	private Parent root;
	public ScrollPane JazzPane;
	

	
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
	private void toJazzFood(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("jazzFood.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void toItem(ActionEvent event) throws IOException{
		Button button = (Button) event.getSource();
		String name = button.getText();
		root = FXMLLoader.load(getClass().getResource("menuitemtemplate.fxml"));
		Text title = (Text)root.lookup("#Title");
		title.setText(name);
		ScrollPane pane = (ScrollPane) root.lookup("#Addons1");
		VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        
        HashMap<String, Object> data = null;
        for (Map<String, Object> entry : Main.ITEMS) {
        	if (entry.get("Name") == name) {
        		data = (HashMap<String, Object>) entry;
        	}
        }
        List<Integer> add = (List<Integer>) data.get("Addons");
        for (Integer idx : add) {
        	HashMap<String, Object> addon = (HashMap<String, Object>) Main.ADDONS.get(idx);
        	String n = (String) addon.get("Name");
        	Double c = (Double) addon.get("Cost");
        	CheckBox box = new CheckBox(n + " ($" + c + ")");
        	box.setId(""+idx);
        	vbox.getChildren().add(box);	
        }
        
        pane.setContent(vbox);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void toMenuItem(ActionEvent event) throws IOException{
		Button button = (Button) event.getSource();
		String buttonId = button.getId();
		HashMap<String,Object> data = Main.getItem(buttonId);
		System.out.println(data);
		root = FXMLLoader.load(getClass().getResource("menuitemtemplate.fxml"));
		Text title = (Text)root.lookup("#Title");
		title.setText(buttonId);
		/*
		System.out.println(root);
		SplitPane a = (SplitPane) root.lookup("#A");
		System.out.println(a);
		AnchorPane b = (AnchorPane) a.lookup("#B"); //this gets set to null for some reason
		System.out.println(b);
		ScrollPane pane = (ScrollPane) b.lookup("#Addons");
		*/
		VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
		//System.out.print(data);
        HashMap<String, Double> addons = (HashMap<String, Double>) data.get("Addons");
		for (Map.Entry<String, Double> entry : addons.entrySet()) {
		    //System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
            String addon = entry.getKey();
            Double cost = entry.getValue();
            CheckBox box = new CheckBox(addon + " ($" + cost + ")");
            //box.setOnAction(event -> System.out.println("Button " + addon + " clicked"));
            vbox.getChildren().add(box);
        }
		ItemController.Addons1.setContent(vbox);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		//stage.hide();
		//System.out.println("hi");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

}
