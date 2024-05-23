package com.bugops.inicio.service;

import com.bugops.inicio.model.Rol;
import com.bugops.inicio.model.Usuario;
import com.bugops.inicio.repository.UsuarioRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz UserDetailsService que se utiliza para cargar
 * los detalles del usuario.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /** 
     * Repositorio de usuarios que proporciona métodos para acceder y manipular los
     * datos de los usuarios en la base de datos.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /** 
     * Codificador de contraseñas que se utiliza para codificar las contraseñas de
     * los usuarios.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Carga los detalles del usuario por su nombre de usuario.
     *
     * @param NombreUsuario el nombre de usuario del usuario a cargar
     * @return los detalles del usuario
     * @throws UsernameNotFoundException si el usuario no se encuentra o no está
     *                                   habilitado
     */
    @Override
    public UserDetails loadUserByUsername(String NombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(NombreUsuario);
        if (usuario == null | (usuario != null && !usuario.isEnabled())) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }

    /**
     * Crea un nuevo usuario con los detalles proporcionados.
     *
     * @param nombreUsuario el nombre de usuario del nuevo usuario
     * @param password      la contraseña del nuevo usuario
     * @param rol           el rol del nuevo usuario
     * @param nombre        el nombre del nuevo usuario
     * @param email         el correo electrónico del nuevo usuario
     * @param eliminado     indica si el nuevo usuario está eliminado o no
     * @param fechaAlta     la fecha de alta del nuevo usuario
     */
    public void createUser(String nombreUsuario, String password, Rol rol, String nombre, String email,
            Boolean eliminado, Date fechaAlta) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Usuario newUser = new Usuario(nombreUsuario, encodedPassword, rol, nombre, email, eliminado, fechaAlta);
        usuarioRepository.save(newUser);
    }
}