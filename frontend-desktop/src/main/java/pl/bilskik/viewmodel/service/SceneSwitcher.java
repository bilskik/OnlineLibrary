package pl.bilskik.viewmodel.service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.viewmodel.service.TableService;

import java.io.IOException;
import java.net.URISyntaxException;

public class SceneSwitcher {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public TableService tableService;

    public void switchScene(ActionEvent event, String view) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource(view));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
