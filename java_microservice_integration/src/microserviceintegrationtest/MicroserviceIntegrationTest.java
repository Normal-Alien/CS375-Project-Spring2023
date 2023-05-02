
package microserviceintegrationtest;

import microserviceMethods.Client;
import okhttp3.*;
import java.io.IOException;
import java.util.Scanner;

public class MicroserviceIntegrationTest {
    
    public static void main(String[] args) throws IOException, Exception {
        Scanner read = new Scanner(System.in);
        Client test = new Client();
        String url = "http://localhost:8080/database/methods/add_entries";
        /*
        String json = "{\"data\":{\"table\":{\"tblName\":\"Item\",\"tblData\":[\"0\",\"'example'\",\"1\",\"10101\",\"8\",\"True\",\"'[1,2]'\",\"True\"]}}}";
        String item2 = "{\"data\":{\"table\":{\"tblName\":\"Item\",\"tblData\":[\"1\",\"'test'\",\"2\",\"00111\",\"8\",\"True\",\"'[0,1]'\",\"True\"]}}}";
        String addon1 = "{\"data\":{\"table\":{\"tblName\":\"Addon\",\"tblData\":[\"0\",\"'add'\",\"3.00\"]}}}";
        String addon2 = "{\"data\":{\"table\":{\"tblName\":\"Addon\",\"tblData\":[\"1\",\"'sub'\",\"5.00\"]}}}";
        String addon3 = "{\"data\":{\"table\":{\"tblName\":\"Addon\",\"tblData\":[\"2\",\"'idk'\",\"1.50\"]}}}";
        String order_json = "{\"data\":{\"table\":{\"tblName\":\"Orders\",\"tblData\":[\"0\",\"100.00\",\"50.00\",\"True\"]}}}";
        String order_json_2 = "{\"data\":{\"table\":{\"tblName\":\"Orders\",\"tblData\":[\"1\",\"75.00\",\"70.00\",\"True\"]}}}";
        String order_json_3 = "{\"data\":{\"table\":{\"tblName\":\"Orders\",\"tblData\":[\"2\",\"50.00\",\"30.00\",\"False\"]}}}";
        String order_json_4 = "{\"data\":{\"table\":{\"tblName\":\"Order_Items\",\"tblData\":[\"0\",\"0\"]}}}";
        String order_json_5 = "{\"data\":{\"table\":{\"tblName\":\"Order_Items\",\"tblData\":[\"0\",\"1\"]}}}";
        String order_addon = "{\"data\":{\"table\":{\"tblName\":\"Order_Addons\",\"tblData\":[\"0\",\"0\",\"1\"]}}}";
        String order_addon_2 = "{\"data\":{\"table\":{\"tblName\":\"Order_Addons\",\"tblData\":[\"0\",\"1\",\"0\"]}}}";
        */
        String json = "{\"data\":["
                + "{\"Item\":[\"0\",\"'example'\",\"1\",\"10101\",\"8\",\"True\",\"'[1,2]'\",\"True\"]},"
                + "{\"Item\":[\"1\",\"'test'\",\"2\",\"00111\",\"8\",\"True\",\"'[0,1]'\",\"True\"]},"
                + "{\"Item\":[\"2\",\"'experiment'\",\"2\",\"01111\",\"8\",\"True\",\"'[1,3]'\",\"True\"]},"
                + "{\"Addon\":[\"0\",\"'add'\",\"1\",\"3.00\"]},"
                + "{\"Addon\":[\"1\",\"'sub'\",\"0\",\"5.00\"]},"
                + "{\"Addon\":[\"2\",\"'idk'\",\"0\",\"1.50\"]},"
                + "{\"Addon\":[\"3\",\"'div'\",\"2\",\"1.00\"]},"
                + "{\"Orders\":[\"0\",\"100.00\",\"50.00\",\"True\"]},"
                + "{\"Orders\":[\"1\",\"75.00\",\"70.00\",\"True\"]},"
                + "{\"Orders\":[\"2\",\"50.00\",\"30.00\",\"False\"]},"
                + "{\"Order_Items\":[\"0\",\"0\"]},"
                + "{\"Order_Items\":[\"0\",\"1\"]},"
                + "{\"Order_Items\":[\"1\",\"1\"]},"
                + "{\"Order_Items\":[\"2\",\"2\"]},"
                + "{\"Order_Addons\":[\"0\",\"0\",\"1\"]},"
                + "{\"Order_Addons\":[\"0\",\"1\",\"0\"]}"
                + "]}";
        String json2 = "{\"data\":["
                + "{\"Orders\":[\"0\",\"100.00\",\"50.00\",\"True\"]},"
                + "{\"Orders\":[\"1\",\"75.00\",\"70.00\",\"True\"]},"
                + "{\"Orders\":[\"2\",\"50.00\",\"30.00\",\"False\"]},"
                + "{\"Order_Items\":[\"0\",\"0\"]},"
                + "{\"Order_Items\":[\"0\",\"1\"]},"
                + "{\"Order_Items\":[\"1\",\"1\"]},"
                + "{\"Order_Items\":[\"2\",\"2\"]},"
                + "{\"Order_Addons\":[\"0\",\"0\",\"1\"]},"
                + "{\"Order_Addons\":[\"0\",\"1\",\"0\"]}"
                + "]}";
        String method = "POST";
        System.out.println("add entries or test java get? (add/get): ");
        String choice = read.nextLine();
        System.out.println("all or orders? (all/orders):");
        String choice2 = read.nextLine();
        if(choice.equals("add")) {
            /*System.out.println("Which entry should be added? (0/1/2/3?): ");
            String inp = read.nextLine();
            if(inp.equals("0")) {
            } else if(inp.equals("1")) {
                json = order_json;
            } else if(inp.equals("2")) {
                json = order_json_2;
            } else if(inp.equals("3")) {
                json = order_json_3;
            }*/
            if(choice2.equals("orders")) {
                System.out.println("only orders");
                json = json2;
            }
            try(Response response = test.sendRequest(url, json, method)) {  
                if(!(response.isSuccessful())) throw new IOException("Request failure.\nFailed code: " + response);
                System.out.println(response.body().string());
            }
        } else if(choice.equals("get")) {
            url = "http://0.0.0.0:8080/database/methods/fetch_active_orders";
            method = "GET";
            try(Response response = test.sendRequest(url, "", method)) {
                if(!(response.isSuccessful())) throw new IOException("Test failure.\nFailed code: " + response);
                System.out.println(response.body().string());
            }
        }
    }
}