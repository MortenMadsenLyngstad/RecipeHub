module ui {
    requires core;
    requires file;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui.controllers to javafx.fxml, javafx.graphics;
    opens ui to javafx.graphics, javafx.fxml;
}
