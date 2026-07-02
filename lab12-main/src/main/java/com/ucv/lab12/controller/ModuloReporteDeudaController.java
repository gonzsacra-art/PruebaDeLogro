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

public class ModuloReporteDeudaController implements Initializable {

    @FXML private TextField txtCriterioBusqueda;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private Button btnFiltrar;

    @FXML private TableView<DeudaUGEL> tblReportes;
    @FXML private TableColumn<DeudaUGEL, String> colDni;
    @FXML private TableColumn<DeudaUGEL, String> colDocente;
    @FXML private TableColumn<DeudaUGEL, String> colResolucion;
    @FXML private TableColumn<DeudaUGEL, Double> colMontoOriginal;
    @FXML private TableColumn<DeudaUGEL, Double> colSaldoPendiente;
    @FXML private TableColumn<DeudaUGEL, String> colEstado;

    @FXML private Button btnExportarExcel;
    @FXML private Button btnExportarPdf;

    private final IDeudaUGELService deudaService;
    private final ObservableList<DeudaUGEL> listaFiltrada = FXCollections.observableArrayList();

    public ModuloReporteDeudaController(IDeudaUGELService deudaService) {
        this.deudaService = deudaService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (cmbFiltroEstado != null) {
            cmbFiltroEstado.setItems(FXCollections.observableArrayList("Todos", "Pendiente", "Cancelado"));
            cmbFiltroEstado.setValue("Todos");
        }

        // Enlaces de las columnas con los getters correspondientes de DeudaUGEL
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDocente.setCellValueFactory(new PropertyValueFactory<>("docente")); // Busca getDocente()
        colResolucion.setCellValueFactory(new PropertyValueFactory<>("resolucion"));
        colMontoOriginal.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colSaldoPendiente.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblReportes.setItems(listaFiltrada);
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        try {
            listaFiltrada.clear();
            listaFiltrada.addAll(deudaService.listar());
        } catch (Exception e) {
            System.err.println("Error al restaurar el reporte: " + e.getMessage());
        }
    }

    @FXML
    void onFiltrarDeudas(ActionEvent event) {
        try {
            String criterio = txtCriterioBusqueda.getText().strip();
            listaFiltrada.clear();
            listaFiltrada.addAll(deudaService.buscar(criterio, ""));
        } catch (Exception e) {
            System.err.println("Error al realizar la consulta filtrada: " + e.getMessage());
        }
    }

    @FXML
    void onExportarExcel(ActionEvent event) {
        System.out.println("Exportando reporte de deudas a formato Excel (.xlsx)...");
    }

    @FXML
    void onExportarPdf(ActionEvent event) {
        System.out.println("Exportando reporte de deudas a formato PDF (.pdf)...");
    }
}