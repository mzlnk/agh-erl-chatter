module client {

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    exports pl.mzlnk.erlchatter;

    opens pl.mzlnk.erlchatter.controller;

}