module com.flimwip.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.flimwip.demo to javafx.fxml;
    exports org.flimwip.demo;
}