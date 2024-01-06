module Lab1 {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires org.json;
    requires com.google.gson;

    exports pl.bilskik;
    exports pl.bilskik.viewmodel;
}