package com.ucv.lab12.config;

import com.ucv.lab12.controller.DistribuidorController;
import com.ucv.lab12.controller.DistribuidorFormController;
import com.ucv.lab12.controller.MenuController;
import com.ucv.lab12.controller.VideojuegoController;
import com.ucv.lab12.repository.DistribuidorRepository;
import com.ucv.lab12.repository.IDistribuidorRepository;
import com.ucv.lab12.repository.VideojuegoRepository;
import com.ucv.lab12.repository.IVideojuegoRepository;
import com.ucv.lab12.service.DistribuidorService;
import com.ucv.lab12.service.IDistribuidorService;
import com.ucv.lab12.service.VideojuegoService;
import com.ucv.lab12.service.IVideojuegoService;

/**
 * Contenedor de Inyección de Dependencias (DI).
 * Instancia y conecta todas las capas: config → repository → service → controller.
 */
public class AppContext {

    private static AppContext instance;

    private final DatabaseConfig dbConfig;

    // Capa de Distribuidores
    private final IDistribuidorRepository distribuidorRepository;
    private final IDistribuidorService distribuidorService;

    // Capa de Videojuegos
    private final IVideojuegoRepository videojuegoRepository;
    private final IVideojuegoService videojuegoService;

    private AppContext() {
        this.dbConfig = new DatabaseConfig();

        // Inicialización de módulo Distribuidor
        this.distribuidorRepository = new DistribuidorRepository(dbConfig);
        this.distribuidorService = new DistribuidorService(distribuidorRepository);

        // Inicialización de módulo Videojuego
        this.videojuegoRepository = new VideojuegoRepository(dbConfig);
        this.videojuegoService = new VideojuegoService(videojuegoRepository);
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    /**
     * Factory de controladores para FXMLLoader.setControllerFactory().
     * Inyecta el servicio en cada controlador correspondiente.
     */
    public Object getController(Class<?> type) {

        if (type == MenuController.class) {
            return new MenuController();
        }

        // Inyección para controladores de Distribuidor
        if (type == DistribuidorController.class) {
            return new DistribuidorController(distribuidorService);
        }

        if (type == DistribuidorFormController.class) {
            return new DistribuidorFormController(distribuidorService);
        }

        // Inyección para controlador unificado de Videojuego
        if (type == VideojuegoController.class) {
            return new VideojuegoController(videojuegoService);
        }

        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear el controlador: " + type.getName(), e);
        }
    }

    public IDistribuidorService getDistribuidorService() {
        return distribuidorService;
    }

    // Getter para exponer el servicio de videojuegos si se requiere externamente
    public IVideojuegoService getVideojuegoService() {
        return videojuegoService;
    }

    public void destroy() {
        dbConfig.close();
    }
}
