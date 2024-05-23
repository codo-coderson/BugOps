package com.bugops.inicio.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Representa un comentario realizado en un informe de error.
 */
@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El informe de error asociado al comentario.
     */
    @ManyToOne
    @JoinColumn(name = "id_informe_error")
    private InformeError informeError;

    /**
     * El usuario que realizó el comentario.
     */
    @ManyToOne
    @JoinColumn(name = "nombreusuario")
    private Usuario usuario;

    /**
     * El contenido del comentario.
     */
    private String contenido;

    /**
     * La fecha de creación del comentario.
     */
    private Date fechaCreacion;

    /**
     * La lista de adjuntos del comentario, por si en un futuro se quiere
     * implementar la posibilidad de varios adjuntos por comentario.
     */
    @OneToMany(mappedBy = "comentario")
    private List<Adjunto> adjuntos;

    /**
     * Constructor vacío de la clase Comentario.
     */
    public Comentario() {
    }

    /**
     * Constructor de la clase Comentario.
     *
     * @param informeError  El informe de error asociado al comentario.
     * @param usuario       El usuario que realizó el comentario.
     * @param contenido     El contenido del comentario.
     * @param fechaCreacion La fecha de creación del comentario.
     */
    public Comentario(InformeError informeError, Usuario usuario, String contenido, Date fechaCreacion) {
        this.informeError = informeError;
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene el ID del comentario.
     *
     * @return El ID del comentario.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del comentario.
     *
     * @param id El ID del comentario.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el informe de error asociado al comentario.
     *
     * @return El informe de error asociado al comentario.
     */
    public InformeError getInformeError() {
        return informeError;
    }

    /**
     * Establece el informe de error asociado al comentario.
     *
     * @param informeError El informe de error asociado al comentario.
     */
    public void setInformeError(InformeError informeError) {
        this.informeError = informeError;
    }

    /**
     * Obtiene el usuario que realizó el comentario.
     *
     * @return El usuario que realizó el comentario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó el comentario.
     *
     * @param usuario El usuario que realizó el comentario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el contenido del comentario.
     *
     * @return El contenido del comentario.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del comentario.
     *
     * @param contenido El contenido del comentario.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtiene la fecha de creación del comentario.
     *
     * @return La fecha de creación del comentario.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del comentario.
     *
     * @param fechaCreacion La fecha de creación del comentario.
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtiene la lista de adjuntos del comentario.
     *
     * @return La lista de adjuntos del comentario.
     */
    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    /**
     * Establece la lista de adjuntos del comentario.
     *
     * @param adjuntos La lista de adjuntos del comentario.
     */
    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    /**
     * Calcula el código hash del comentario.
     *
     * @return El código hash del comentario.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Compara si el comentario es igual a otro objeto.
     *
     * @param obj El objeto a comparar.
     * @return true si el comentario es igual al objeto, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Comentario comentario = (Comentario) obj;
        return id.equals(comentario.id);
    }

    /**
     * Devuelve una representación en cadena del comentario.
     *
     * @return Una representación en cadena del comentario.
     */
    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", informeError=" + informeError +
                ", usuario=" + usuario +
                ", contenido='" + contenido + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}