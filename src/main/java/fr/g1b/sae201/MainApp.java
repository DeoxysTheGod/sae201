package fr.g1b.sae201;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SismicAnalyzer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        FenetreInfo popup = new FenetreInfo();
        popup.display();
    }

    public static void main(String[] args) {
        launch();
    }
}