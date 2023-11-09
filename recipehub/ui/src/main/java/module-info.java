module ui {
    requires transitive core;
    requires transitive file;
    requires java.net.http;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires transitive javafx.graphics;
    exports ui;

    opens ui to javafx.fxml, javafx.graphics;
}
