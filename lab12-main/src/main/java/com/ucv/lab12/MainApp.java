package com.ucv.lab12;

import com.ucv.lab12.config.AppContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppContext context = AppContext.getInstance();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/ucv/lab12/MenuModulo.fxml")
        );

        loader.setControllerFactory(context::getController);


        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Gestión de Deudas - UGEL");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        AppContext.getInstance().destroy();
    }
}