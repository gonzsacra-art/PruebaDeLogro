package com.ucv.lab12.service;

import com.ucv.lab12.model.DeudaUGEL;
import java.util.List;

public interface IDeudaUGELService {
    List<DeudaUGEL> listar();
    List<DeudaUGEL> buscar(String dni, String resolucion);
    void crear(DeudaUGEL deuda);
    void actualizar(DeudaUGEL deuda);
    void eliminar(String dni);
    void validar(DeudaUGEL deuda);
}