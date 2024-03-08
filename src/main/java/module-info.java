module com.gui.test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.gui.test to javafx.fxml;
    exports com.gui.test;
}