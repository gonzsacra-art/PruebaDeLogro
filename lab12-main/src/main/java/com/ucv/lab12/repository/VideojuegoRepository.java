package com.ucv.lab12.repository;

import com.ucv.lab12.config.DatabaseConfig;
import com.ucv.lab12.model.Videojuego;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoRepository implements IVideojuegoRepository {

    private final DatabaseConfig dbConfig;

    // Se inyecta la configuración de la base de datos por constructor
    public VideojuegoRepository(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public void guardar(Videojuego videojuego) {
        String sql = "INSERT INTO Videojuego (Consola, Nombre, Genero, Clasificacion, Descripcion, IDdesarrollador, IDdistribuidor) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConfig.getConnection(); // Ajusta según el método exacto de tu DatabaseConfig
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, videojuego.getConsola());
            ps.setString(2, videojuego.getNombre());
            ps.setString(3, videojuego.getGenero());
            ps.setString(4, videojuego.getClasificacion());
            ps.setString(5, videojuego.getDescripcion());
            ps.setInt(6, videojuego.getIdDesarrollador());
            ps.setInt(7, videojuego.getIdDistribuidor());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Videojuego> listarTodos() {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM Videojuego";
        try (Connection conn = dbConfig.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Videojuego v = new Videojuego(
                        rs.getInt("idVideojuego"),
                        rs.getString("Consola"),
                        rs.getString("Nombre"),
                        rs.getString("Genero"),
                        rs.getString("Clasificacion"),
                        rs.getString("Descripcion"),
                        rs.getInt("IDdesarrollador"),
                        rs.getInt("IDdistribuidor")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}