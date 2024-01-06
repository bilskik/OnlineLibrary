package pl.bilskik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;
import pl.bilskik.viewmodel.SceneSwitcher;
import pl.bilskik.viewmodel.service.HttpService;
import pl.bilskik.viewmodel.service.TableService;

public class Main extends Application {
    private final static String TITLE = "Online library";

    public static void main(String[] args) {
        DI di = DIContainer.getInstance();
        di.register(Auth.class, new Auth());
        di.register(HttpService.class, new HttpService());
        di.register(TableService.class, new TableService());
        di.register(SceneSwitcher.class, new SceneSwitcher());
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