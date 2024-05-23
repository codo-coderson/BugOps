package com.bugops.inicio.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

/**
 * Esta clase implementa la interfaz RowMapper y se utiliza para mapear los
 * resultados de una consulta de base de datos
 * a un objeto Adjunto.
 */
public class AdjuntoRowMapper implements RowMapper<Adjunto> {
    /**
     * Mapea una fila de resultados de la consulta a un objeto Adjunto.
     *
     * @param rs     el ResultSet que contiene los resultados de la consulta
     * @param rowNum el número de la fila actual
     * @return el objeto Adjunto mapeado
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet
     */
    @Override
    public Adjunto mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Adjunto adjunto = new Adjunto();
        adjunto.setId(rs.getInt("ID"));
        adjunto.setNombre(rs.getString("Nombre"));
        adjunto.setRuta(rs.getString("Ruta"));
        return adjunto;
    }
}
