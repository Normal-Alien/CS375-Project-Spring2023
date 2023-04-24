package microserviceMethods;

import okhttp3.*;
import java.io.IOException;

public class Client {

    private final OkHttpClient httpClient = new OkHttpClient();

    public Response sendRequest(String iurl, String json, String command) throws Exception {
        Client obj = new Client();
        if(!(command.equals("POST"))&&!(command.equals("GET"))) {
            System.out.println("command must be either 'POST' or 'GET'");
        } else {
            if(command.equals("GET")) {
                Response response = obj.sendGet(iurl); //json not included since GET requests dont use json data blocks
                return response;
            } else if(command.equals("POST")) {
                Response response = obj.sendPost(iurl, json);
                return response;
            }
        }
        return null;
    }

    private Response sendGet(String iurl) throws Exception {

        Request request = new Request.Builder()
            .url(iurl)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("GET Request failure.\nFailed Code: " + response);

            System.out.println(response.body().string());
            return response;
        }
    }

    private Response sendPost(String url, String json) throws Exception {

        RequestBody body = RequestBody.create(
            MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if(!(response.isSuccessful())) throw new IOException("POST Request failure.\nFailed code: " + response);

            System.out.println(response.body().string());
            return response;
        }
    }
}