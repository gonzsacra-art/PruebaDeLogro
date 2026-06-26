package com.ucv.lab12.service;

import com.ucv.lab12.model.Videojuego;
import com.ucv.lab12.repository.IVideojuegoRepository;
import java.util.List;

public class VideojuegoService implements IVideojuegoService {

    private final IVideojuegoRepository repository;

    // Inyección del repositorio por constructor
    public VideojuegoService(IVideojuegoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarVideojuego(Videojuego videojuego) {
        // Aquí puedes agregar lógica de negocio o validaciones extras si es necesario
        repository.guardar(videojuego);
    }

    @Override
    public List<Videojuego> obtenerVideojuegos() {
        return repository.listarTodos();
    }
}