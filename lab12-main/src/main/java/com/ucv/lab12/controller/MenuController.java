package com.ucv.lab12.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class MenuController {

    @FXML
    private void abrirDistribuidores(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/ucv/lab12/distribuidor-view.fxml"));

            loader.setControllerFactory(
                    com.ucv.lab12.config.AppContext.getInstance()::getController);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loader.load(), 1100, 620));
            stage.setTitle("Mantenimiento de Distribuidores");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void abrirVideojuegos(ActionEvent event) {

        try {

            // Cargamos el archivo FXML correspondiente a la vista principal de videojuegos
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/ucv/lab12/videojuego-view.fxml"));

            // Conectamos el controlador con el AppContext para inyectar el VideojuegoService
            loader.setControllerFactory(
                    com.ucv.lab12.config.AppContext.getInstance()::getController);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Mantenemos las proporciones estables de la ventana de gestión
            stage.setScene(new Scene(loader.load(), 1100, 620));
            stage.setTitle("Mantenimiento de Videojuegos");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void salir() {
        Platform.exit();
    }

}