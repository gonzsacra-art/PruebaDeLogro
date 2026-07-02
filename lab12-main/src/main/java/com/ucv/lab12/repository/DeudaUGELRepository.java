package com.ucv.lab12.repository;

import com.ucv.lab12.config.DatabaseConfig;
import com.ucv.lab12.model.DeudaUGEL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeudaUGELRepository implements IDeudaUGELRepository, AutoCloseable {

    private final DatabaseConfig dbConfig;

    public DeudaUGELRepository(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public List<DeudaUGEL> findAll() {
        return findByFilters("", "");
    }

    @Override
    public List<DeudaUGEL> findByFilters(String dni, String resolucion) {
        String sql = """
                SELECT dni, resolucion, concepto, monto, estado
                FROM DeudaUGEL
                WHERE dni LIKE ?
                  AND resolucion LIKE ?
                ORDER BY dni
                """;
        List<DeudaUGEL> list = new ArrayList<>();
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + (dni == null ? "" : dni.trim()) + "%");
            ps.setString(2, "%" + (resolucion == null ? "" : resolucion.trim()) + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar las deudas de la UGEL", e);
        }
        return list;
    }

    @Override
    public void save(DeudaUGEL d) {
        String sql = """
                INSERT INTO DeudaUGEL (dni, resolucion, concepto, monto, estado)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getDni());
            ps.setString(2, nvl(d.getResolucion()));
            ps.setString(3, nvl(d.getConcepto()));
            ps.setDouble(4, d.getMonto());
            ps.setString(5, nvl(d.getEstado()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la deuda de la UGEL", e);
        }
    }

    @Override
    public void update(DeudaUGEL d) {
        String sql = """
                UPDATE DeudaUGEL
                SET resolucion = ?,
                    concepto   = ?,
                    monto      = ?,
                    estado     = ?
                WHERE dni = ?
                """;
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nvl(d.getResolucion()));
            ps.setString(2, nvl(d.getConcepto()));
            ps.setDouble(3, d.getMonto());
            ps.setString(4, nvl(d.getEstado()));
            ps.setString(5, d.getDni());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la deuda de la UGEL", e);
        }
    }

    @Override
    public void delete(String dni) {
        String sql = "DELETE FROM DeudaUGEL WHERE dni = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la deuda de la UGEL", e);
        }
    }

    // --- Helpers ---

    private DeudaUGEL mapRow(ResultSet rs) throws SQLException {
        return new DeudaUGEL(
                rs.getString("dni"),
                rs.getString("resolucion"),
                rs.getString("concepto"),
                rs.getDouble("monto"),
                rs.getString("estado")
        );
    }

    private String nvl(String value) {
        return value == null ? "" : value.trim();
    }

    @Override
    public void close() {
        dbConfig.close();
    }
}