package pl.bilskik.viewmodel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import pl.bilskik.DI.DI;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.Book;

import java.net.URI;
import java.net.URISyntaxException;
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

    public Callback<TableColumn<Book, Void>, TableCell<Book, Void>> getEditCellFactory(
            TextField bookNameInpt, TextField bookAuthorInpt, ObservableList<Book> data
    ) {
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory =
                new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {

                    @Override
                    public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                        final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

                            private final Button btn = new Button("Edit");
                            {
                                btn.setOnAction((ActionEvent event) -> {
                                    Book book = getTableView().getItems().get(getIndex());
                                    bookNameInpt.setText(book.getName());
                                    bookAuthorInpt.setText(book.getAuthor());
                                    for(var b : data) {
                                        if(b.isEdited()) {
                                            b.setEdited(false);
                                        }
                                    }
                                    book.setEdited(true);
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
                                    deleteBookOnServer(book);
                                    data.remove(book);
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

}
