package com.ucv.lab12.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.ucv.lab12.model.DeudaUGEL;
import com.ucv.lab12.service.IDeudaUGELService;

public class ModuloRegistroDeudaController implements Initializable {

    @FXML private TextField txtDni;
    @FXML private TextField txtDocenteNombre;
    @FXML private TextField txtResolucion;
    @FXML private ComboBox<String> cmbConcepto;
    @FXML private TextField txtMonto;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;

    @FXML private TableView<DeudaUGEL> tblDeudas;
    @FXML private TableColumn<DeudaUGEL, String> colDni;
    @FXML private TableColumn<DeudaUGEL, String> colResolucion;
    @FXML private TableColumn<DeudaUGEL, String> colConcepto;
    @FXML private TableColumn<DeudaUGEL, Double> colMonto;
    @FXML private TableColumn<DeudaUGEL, String> colEstado;

    private final IDeudaUGELService deudaService;
    private final ObservableList<DeudaUGEL> listaDeudas = FXCollections.observableArrayList();

    // Constructor obligatorio para la Inyección de Dependencias desde AppContext
    public ModuloRegistroDeudaController(IDeudaUGELService deudaService) {
        this.deudaService = deudaService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> opciones = FXCollections.observableArrayList(
                "Preparación de Clases y Evaluaciones (30%)",
                "Subsidio por Luto y Gastos de Sepelio",
                "Bonificación por Céntimo de Servicio",
                "Devengados por Decretos de Urgencia"
        );
        cmbConcepto.setItems(opciones);

        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colResolucion.setCellValueFactory(new PropertyValueFactory<>("resolucion"));
        colConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblDeudas.setItems(listaDeudas);
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        try {
            listaDeudas.clear();
            listaDeudas.addAll(deudaService.listar());
        } catch (Exception e) {
            System.err.println("Error al cargar deudas: " + e.getMessage());
        }
    }

    @FXML
    void onGuardarDeuda(ActionEvent event) {
        try {
            String dni = txtDni.getText().strip();
            String res = txtResolucion.getText().strip();
            String con = cmbConcepto.getValue();
            double mon = txtMonto.getText().strip().isEmpty() ? 0 : Double.parseDouble(txtMonto.getText().strip());

            DeudaUGEL nuevaDeuda = new DeudaUGEL(dni, res, con, mon, "Pendiente");

            deudaService.crear(nuevaDeuda);
            listaDeudas.add(nuevaDeuda);
            onLimpiarCampos(null);
            System.out.println("Deuda registrada exitosamente.");
        } catch (NumberFormatException e) {
            System.err.println("El monto ingresado no es un número válido.");
        } catch (IllegalArgumentException e) {
            System.err.println("Validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    void onLimpiarCampos(ActionEvent event) {
        txtDni.clear();
        txtDocenteNombre.clear();
        txtResolucion.clear();
        txtMonto.clear();
        cmbConcepto.setValue(null);
    }
}