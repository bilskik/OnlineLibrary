package pl.bilskik.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherController {

    @FXML
    public TextField locationField;
    @FXML
    public Text cityId;
    @FXML
    public TextArea textArea;
    @FXML
    public TextField loginField;

    public WeatherController() throws IOException {
        DIContainer di = DIContainer.getInstance();
    }

    private HttpResponse<String> createGetRequest(URI uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
