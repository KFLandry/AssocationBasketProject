module com.example.assocationbasketproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires java.sql;
    requires com.google.api.client.extensions.java6.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.services.gmail;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.extensions.jetty.auth;

    opens com.example.assocationbasketproject to javafx.fxml;
    exports com.example.assocationbasketproject;
}