package com.ucv.lab12.service;

import com.ucv.lab12.model.Videojuego;
import java.util.List;

public interface IVideojuegoService {
    void registrarVideojuego(Videojuego videojuego);

    List<Videojuego> obtenerVideojuegos();
}