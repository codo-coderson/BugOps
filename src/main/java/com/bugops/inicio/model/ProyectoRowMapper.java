package com.bugops.inicio.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Esta clase implementa la interfaz RowMapper y se utiliza para mapear filas de
 * un ResultSet a objetos de tipo Proyecto.
 */
public class ProyectoRowMapper implements RowMapper<Proyecto> {
    /**
     * Mapea una fila del ResultSet a un objeto de tipo Proyecto.
     *
     * @param rs     el ResultSet que contiene los datos de la fila
     * @param rowNum el número de la fila actual
     * @return el objeto Proyecto mapeado
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet
     */
    @Override
    public Proyecto mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(rs.getInt("id"));
        proyecto.setNombre(rs.getString("nombre"));

        Timestamp timestamp = rs.getTimestamp("fechaCreacion");
        if (timestamp != null) {
            Date fechaCreacion = new Date(timestamp.getTime());
            proyecto.setFechaCreacion(fechaCreacion);
        }

        proyecto.setArchivado(rs.getBoolean("archivado"));

        return proyecto;
    }
}
