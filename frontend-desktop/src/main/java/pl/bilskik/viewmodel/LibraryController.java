package pl.bilskik.viewmodel;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;
import pl.bilskik.model.Book;
import pl.bilskik.viewmodel.service.TableService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LibraryController {

    @FXML
    public Button logout;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<Book, String> authorCol;
    @FXML
    public TableColumn<Book, String> nameCol;
    @FXML
    public TableColumn<Book, Void> editBtnCol;
    @FXML
    public TableColumn<Book, Void> deleteBtnCol;


    private Auth auth;
    private SceneSwitcher sceneSwitcher;
    private TableService tableService;

    public LibraryController() {
        DI di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
        tableService = di.resolve(TableService.class);
        sceneSwitcher = di.resolve(SceneSwitcher.class);
    }

    @FXML
    public void initialize() {
        List<Book> bookList = null;
        try {
            bookList = tableService.launchTableData();
        } catch(Exception e) {
            e.printStackTrace();
        }
        tableView.setEditable(true);
        if(bookList != null) {
            final ObservableList<Book> data = FXCollections.observableArrayList(bookList);

            authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            nameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

            Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactoryEdit = tableService.getEditCellFactory();
            Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactoryDelete = tableService.getDeleteCellFactory(data);

            editBtnCol.setCellFactory(cellFactoryEdit);
            deleteBtnCol.setCellFactory(cellFactoryDelete);

            tableView.setItems(data);
        }
    }


    public void onLogoutHandle(ActionEvent event) throws IOException {
        auth.setJwt("");
        sceneSwitcher.switchScene(event, "LoginView.fxml");
    }
}
