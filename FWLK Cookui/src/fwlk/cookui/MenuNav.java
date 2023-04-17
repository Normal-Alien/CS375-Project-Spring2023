/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fwlk.cookui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author breeze
 */
public class MenuNav {
    private Stage stage;
	private Scene scene;
	private Parent root;    
        @FXML
        
        private javafx.scene.layout.VBox orderBox;
        @FXML
	private void refresh(ActionEvent event) throws IOException{
		System.out.println("refreshed!");
                orderBox.getChildren().clear();
                for(int i = 0; i < 10; i++){
                            
                            javafx.scene.control.Button button = new javafx.scene.control.Button("Button " + i);
                            button.setOnAction(orderPress -> System.out.println(button.getText()));
                            orderBox.getChildren().add(button);
                        }
                /*
                root = FXMLLoader.load(getClass().getResource("CookuiV0.1.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
                */
                String name = event.getSource().toString();
                System.out.println(name);
	}
}
