package pl.bilskik.viewmodel.service;

import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpService {

    private Auth auth;

    public HttpService() {
        DI di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
    }

    public HttpResponse<String> createGetRequest(URI uri) {
        HttpRequest request = reqBuilder(false, "GET", uri, null);
        return sendRequest(request);
    }

    public HttpResponse<String> createGETRequestWithJwt(URI uri) {
        HttpRequest request = reqBuilder(true, "GET", uri, null);
        return sendRequest(request);
    }
    public HttpResponse<String> createPOSTRequestWithJwt(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        HttpRequest request = reqBuilder(true, "POST", uri, bodyPublisher);
        return sendRequest(request);
    }
    public HttpResponse<String> createPUTRequestWithJwt(URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        HttpRequest request = reqBuilder(true, "PUT", uri, bodyPublisher);
        return sendRequest(request);
    }

    public HttpResponse<String> createDELETERequestWithJwt(URI uri) {
        HttpRequest request = reqBuilder(true, "DELETE", uri, null);
        return sendRequest(request);
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private HttpRequest reqBuilder(boolean isJWT, String method,  URI uri, HttpRequest.BodyPublisher bodyPublisher) {
        if(method.equals("GET")) {
            if(isJWT) {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + auth.getJwt())
                        .GET()
                        .build();
            } else {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();
            }
        }
        else if(method.equals("POST")) {
            if(isJWT) {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + auth.getJwt())
                        .POST(bodyPublisher)
                        .build();
            } else {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .POST(bodyPublisher)
                        .build();
            }
        }
        else if(method.equals("PUT")) {
            if(isJWT) {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + auth.getJwt())
                        .PUT(bodyPublisher)
                        .build();
            } else {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .PUT(bodyPublisher)
                        .build();
            }
        } else if(method.equals("DELETE")) {
            if(isJWT) {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + auth.getJwt())
                        .DELETE()
                        .build();
            } else {
                return HttpRequest.newBuilder()
                        .uri(uri)
                        .header("Content-Type", "application/json")
                        .DELETE()
                        .build();
            }

        } else {
            throw new RuntimeException("Method not supported!");
        }
    }



}
