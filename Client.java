package client;

import okhttp3.*;
import java.io.IOException;

/*

Adapated from:
https://mkyong.com/java/how-to-send-http-request-getpost-in-java/

*/

public class Client {

    private final OkHttpClient httpClient = new OkHttpClient();

    public void sendRequest(String url, String json, String command) {
        Client obj = new Client();
        if(!(command.equals("POST"))|!(command.equals("GET"))) {
            System.out.println("command must be either 'POST' or 'GET'");
        } else {
            if(command.equals("GET")) {
                obj.sendGet(url); //json not included since GET requests dont use json data blocks
            } else if(command.equals("POST")) {
                obj.sendPost(url, json);
            }
        }
    }

    private void sendGet(String url) throws Exception {

        Request request = new Request.builder();
            .url(url)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("GET Request failure.\nFailed Code: " + response);

            System.out.println(response.body().string());
        }
    }

    private void sendPost(String url, String json) throws Exception {

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
        }
    }
}