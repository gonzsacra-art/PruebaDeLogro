package com.ucv.lab12.controller;

import com.ucv.lab12.model.Videojuego;
import com.ucv.lab12.service.IVideojuegoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class VideojuegoController {

    private final IVideojuegoService videojuegoService;

    // Componentes del Formulario FXML
    @FXML private TextField txtNombre;
    @FXML private TextField txtConsola;
    @FXML private TextField txtGenero;
    @FXML private TextField txtClasificacion;
    @FXML private TextArea txtDescripcion;
    @FXML private TextField txtIdDesarrollador;
    @FXML private TextField txtIdDistribuidor;

    // Componentes de la Tabla FXML
    @FXML private TableView<Videojuego> tblVideojuegos;
    @FXML private TableColumn<Videojuego, Integer> colId;
    @FXML private TableColumn<Videojuego, String> colNombre;
    @FXML private TableColumn<Videojuego, String> colConsola;
    @FXML private TableColumn<Videojuego, String> colGenero;
    @FXML private TableColumn<Videojuego, String> colClasificacion;
    @FXML private TableColumn<Videojuego, String> colDescripcion;
    @FXML private TableColumn<Videojuego, Integer> colIdDesarrollador;
    @FXML private TableColumn<Videojuego, Integer> colIdDistribuidor;

    private ObservableList<Videojuego> listaVideojuegos;

    // Constructor para la Inyección de Dependencias desde AppContext
    public VideojuegoController(IVideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @FXML
    public void initialize() {
        // Enlace de columnas con los atributos del modelo Videojuego
        colId.setCellValueFactory(new PropertyValueFactory<>("idVideojuego"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colConsola.setCellValueFactory(new PropertyValueFactory<>("consola"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colIdDesarrollador.setCellValueFactory(new PropertyValueFactory<>("idDesarrollador"));
        colIdDistribuidor.setCellValueFactory(new PropertyValueFactory<>("idDistribuidor"));

        // Cargar los datos al iniciar la ventana
        cargarDatosTabla();
    }

    private void cargarDatosTabla() {
        // Aquí llamamos al método correcto de tu interfaz de servicio
        List<Videojuego> videojuegos = videojuegoService.obtenerVideojuegos();
        listaVideojuegos = FXCollections.observableArrayList(videojuegos);
        tblVideojuegos.setItems(listaVideojuegos);
    }

    @FXML
    private void guardarVideojuego(ActionEvent event) {
        try {
            if (txtNombre.getText().trim().isEmpty() || txtConsola.getText().trim().isEmpty()) {
                mostrarAlerta("Campos Requeridos", "Por favor ingresa al menos el Nombre y la Consola.", Alert.AlertType.WARNING);
                return;
            }

            Videojuego nuevoJuego = new Videojuego();
            nuevoJuego.setNombre(txtNombre.getText().trim());
            nuevoJuego.setConsola(txtConsola.getText().trim());
            nuevoJuego.setGenero(txtGenero.getText().trim());
            nuevoJuego.setClasificacion(txtClasificacion.getText().trim());
            nuevoJuego.setDescripcion(txtDescripcion.getText().trim());

            int idDesarr = txtIdDesarrollador.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdDesarrollador.getText().trim());
            int idDistr = txtIdDistribuidor.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdDistribuidor.getText().trim());

            nuevoJuego.setIdDesarrollador(idDesarr);
            nuevoJuego.setIdDistribuidor(idDistr);

            videojuegoService.registrarVideojuego(nuevoJuego);

            mostrarAlerta("Éxito", "El videojuego se registró correctamente.", Alert.AlertType.INFORMATION);

            limpiarFormulario();
            cargarDatosTabla();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "Los IDs deben ser números enteros.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un problema: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtNombre.clear();
        txtConsola.clear();
        txtGenero.clear();
        txtClasificacion.clear();
        txtDescripcion.clear();
        txtIdDesarrollador.clear();
        txtIdDistribuidor.clear();
    }

    @FXML
    private void regresarAlMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ucv/lab12/Menu.fxml"));
            loader.setControllerFactory(com.ucv.lab12.config.AppContext.getInstance()::getController);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 1100, 620));
            stage.setTitle("Sistema de Gestión - UCV");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al menú principal.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}