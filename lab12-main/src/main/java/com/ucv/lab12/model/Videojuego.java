package com.ucv.lab12.model;

public class Videojuego {
    private int idVideojuego;
    private String consola;
    private String nombre;
    private String genero;
    private String clasificacion;
    private String descripcion;
    private int idDesarrollador;
    private int idDistribuidor;

    // Constructor vacío
    public Videojuego() {}

    // Constructor completo
    public Videojuego(int idVideojuego, String consola, String nombre, String genero,
                      String clasificacion, String descripcion, int idDesarrollador, int idDistribuidor) {
        this.idVideojuego = idVideojuego;
        this.consola = consola;
        this.nombre = nombre;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.descripcion = descripcion;
        this.idDesarrollador = idDesarrollador;
        this.idDistribuidor = idDistribuidor;
    }

    // Getters y Setters
    public int getIdVideojuego() { return idVideojuego; }
    public void setIdVideojuego(int idVideojuego) { this.idVideojuego = idVideojuego; }

    public String getConsola() { return consola; }
    public void setConsola(String consola) { this.consola = consola; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIdDesarrollador() { return idDesarrollador; }
    public void setIdDesarrollador(int idDesarrollador) { this.idDesarrollador = idDesarrollador; }

    public int getIdDistribuidor() { return idDistribuidor; }
    public void setIdDistribuidor(int idDistribuidor) { this.idDistribuidor = idDistribuidor; }
}