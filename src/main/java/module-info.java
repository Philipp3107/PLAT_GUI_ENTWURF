module com.flimwip.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.flimwip.design to javafx.fxml;
    exports org.flimwip.design;
}