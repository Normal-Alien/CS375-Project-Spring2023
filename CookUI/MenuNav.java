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
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author breeze
 */
public class MenuNav {
    private Boolean isLive = false;
    private String ip = "172.25.38.184:8080";
    private Boolean mapVer = false;
    private String sampleMap = "{\"0\":[[0,100.0,50.0,1],[[0,0],[0,1]],[[0,0,1],[0,1,0]]],\"1\":[[1,75.0,70.0,1],[],[]]}";
    private String sampleArray = "[[0, 100.0, 50.0,1], [1, 75.0, 70.0,1], [2, 50.0, 30.0,0]]";
    private ClientCook client = new ClientCook();
    private Stage stage;
	private Scene scene;
	private Parent root; 
        @FXML
        private javafx.scene.layout.VBox orderBox;
        
        private void remove(javafx.scene.control.Button source){
            //clear from orderItems
            //clear from orders
            
            String id = source.getText();
            if(mapVer){
               //id = id.split(" ")[0]; 
            }
            else{
                //removes the starting opening bracket, then gets the first element
                //assumes the first element of the array is the id 
                //(not not an array containing the id)
                id = id.split("\\[")[1];
                id = id.split(",")[0];
            }
            
            //obj.put("ID", id);
            
            
            System.out.println("removing order: " + id);
            orderBox.getChildren().remove(source);
            if(isLive){
                
               String ret = removeOrder(id);
               System.out.println("microservice call returned: " + ret);
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
            String ret = "no return recieved";
            try{
                String cmdURL = "http://" + ip + "/database/methods/fetch_active_orders";
                System.out.println(cmdURL);
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
                JSONParser parser = new JSONParser();
                String data = "";
                if(isLive){
                    

                    //get list of active orders from the database
                    String response = getOrders();
                    if(response.equals("no return recieved")){
                        System.out.println(response);
                    }
                    else{
                        System.out.println("response successful... ");
                        
                        data = response;
                        System.out.println(data);
                    }
                }
                else{
                    
                //create buttons using sample data
                
                }
                    try {
                        if(mapVer){
                            data = sampleMap;
                            ContainerFactory containerFactory = new ContainerFactory() {
                                @Override
                                public Map createObjectContainer() {
                                    return new LinkedHashMap<>();
                                }
                                @Override
                                public List creatArrayContainer() {
                                   return new LinkedList<>();
                                }
                            };
                            Map map = (Map)parser.parse(data, containerFactory);       
                            Set<String> keySet = map.keySet();
                            String[] keys = new String[keySet.size()];
                            keySet.toArray(keys);

                            for(int i = 0; i < keys.length; i++){
                                String key = keys[i];
                                //in future would parse to get string labels,
                                //currently just int ids
                                String label = key + " " + map.get(keys[i]);
                                javafx.scene.control.Button button = new javafx.scene.control.Button(label);
                                button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                                orderBox.getChildren().add(button);
                            }
                        }
                        else{
                            data = sampleArray;                        
                            Object obj = JSONValue.parse(data);       
                            JSONArray arr = (JSONArray) obj;
                            for(int i = 0; i < arr.size(); i++){
                                String label = arr.get(i).toString();
                                javafx.scene.control.Button button = new javafx.scene.control.Button(label);
                                button.setOnAction(orderPress -> remove((javafx.scene.control.Button) orderPress.getSource()));
                                orderBox.getChildren().add(button);
                            }
                        }
                    } catch(ParseException pe) {
                        System.out.println("idx: " + pe.getPosition());
                        System.out.println(pe);
                    }
                System.out.println("refreshed!");
	}
}
