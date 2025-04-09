module org.example.orientierungprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens org.example.orientierungprojekt to javafx.fxml;
    exports org.example.orientierungprojekt;
    exports org.example.orientierungprojekt.util;
}