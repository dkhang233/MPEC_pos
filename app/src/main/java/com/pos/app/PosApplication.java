package com.pos.app;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class PosApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load file fxml và tạo scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("com/pos/app/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet(), getClass().getClassLoader().getResource("css/styles.css").toExternalForm());


        // Khởi tạo stage
        stage.setTitle("MPEC-Pos");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}