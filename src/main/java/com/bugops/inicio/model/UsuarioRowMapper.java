package com.bugops.inicio.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Esta clase implementa la interfaz RowMapper para mapear filas de un ResultSet
 * a objetos de tipo Usuario.
 */
public class UsuarioRowMapper implements RowMapper<Usuario> {
    /**
     * Mapea una fila del ResultSet a un objeto Usuario.
     *
     * @param rs     el ResultSet que contiene los datos de la fila
     * @param rowNum el número de la fila actual
     * @return el objeto Usuario mapeado
     * @throws SQLException si ocurre algún error al acceder a los datos del
     *                      ResultSet
     */
    @Override
    public Usuario mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(rs.getString("NombreUsuario"));
        usuario.setPassword(rs.getString("Password")); // Asegúrate de manejar correctamente la contraseña encriptada
        usuario.setRol(Rol.valueOf(rs.getString("rol")));
        usuario.setNombre(rs.getString("Nombre"));
        usuario.setEmail(rs.getString("Email"));
        usuario.setEliminado(rs.getBoolean("Eliminado"));
        usuario.setFechaAlta(new Date(rs.getTimestamp("FechaAlta").getTime()));
        return usuario;
    }
}