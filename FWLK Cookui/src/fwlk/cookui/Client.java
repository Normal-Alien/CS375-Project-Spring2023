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

public class Client {

    private final OkHttpClient httpClient = new OkHttpClient();

    public Response sendRequest(String url, String json, String command) {
        Client obj = new Client();
        if(!(command.equals("POST"))|!(command.equals("GET"))) {
            System.out.println("command must be either 'POST' or 'GET'");
        } else {
            Response response = null;
            if(command.equals("GET")) {
                response = obj.sendGet(url); //json not included since GET requests dont use json data blocks
            } else if(command.equals("POST")) {
                response = obj.sendPost(url, json);
            }
            return response;
        }
    }

    private Response sendGet(String url) throws Exception {

        Request request = new Request.builder();
            .url(url)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("GET Request failure.\nFailed Code: " + response);

            System.out.println(response.body().string());
            return response;
        } 
                
                
        
    }

    private Response sendPost(String url, String json) throws Exception {

        RequestBody formbody = new FormBody.Builder()
            .add(json)
            .build();

        Request request = new Request.Builder()
            .url(url)
            .post(formBody)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("POST Request failure.\nFailed code: " + response);

            System.out.println(response.body().string());
            return response;
        }
    }
}