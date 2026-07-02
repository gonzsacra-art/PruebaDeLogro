package com.ucv.lab12.controller;

import com.ucv.lab12.config.AppContext;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuModuloController {

    @FXML
    private Button btnRegistro;

    @FXML
    private Button btnReporte;

    @FXML
    private VBox contentArea;

    @FXML
    void onAbrirRegistro(ActionEvent event) {
        cargarSubVista("ModuloRegistroDeuda.fxml");
    }

    @FXML
    void onAbrirReporte(ActionEvent event) {
        cargarSubVista("ModuloReporteDeuda.fxml");
    }

    private void cargarSubVista(String fxmlFile) {
        try {

            AppContext context = AppContext.getInstance();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ucv/lab12/" + fxmlFile));


            loader.setControllerFactory(context::getController);

            Parent subVista = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().setAll(subVista);

            System.out.println("Sub-vista cargada con éxito: " + fxmlFile);
        } catch (IOException e) {
            System.err.println("No se pudo cargar la vista modular: " + fxmlFile);
            e.printStackTrace();
        }
    }
}