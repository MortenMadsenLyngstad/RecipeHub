module ui.controllers {
    requires core;
    requires file;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires de.jensd.fx.glyphs.fontawesome;

    opens ui.controllers to javafx.fxml, javafx.graphics;
    opens ui to javafx.fxml, javafx.graphics;
}
