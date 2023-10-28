module ui {
    requires core;
    requires file;
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.google.gson;

    opens ui to javafx.fxml, javafx.graphics;
}
