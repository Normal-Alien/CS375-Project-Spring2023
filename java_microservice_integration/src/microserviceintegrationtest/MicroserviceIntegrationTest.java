
package microserviceintegrationtest;

import microserviceMethods.Client;
import okhttp3.*;
import java.io.IOException;

public class MicroserviceIntegrationTest {
    
    public static void main(String[] args) throws IOException, Exception {
        
        Client test = new Client();
        String url = "http://127.0.0.1:5000/database/methods/add_item_entry";
        String json = "{\"data\": {\"name\":\"'example'\", \"cost\":\"8\", \"store\":\"'grill'\", \"pic\":\"'(PICTURE STORED AS STRING)'\", \"taxable\":\"True\"}}";
        String method = "POST";
        try(Response response = test.sendRequest(url, json, method)) {
            if(!(response.isSuccessful())) throw new IOException("Request failure.\nFailed code: " + response);
            System.out.println(response.body().string());
        }
    }
}