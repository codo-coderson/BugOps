package com.bugops.inicio.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * La clase Usuario representa a un usuario en el sistema.
 * Implementa la interfaz UserDetails para la autenticación y autorización.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    /**
     * El nombre de usuario del usuario.
     */
    @Id
    @Column(name = "NombreUsuario", nullable = false, unique = true)
    private String nombreUsuario;

    /**
     * La contraseña del usuario.
     */
    private String password;

    /**
     * El rol del usuario.
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private Rol rol;

    /**
     * El nombre del usuario.
     */
    private String nombre;

    /**
     * El correo electrónico del usuario.
     */
    private String email;

    /**
     * Indica si el usuario ha sido eliminado.
     */
    private Boolean eliminado;

    /**
     * La fecha de alta del usuario.
     */
    private Date fechaAlta;

    /**
     * Los proyectos asociados al usuario.
     */
    @ManyToMany(mappedBy = "usuarios")
    private List<Proyecto> proyectos;

    /**
     * Los informes de error reportados por el usuario.
     */
    @OneToMany(mappedBy = "reportador")
    private List<InformeError> informesError;

    /**
     * Los comentarios realizados por el usuario.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    /**
     * Indica si el usuario necesita cambiar la contraseña.
     */
    private Boolean needsPasswordChange = false;

    /**
     * Crea una instancia de Usuario sin argumentos.
     */
    public Usuario() {
    }

    /**
     * Crea una instancia de Usuario con los argumentos proporcionados.
     *
     * @param nombreUsuario El nombre de usuario del usuario.
     * @param password      La contraseña del usuario.
     * @param rol           El rol del usuario.
     * @param nombre        El nombre del usuario.
     * @param email         El correo electrónico del usuario.
     * @param eliminado     Indica si el usuario ha sido eliminado.
     * @param fechaAlta     La fecha de alta del usuario.
     */
    public Usuario(String nombreUsuario, String password, Rol rol, String nombre, String email, Boolean eliminado,
            Date fechaAlta) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.nombre = nombre;
        this.email = email;
        this.eliminado = eliminado;
        this.fechaAlta = fechaAlta;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario del usuario.
     */
    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario del usuario.
     *
     * @param nombreUsuario El nombre de usuario del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El rol del usuario.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indica si el usuario ha sido eliminado.
     *
     * @return true si el usuario ha sido eliminado, false de lo contrario.
     */
    public Boolean getEliminado() {
        return eliminado;
    }

    /**
     * Establece si el usuario ha sido eliminado.
     *
     * @param eliminado true si el usuario ha sido eliminado, false de lo contrario.
     */
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    /**
     * Obtiene la fecha de alta del usuario.
     *
     * @return La fecha de alta del usuario.
     */
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Establece la fecha de alta del usuario.
     *
     * @param fechaAlta La fecha de alta del usuario.
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    /**
     * Obtiene los proyectos asociados al usuario.
     *
     * @return Una lista de proyectos asociados al usuario.
     */
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    /**
     * Establece los proyectos asociados al usuario.
     *
     * @param proyectos Una lista de proyectos asociados al usuario.
     */
    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    /**
     * Obtiene los informes de error reportados por el usuario.
     *
     * @return Una lista de informes de error reportados por el usuario.
     */
    public List<InformeError> getInformesError() {
        return informesError;
    }

    /**
     * Establece los informes de error reportados por el usuario.
     *
     * @param informesError Una lista de informes de error reportados por el
     *                      usuario.
     */
    public void setInformesError(List<InformeError> informesError) {
        this.informesError = informesError;
    }

    /**
     * Obtiene los comentarios realizados por el usuario.
     *
     * @return Una lista de comentarios realizados por el usuario.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Establece los comentarios realizados por el usuario.
     *
     * @param comentarios Una lista de comentarios realizados por el usuario.
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Indica si el usuario necesita cambiar la contraseña.
     *
     * @return true si el usuario necesita cambiar la contraseña, false de lo
     *         contrario.
     */
    public Boolean getNeedsPasswordChange() {
        return needsPasswordChange;
    }

    /**
     * Establece si el usuario necesita cambiar la contraseña.
     *
     * @param needsPasswordChange true si el usuario necesita cambiar la contraseña,
     *                            false de lo contrario.
     */
    public void setNeedsPasswordChange(Boolean needsPasswordChange) {
        this.needsPasswordChange = needsPasswordChange;
    }

    /**
     * Calcula el hash code del usuario.
     *
     * @return El hash code del usuario.
     */
    @Override
    public int hashCode() {
        return nombreUsuario.hashCode();
    }

    /**
     * Compara si el objeto especificado es igual al usuario.
     *
     * @param obj El objeto a comparar.
     * @return true si el objeto especificado es igual al usuario, false de lo
     *         contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Usuario usuario = (Usuario) obj;
        return nombreUsuario.equals(usuario.nombreUsuario);
    }

    /**
     * Devuelve una representación en cadena del usuario.
     *
     * @return Una representación en cadena del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", rol='" + rol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", eliminado=" + eliminado +
                ", fechaAlta=" + fechaAlta +
                '}';
    }

    /**
     * Obtiene las autoridades del usuario.
     *
     * @return Una lista de autoridades del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.rol));
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     *
     * @return true si la cuenta del usuario no ha expirado, false de lo contrario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     *
     * @return true si la cuenta del usuario no está bloqueada, false de lo
     *         contrario.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     *
     * @return true si las credenciales del usuario no han expirado, false de lo
     *         contrario.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     *
     * @return true si el usuario está habilitado, false de lo contrario.
     */
    @Override
    public boolean isEnabled() {
        return !this.eliminado;
    }

}