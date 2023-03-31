module com.example.social_network_3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.social_network_3.domain to javafx.base;
    opens com.example.social_network_3 to javafx.fxml;
    exports com.example.social_network_3;


}