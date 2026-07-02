package com.ucv.lab12.model;

public class DeudaUGEL {

    private String dni;
    private String resolucion;
    private String concepto;
    private double monto;
    private String estado;

    // Constructor vacío (necesario para frameworks de persistencia o mapeos)
    public DeudaUGEL() {
    }

    // Constructor con parámetros para inicializar datos rápidamente
    public DeudaUGEL(String dni, String resolucion, String concepto, double monto, String estado) {
        this.dni = dni;
        this.resolucion = resolucion;
        this.concepto = concepto;
        this.monto = monto;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS (Esenciales para que TableView lea los datos automáticamente) ---

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método toString opcional para imprimir datos en consola fácilmente (System.out.println)
    @Override
    public String toString() {
        return "DeudaUGEL{" + "dni=" + dni + ", resolucion=" + resolucion + ", concepto=" + concepto + ", monto=" + monto + ", estado=" + estado + '}';
    }
}