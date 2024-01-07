package pl.bilskik.viewmodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONObject;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Auth;
import pl.bilskik.model.Book;
import pl.bilskik.viewmodel.service.HttpService;
import pl.bilskik.viewmodel.service.SceneSwitcher;
import pl.bilskik.viewmodel.service.TableService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LibraryController {

    @FXML
    public Button logout;
    @FXML
    public TableView tableView;
    @FXML
    public Button addNewBookBtn;
    @FXML
    public TextField bookNameInput;
    @FXML
    public TextField authorNameInput;
    @FXML
    public TableColumn<Book, String> authorCol;
    @FXML
    public TableColumn<Book, String> nameCol;
    @FXML
    public TableColumn<Book, Void> editBtnCol;
    @FXML
    public TableColumn<Book, Void> deleteBtnCol;

    private final static String serverUrl = "http://localhost:8080";

    ObservableList<Book> data;

    private Auth auth;
    private SceneSwitcher sceneSwitcher;
    private TableService tableService;
    private HttpService httpService;

    public LibraryController() {
        DI di = DIContainer.getInstance();
        auth = di.resolve(Auth.class);
        httpService = di.resolve(HttpService.class);
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
            data = FXCollections.observableArrayList(bookList);

            authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            authorCol.setStyle("-fx-alignment: CENTER;");

            nameCol.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            nameCol.setStyle("-fx-alignment: CENTER;");

            Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactoryEdit = tableService.getEditCellFactory();
            Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactoryDelete = tableService.getDeleteCellFactory(data);

            editBtnCol.setCellFactory(cellFactoryEdit);
            deleteBtnCol.setCellFactory(cellFactoryDelete);

            tableView.setItems(data);
        }
    }

    public void onHandleAddNewBook(ActionEvent event) throws URISyntaxException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject();
        String name = bookNameInput.getText();
        String author = authorNameInput.getText();
        jsonObject.put("name", name);
        jsonObject.put("author", author);
        HttpResponse<String> res = null;
        try {
            res = httpService.createPOSTRequestWithJwt(new URI(serverUrl + "/book"),
                    HttpRequest.BodyPublishers.ofString(jsonObject.toString()));
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(res != null && res.statusCode() == 201) {
            List<Book> bookList = tableService.launchTableData();
            for(var b : bookList) {
                if(b.getName().equals(name) && b.getAuthor().equals(author)) {
                    data.add(b);
                }
            }
        }
    }

    public void onLogoutHandle(ActionEvent event) throws IOException {
        auth.setJwt("");
        sceneSwitcher.switchScene(event, "LoginView.fxml");
    }

}
