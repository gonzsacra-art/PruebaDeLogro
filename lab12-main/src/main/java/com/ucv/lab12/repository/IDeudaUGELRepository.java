package com.ucv.lab12.repository;

import com.ucv.lab12.model.DeudaUGEL;
import java.util.List;

public interface IDeudaUGELRepository {
    List<DeudaUGEL> findAll();
    List<DeudaUGEL> findByFilters(String dni, String resolucion);
    void save(DeudaUGEL d);
    void update(DeudaUGEL d);
    void delete(String dni); // Se asume el DNI como identificador clave, o cámbialo según tu tabla
}