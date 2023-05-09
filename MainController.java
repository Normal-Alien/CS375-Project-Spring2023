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
 
public class MainController {
	// @FXML tag to be used for every item that has to be used with the fxml file
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML 
	public ScrollPane Addons1;
	
	/* Method names can be changed according to the function
	 * WARNING: MUST ALSO CHANGE THE REFERENCED NAMES THROUGH SCENEBUILDER OR THE 
	 * FXML FILE ITSELF
	 */
	
	@FXML
	private void toTresEntrees(ActionEvent event) throws IOException{
		TresController t = new TresController();
		root = FXMLLoader.load(getClass().getResource("tresEntrees.fxml"));
		ScrollPane TresPane = (ScrollPane) root.lookup("#TresPane");
		VBox tbox = new VBox();
        tbox.setPadding(new Insets(10));
        tbox.setSpacing(10);
		for (Map<String, Object> entry : Main.ITEMS) {
			Button button = new Button((String)entry.get("Name"));
			button.setOnAction(event1 -> {
				try {
					t.toItem(event1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			int store = (int) entry.get("Store");
			if(store == 0) {
				tbox.getChildren().add(button);
			}
		}
		TresPane.setContent(tbox);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toSubEntrees(ActionEvent event) throws IOException{
		SubController s = new SubController();
		root = FXMLLoader.load(getClass().getResource("subEntrees.fxml"));
		ScrollPane SubPane = (ScrollPane) root.lookup("#SubPane");
		VBox sbox = new VBox();
        sbox.setPadding(new Insets(10));
        sbox.setSpacing(10);
		for (Map<String, Object> entry : Main.ITEMS) {
			Button button = new Button((String)entry.get("Name"));
			button.setOnAction(event1 -> {
				try {
					s.toItem(event1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			int store = (int) entry.get("Store");
			if(store == 1) {
				sbox.getChildren().add(button);
			}
		}
		SubPane.setContent(sbox);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toGrillEntrees(ActionEvent event) throws IOException{
		GrillController g = new GrillController();
		root = FXMLLoader.load(getClass().getResource("grillentrees.fxml"));
		ScrollPane GrillPane = (ScrollPane) root.lookup("#GrillPane");
		VBox tbox = new VBox();
        tbox.setPadding(new Insets(10));
        tbox.setSpacing(10);
		for (Map<String, Object> entry : Main.ITEMS) {
			Button button = new Button((String)entry.get("Name"));
			button.setOnAction(event1 -> {
				try {
					g.toItem(event1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			int store = (int) entry.get("Store");
			if(store == 2) {
				tbox.getChildren().add(button);
			}
		}
		GrillPane.setContent(tbox);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void toJazzFood(ActionEvent event) throws IOException{
		JazzController j = new JazzController();
		root = FXMLLoader.load(getClass().getResource("jazzFood.fxml"));
		ScrollPane JazzPane = (ScrollPane) root.lookup("#JazzPane");
		VBox tbox = new VBox();
        tbox.setPadding(new Insets(10));
        tbox.setSpacing(10);
		for (Map<String, Object> entry : Main.ITEMS) {
			Button button = new Button((String)entry.get("Name"));
			button.setOnAction(event1 -> {
				try {
					j.toItem(event1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			int store = (int) entry.get("Store");
			if(store == 3) {
				tbox.getChildren().add(button);
			}
		}
		JazzPane.setContent(tbox);
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
		Addons1.setContent(vbox);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		//stage.hide();
		//System.out.println("hi");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
