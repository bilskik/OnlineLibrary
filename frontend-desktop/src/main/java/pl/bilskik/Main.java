package pl.bilskik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;

public class Main extends Application {
    private final static String TITLE = "Weather App";

    public static void main(String[] args) {
        DI di = DIContainer.getInstance();
        di.register(Auth.class, new Auth());
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane mainPane = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();

    }
}