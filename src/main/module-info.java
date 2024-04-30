module org.flimwip.design {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.desktop;

    opens org.flimwip.design;
    exports org.flimwip.design;
}