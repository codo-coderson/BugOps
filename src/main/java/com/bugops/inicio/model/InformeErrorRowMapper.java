package com.bugops.inicio.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Esta clase implementa la interfaz RowMapper y se utiliza para mapear filas de
 * un ResultSet a objetos de tipo InformeError.
 */
public class InformeErrorRowMapper implements RowMapper<InformeError> {
    /**
     * Mapea una fila del ResultSet a un objeto InformeError.
     *
     * @param rs     el ResultSet que contiene los datos de la fila
     * @param rowNum el número de la fila actual
     * @return el objeto InformeError mapeado
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet
     */
    @Override
    public InformeError mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        InformeError informeError = new InformeError();
        informeError.setId(rs.getInt("ID"));
        informeError.setTitulo(rs.getString("Titulo"));
        informeError.setPrioridad(rs.getString("Prioridad"));
        informeError.setEstado(rs.getString("Estado"));
        // FechaCreacion es tipo datetime en SQL
        Timestamp timestamp = rs.getTimestamp("FechaCreacion");
        Date date = new Date(timestamp.getTime());
        informeError.setFechaCreacion(date);

        Usuario reportador = new Usuario();
        reportador.setNombreUsuario(rs.getString("Reportador")); // Asegúrate de que el nombre del campo coincide con tu
                                                                 // base de datos
        informeError.setReportador(reportador);

        Proyecto proyecto = new Proyecto();
        proyecto.setId(rs.getInt("ID_Proyecto"));
        proyecto.setNombre(rs.getString("Proyecto"));
        informeError.setProyecto(proyecto);
        informeError.getProyecto().setArchivado(rs.getBoolean("Archivado"));

        return informeError;
    }
}
