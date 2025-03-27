module javalinos.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens javalinos.onlinestore to javafx.fxml;
    opens javalinos.onlinestore.controlador to javafx.fxml;
    opens javalinos.onlinestore.modelo.gestores to javafx.fxml;
    opens javalinos.onlinestore.modelo.primitivos to javafx.fxml;

    exports javalinos.onlinestore;
    exports javalinos.onlinestore.controlador;
    exports javalinos.onlinestore.modelo.gestores;
    exports javalinos.onlinestore.modelo.primitivos;
    exports javalinos.onlinestore.utils;
    exports javalinos.onlinestore.vista;
}