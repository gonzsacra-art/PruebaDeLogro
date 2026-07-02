package com.ucv.lab12.service;

import com.ucv.lab12.model.DeudaUGEL;
import com.ucv.lab12.repository.IDeudaUGELRepository;

import java.util.List;

public class DeudaUGELService implements IDeudaUGELService {

    private final IDeudaUGELRepository repository;

    public DeudaUGELService(IDeudaUGELRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DeudaUGEL> listar() {
        return repository.findAll();
    }

    @Override
    public List<DeudaUGEL> buscar(String dni, String resolucion) {
        return repository.findByFilters(dni, resolucion);
    }

    @Override
    public void crear(DeudaUGEL deuda) {
        validar(deuda);
        repository.save(deuda);
    }

    @Override
    public void actualizar(DeudaUGEL deuda) {
        validar(deuda);
        repository.update(deuda);
    }

    @Override
    public void eliminar(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es requerido para eliminar el registro.");
        }
        repository.delete(dni);
    }

    @Override
    public void validar(DeudaUGEL d) {
        // Validación del DNI
        if (d.getDni() == null || d.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI del docente es obligatorio.");
        }
        if (!d.getDni().trim().matches("^\\d{8}$")) {
            throw new IllegalArgumentException("El DNI debe estar compuesto exactamente por 8 dígitos numéricos.");
        }

        // Validación de la Resolución Directoral (RD)
        if (d.getResolucion() == null || d.getResolucion().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de Resolución Directoral (RD) es obligatorio.");
        }
        if (d.getResolucion().trim().length() > 50) {
            throw new IllegalArgumentException("El identificador de la resolución no puede superar los 50 caracteres.");
        }

        // Validación del Concepto de Pago
        if (d.getConcepto() == null || d.getConcepto().trim().isEmpty()) {
            throw new IllegalArgumentException("El concepto de la deuda es obligatorio.");
        }
        if (d.getConcepto().trim().length() > 150) {
            throw new IllegalArgumentException("El concepto de pago no puede superar los 150 caracteres.");
        }

        // Validación del Monto Financiero
        if (d.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto original asignado debe ser una cantidad mayor a cero (S/.).");
        }

        // Validación del Estado de la Deuda
        if (d.getEstado() == null || d.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado inicial de la deuda es obligatorio.");
        }
        if (d.getEstado().trim().length() > 20) {
            throw new IllegalArgumentException("El estado no puede superar los 20 caracteres.");
        }
    }
}