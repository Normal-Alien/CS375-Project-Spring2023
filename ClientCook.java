/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fwlk.cookui;

//package client;

import okhttp3.*;
import java.io.IOException;

/*

Adapated from:
https://mkyong.com/java/how-to-send-http-request-getpost-in-java/

Actual testing file can be found in '/java_microservice_integration/src/microserviceintegrationtest/MicroserviceIntegrationTest.java'

*/

public class ClientCook {

    private final OkHttpClient httpClient = new OkHttpClient();

    public String sendRequest(String iurl, String json, String command) throws Exception {
        ClientCook obj = new ClientCook();
        if(!(command.equals("POST"))&&!(command.equals("GET"))) {
            System.out.println("command must be either 'POST' or 'GET'");
        } else {
            if(command.equals("GET")) {
                String response = obj.sendGet(iurl); //json not included since GET requests dont use json data blocks
                return response;
            } else if(command.equals("POST")) {
                String response = obj.sendPost(iurl, json);
                
                return response;
            }
        }
        return null;
    }

    String sendGet(String iurl) throws Exception {

        Request request = new Request.Builder()
            .url(iurl)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("GET Request failure.\nFailed Code: " + response);

            //System.out.println(response.body().string());
            
            return response.body().string();
        }
    }

    String sendPost(String url, String json) throws Exception {

        RequestBody body = RequestBody.create(
            MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("POST Request failure.\nFailed code: " + response);

            //System.out.println(response.body().string());
            return response.body().string();
        }
    }
}
