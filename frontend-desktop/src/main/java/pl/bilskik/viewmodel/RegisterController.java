package pl.bilskik.viewmodel;

import com.google.gson.JsonArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javafx.stage.Stage;
import org.json.JSONObject;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;
import pl.bilskik.viewmodel.service.SceneSwitcher;

public class RegisterController {

    private final static String URL = "http://localhost:8080";

    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public Label errLogin;

    private Auth auth;
    private SceneSwitcher sceneSwitcher;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public RegisterController() {
        DIContainer di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
        sceneSwitcher = di.resolve(SceneSwitcher.class);
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
        JSONObject json = null;
        try {
            json = new JSONObject(res.body());
        } catch(Exception e) {
            errLogin.setText("Bad credentials!");
            e.printStackTrace();
        }
        if (json != null) {
            String jwt = json.getString("jwt");
            if(jwt == null || jwt.equals("")) {
                errLogin.setText("Bad credentials!");
                throw new IllegalAccessError("No Access to this resources!");
            } else {
                errLogin.setText("");
                auth.setJwt(jwt);
                sceneSwitcher.switchScene(event, "MainView.fxml");
            }
        } else {
            errLogin.setText("Bad credentials!");
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
