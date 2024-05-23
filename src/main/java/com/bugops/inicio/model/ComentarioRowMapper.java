package com.bugops.inicio.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

/**
 * Esta clase implementa la interfaz RowMapper y se utiliza para mapear filas de
 * un ResultSet a objetos de tipo Comentario.
 */
public class ComentarioRowMapper implements RowMapper<Comentario> {

    /**
     * Mapea una fila del ResultSet a un objeto Comentario.
     *
     * @param rs     el ResultSet que contiene los datos de la fila actual
     * @param rowNum el número de la fila actual
     * @return el objeto Comentario mapeado
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet
     */
    @Override
    public Comentario mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Comentario comentario = new Comentario();
        comentario.setId(rs.getInt("ID"));

        // FechaCreacion es tipo datetime en SQL
        Timestamp timestamp = rs.getTimestamp("FechaCreacion");
        Date date = new Date(timestamp.getTime());
        comentario.setFechaCreacion(date);

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(rs.getString("NombreUsuario"));
        comentario.setUsuario(usuario);

        comentario.setContenido(rs.getString("Contenido"));

        return comentario;
    }
}
