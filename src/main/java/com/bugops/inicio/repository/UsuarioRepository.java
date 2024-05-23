package com.bugops.inicio.repository;

import com.bugops.inicio.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Esta interfaz representa el repositorio de usuarios en la aplicación.
 * Proporciona métodos para acceder y manipular los datos de los usuarios en la
 * base de datos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra ninguno.
     */
    Usuario findByNombreUsuario(String nombreUsuario);

    /**
     * Busca todos los usuarios que no han sido eliminados. Spring Data JPA
     * interpretará el nombre del método y generará la consulta adecuada.
     *
     * @return Una lista de usuarios que no han sido eliminados.
     */
    List<Usuario> findByEliminadoFalse();

}
