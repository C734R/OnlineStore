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
    requires java.sql;
    requires jakarta.persistence;

    opens javalinos.onlinestore to javafx.fxml;
    opens javalinos.onlinestore.controlador to javafx.fxml;
    opens javalinos.onlinestore.modelo.gestores to javafx.fxml;
    opens javalinos.onlinestore.modelo.DTO to javafx.fxml;

    exports javalinos.onlinestore;
    exports javalinos.onlinestore.controlador;
    exports javalinos.onlinestore.modelo.gestores;
    exports javalinos.onlinestore.modelo.DTO;
    exports javalinos.onlinestore.utils;
    exports javalinos.onlinestore.vista;
    exports javalinos.onlinestore.modelo.Entidades;
    exports javalinos.onlinestore.modelo.DAO;
    exports javalinos.onlinestore.modelo.DAO.Interfaces;
    exports javalinos.onlinestore.modelo.gestores.Local;
    opens javalinos.onlinestore.modelo.gestores.Local to javafx.fxml;
    exports javalinos.onlinestore.modelo.gestores.Interfaces;
    opens javalinos.onlinestore.modelo.gestores.Interfaces to javafx.fxml;
    exports javalinos.onlinestore.utils.Conexiones;
}