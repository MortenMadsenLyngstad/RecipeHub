module ui.controllers {
    requires core;
    requires file;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens ui.controllers to javafx.fxml, javafx.graphics;
    opens ui to javafx.fxml, javafx.graphics;
}
