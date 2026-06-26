package com.ucv.lab12.repository;

import com.ucv.lab12.model.Videojuego;
import java.util.List;

public interface IVideojuegoRepository {
    void guardar(Videojuego videojuego);
    List<Videojuego> listarTodos();
}