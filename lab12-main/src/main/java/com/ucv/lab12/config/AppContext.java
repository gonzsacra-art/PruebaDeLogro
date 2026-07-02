package com.ucv.lab12.config;

import com.ucv.lab12.config.DatabaseConfig;
import com.ucv.lab12.repository.*;
import com.ucv.lab12.service.*;
import com.ucv.lab12.controller.*;

/**
 * Contenedor de Inyección de Dependencias (DI).
 * Instancia y conecta todas las capas del sistema de forma limpia.
 */
public class AppContext {

    private static AppContext instance;

    private final DatabaseConfig dbConfig;

    // Capa de Deudas UGEL
    private final IDeudaUGELRepository deudaUGELRepository;
    private final IDeudaUGELService deudaUGELService;

    private AppContext() {
        // 1. Inicializar la configuración de base de datos
        this.dbConfig = new DatabaseConfig();

        // 2. Inicialización única del módulo Deuda UGEL
        this.deudaUGELRepository = new DeudaUGELRepository(dbConfig);
        this.deudaUGELService = new DeudaUGELService(deudaUGELRepository);
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    /**
     * Factory de controladores para FXMLLoader.setControllerFactory().
     * Inyecta de forma dinámica los servicios requeridos en cada ventana (FXML).
     */
    public Object getController(Class<?> type) {

        // 1. Controladores de Navegación del Panel Principal
        if (type == MenuModuloController.class) {
            return new MenuModuloController();
        }

        // 2. Controladores de Deuda (Inyección del servicio hacia los constructores)
        if (type == ModuloRegistroDeudaController.class) {
            return new ModuloRegistroDeudaController(deudaUGELService);
        }

        if (type == ModuloReporteDeudaController.class) {
            return new ModuloReporteDeudaController(deudaUGELService);
        }

        // Fallback para constructores vacíos por defecto (p.ej. si usas InicioSesionController de forma opcional)
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo instanciar el controlador: " + type.getName(), e);
        }
    }

    // --- Getter único expuesto para el servicio de Deudas ---

    public IDeudaUGELService getDeudaUGELService() {
        return deudaUGELService;
    }

    /**
     * Cierre limpio de recursos al salir de la aplicación.
     */
    public void destroy() {
        if (dbConfig != null) {
            dbConfig.close();
        }
    }
}