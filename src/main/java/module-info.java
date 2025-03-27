module javalinos.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    // 🚨 Añadido para habilitar pruebas con JUnit 5 (Jupiter)
    opens javalinos.onlinestore to javafx.fxml, org.junit.jupiter.api;
    opens javalinos.onlinestore.controlador to org.junit.jupiter.api;
    opens javalinos.onlinestore.modelo.gestores to org.junit.jupiter.api;
    opens javalinos.onlinestore.modelo.primitivos to org.junit.jupiter.api;

    exports javalinos.onlinestore;
    exports javalinos.onlinestore.controlador;
    exports javalinos.onlinestore.modelo.gestores;
    exports javalinos.onlinestore.modelo.primitivos;
    exports javalinos.onlinestore.utils;
    exports javalinos.onlinestore.vista;
}