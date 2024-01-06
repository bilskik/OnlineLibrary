package pl.bilskik.viewmodel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Stage;
import org.json.JSONObject;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;

public class RegisterController {

    private final static String URL = "http://localhost:8080";

    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;

    private Auth auth;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public RegisterController() {
        DIContainer di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
    }

    public void onLoginHandle(ActionEvent event) throws URISyntaxException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginField.getText());
        jsonObject.put("password", passwordField.getText());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(res.body());
        jwtResolver(event, res);
    }

    public void onRegisterHandle(ActionEvent event) throws URISyntaxException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginField.getText());
        jsonObject.put("password", passwordField.getText());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .build();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        jwtResolver(event, res);
    }

    private void jwtResolver(ActionEvent event, HttpResponse<String> res) throws IOException {
        JSONObject json = new JSONObject(res.body());
        String jwt = json.getString("jwt");
        if(jwt == null || jwt.equals("")) {
            throw new IllegalAccessError("No Access to this resources!");
        } else {
            auth.setJwt(jwt);
            switchScene(event);
        }
    }

    private void switchScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
