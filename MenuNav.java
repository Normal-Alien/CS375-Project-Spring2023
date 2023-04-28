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
import org.json.simple.*;
import okhttp3.*;
import java.util.*;

/**
 *
 * @author breeze
 */
public class MenuNav {
    private String ip = "";
    private Client client = new Client();
    private Stage stage;
	private Scene scene;
	private Parent root; 
        @FXML
        private javafx.scene.layout.VBox orderBox;
        
        private void remove(javafx.scene.control.Button source){
            //clear from orderItems
            //clear from orders
            
            //testing JSON library
            JSONObject obj = new JSONObject();
            String id = source.getText();
            id = id.split(" ")[0];
            //obj.put("ID", id);
            
            
            System.out.println(id);
            orderBox.getChildren().remove(source);
            //likely need to change url
            String cmdURL = "http://172.25.36.95:5000/database/methods/rm_order/" + id;
            try(Response response = (Response) client.sendRequest(cmdURL, "", "GET")) {  
            if(!(response.isSuccessful())) throw new IOException("Request failure.\nFailed code: " + response);
                System.out.println(response.body().string());
                return;
            }
        }
        @FXML
	
        private void refresh(ActionEvent event) throws IOException{
		System.out.println("refreshing...");
                orderBox.getChildren().clear();
                String cmdURL = "http://172.25.36.95:5000/database/methods/fetch_active_orders";
                
                //get list of active orders from the database
                try(Response response = (Response) client.sendRequest(cmdURL, "", "GET")) {  
                if(!(response.isSuccessful())) throw new IOException("Request failure.\nFailed code: " + response);
                    System.out.println(response.body().string());
                    String data = response.body().string();
                    Object obj = JSONValue.parse(data);
                
                    JSONArray arr = (JSONArray) obj;
                    Iterator it = arr.iterator();
                    while(it.hasNext()){
                    
                        JSONObject curr = (JSONObject) it.next();
                        String s = (String) curr.get("ID");
                        //add item details later if able
                        javafx.scene.control.Button button = new javafx.scene.control.Button(s);
                    
                        button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                        orderBox.getChildren().add(button);
                    }
                }
                
                
                //temp dummy buttons
                for(int i = 0; i < 10; i++){
                            
                            javafx.scene.control.Button button = new javafx.scene.control.Button("Button" + i);
                            button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                            orderBox.getChildren().add(button);
                        }
                /*
                root = FXMLLoader.load(getClass().getResource("CookuiV0.1.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
                */
                //String name = event.getSource().toString();
                //System.out.println(name);
                System.out.println("refreshed!");
	}
}
