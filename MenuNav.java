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
    private Boolean isLive = true;
    private String ip = "172.25.37.136:5000";
    private ClientCook client = new ClientCook();
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
            if(isLive){
                
               String ret = removeOrder(id);
                
            }
        }
        private String removeOrder(String id){
            String ret = "empty";
            try{
                String cmdURL = "http://" + ip + "/database/methods/rm_order/" + id;
                ret = client.sendRequest(cmdURL, "", "GET");
            }
            catch(Exception e){
                System.out.println(e);
            }
            return ret;
        }
	private String getOrders(){
            String ret = "empty";
            try{
                String cmdURL = "http://" + ip + "/database/methods/fetch_active_orders";
                ret = client.sendRequest(cmdURL, "", "GET");
            }
            catch(Exception e){
                System.out.println(e);
            }
            return ret;
        }
        @FXML
        private void refresh(ActionEvent event) throws IOException{
		System.out.println("refreshing...");
                orderBox.getChildren().clear();
                if(isLive){
                    

                    //get list of active orders from the database
                    String response = getOrders();
                        System.out.println("tryed a response, got it");
                        //if(!(response.isSuccessful())) throw new IOException("Request failure.\nFailed code: " + response);
                        System.out.println("response successful...");
                        
                        String data = response;
                        System.out.println(data);
                        System.out.print("parsing to obj...");
                        Object obj = JSONValue.parse(data);
                        System.out.println("parsed to obj");

                        JSONArray arr = (JSONArray) obj;
                        System.out.println("toArray");
                        Iterator it = arr.iterator();
                        System.out.println("starting iteration");
                        while(it.hasNext()){
                            
                            JSONObject curr = (JSONObject) it.next();
                            System.out.println(curr);
                            String s = (String) curr.get("ID");
                            //add item details later if able
                            javafx.scene.control.Button button = new javafx.scene.control.Button(s);

                            button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                            orderBox.getChildren().add(button);
                        }
                    }
                    /*
                    catch(Exception e){
                        System.out.println(e);
                    }
                    */
                
                
                else{
                    //temp dummy buttons
                    for(int i = 0; i < 10; i++){
                        javafx.scene.control.Button button = new javafx.scene.control.Button("Button" + i);
                        button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                        orderBox.getChildren().add(button);
                    }
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
