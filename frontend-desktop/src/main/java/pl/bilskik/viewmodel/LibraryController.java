package pl.bilskik.viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;

public class LibraryController {

    @FXML
    public Button logout;

    public Auth auth;


    public LibraryController() {
        DI di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
    }

    public void onLogoutHandle(ActionEvent e) {
        auth.setJwt("");

    }
}
