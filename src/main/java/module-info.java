module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.example.demo.Screen;
    opens com.example.demo.Screen to javafx.fxml;
    exports com.example.demo.controller;
    exports com.example.demo.Actor;
    opens com.example.demo.Actor to javafx.fxml;
    exports com.example.demo.Level;
    opens com.example.demo.Level to javafx.fxml;
    exports com.example.demo.Image;
    opens com.example.demo.Image to javafx.fxml;
    exports com.example.demo.Actor.User;
    opens com.example.demo.Actor.User to javafx.fxml;
    exports com.example.demo.Actor.Enemy;
    opens com.example.demo.Actor.Enemy to javafx.fxml;
}