package javalinos.onlinestore.vista.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AplicacionFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaBaseJavaFX.fxml"));
        Scene scene = new Scene(loader.load());


    }

}
