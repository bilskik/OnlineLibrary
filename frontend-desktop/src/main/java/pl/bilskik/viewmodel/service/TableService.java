package pl.bilskik.viewmodel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.json.JSONObject;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Book;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TableService {

    private final static String serverUrl = "http://localhost:8080";

    private final HttpService httpService;



    public TableService() {
        DI di = DIContainer.getInstance();
        httpService = di.resolve(HttpService.class);
    }

    public List<Book> launchTableData() throws URISyntaxException, JsonProcessingException {
        HttpResponse<String> res = httpService.createGETRequestWithJwt(new URI(serverUrl + "/book"));

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Book>> listType = new TypeReference<List<Book>>() {};

        List<Book> bookList = objectMapper.readValue(res.body(), listType);

        return bookList;
    }

    public Callback<TableColumn<Book, Void>, TableCell<Book, Void>> getEditCellFactory() {
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory =
                new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {

                    @Override
                    public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                        final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                            private final Button btn = new Button("Edit");
                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    Book book = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + book.getBookId() + " name " + book.getName());
                                });
                            }
                            {
                                btn.setStyle("-fx-background-color: #F78F28; -fx-text-fill: #fff; -fx-font-weight: bold");
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                        cell.setAlignment(Pos.CENTER);
                        return cell;
                    }
                };
        return cellFactory;
    }

    public Callback<TableColumn<Book, Void>, TableCell<Book, Void>> getDeleteCellFactory(ObservableList<Book> data) {
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory =
                new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {

                    @Override
                    public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                        final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                            private final Button btn = new Button("Delete");

                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    Book book = getTableView().getItems().get(getIndex());
                                    boolean isDeleted = deleteBookOnServer(book);
                                    data.remove(book);
                                    System.out.println("selectedData: " + book.getBookId() + " name " + book.getName());
                                });
                            }
                            {
                                btn.setStyle("-fx-background-color: #F78F28; -fx-text-fill: #fff; -fx-font-weight: bold");
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                        cell.setAlignment(Pos.CENTER);
                        return cell;
                    }
                };
        return cellFactory;
    }

    private boolean updateBookOnServer(Book book) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", book.getName());
        jsonObject.put("author", book.getAuthor());
        HttpResponse<String> res = null;
        try {
            res = httpService
                    .createPUTRequestWithJwt(
                            new URI(serverUrl + "/book"), HttpRequest.BodyPublishers.ofString(jsonObject.toString())
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res != null && res.statusCode() == 200;
    }

    private boolean deleteBookOnServer(Book book) {
        HttpResponse<String> res = null;
        try {
            res = httpService
                    .createDELETERequestWithJwt(
                            new URI(serverUrl + "/book/" + book.getBookId())
                    );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return res != null && res.statusCode() == 200;
    }

    public void centerCell(TableColumn<Book, String> col) {
        col.setCellFactory(new Callback<TableColumn<Book, String>, TableCell<Book, String>>() {
            @Override
            public TableCell<Book, String> call(TableColumn<Book, String> p) {
                TableCell<Book, String> tc = new TableCell<Book, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });
    }
}
