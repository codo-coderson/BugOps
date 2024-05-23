package com.bugops.inicio.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * La clase InformeError representa un informe de error en el sistema.
 * Contiene información sobre el título, proyecto, prioridad, reportador,
 * estado,
 * fecha de creación, descripción, ruta del archivo adjunto, nombre del archivo
 * adjunto
 * y comentarios asociados al informe de error.
 */
@Entity
@Table(name = "informe_error")
public class InformeError {

    /**
     * El identificador único del informe de error.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El título del informe de error.
     */
    @Column(name = "titulo")
    private String titulo;

    /**
     * El proyecto asociado al informe de error.
     */
    @ManyToOne
    @JoinColumn(name = "id_informe_error")
    private Proyecto proyecto;

    /**
     * La prioridad del informe de error.
     */
    @Column(name = "prioridad")
    private String prioridad;

    /**
     * El usuario que reportó el informe de error.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario reportador;

    /**
     * El estado del informe de error.
     */
    @Column(name = "estado")
    private String estado;

    /**
     * La fecha de creación del informe de error.
     */
    @Column(name = "FechaCreacion")
    private Date fechaCreacion;

    /**
     * La descripción del informe de error.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * La ruta del archivo adjunto al informe de error.
     */
    @Column(name = "rutaarchivo")
    private String rutaArchivo;

    /**
     * El nombre del archivo adjunto al informe de error.
     */
    @Column(name = "nombrearchivo")
    private String nombreArchivo;

    /**
     * Los comentarios asociados al informe de error.
     */
    @OneToMany(mappedBy = "informeError")
    private List<Comentario> comentarios;

    /**
     * Obtiene el identificador del informe de error.
     *
     * @return El identificador del informe de error.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del informe de error.
     *
     * @param id El identificador del informe de error.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del informe de error.
     *
     * @return El título del informe de error.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del informe de error.
     *
     * @param title El título del informe de error.
     */
    public void setTitulo(String title) {
        this.titulo = title;
    }

    /**
     * Obtiene el proyecto asociado al informe de error.
     *
     * @return El proyecto asociado al informe de error.
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * Establece el proyecto asociado al informe de error.
     *
     * @param project El proyecto asociado al informe de error.
     */
    public void setProyecto(Proyecto project) {
        this.proyecto = project;
    }

    /**
     * Obtiene la prioridad del informe de error.
     *
     * @return La prioridad del informe de error.
     */
    public String getPrioridad() {
        return prioridad;
    }

    /**
     * Establece la prioridad del informe de error.
     *
     * @param priority La prioridad del informe de error.
     */
    public void setPrioridad(String priority) {
        this.prioridad = priority;
    }

    /**
     * Obtiene el usuario que reportó el informe de error.
     *
     * @return El usuario que reportó el informe de error.
     */
    public Usuario getReportador() {
        return reportador;
    }

    /**
     * Establece el usuario que reportó el informe de error.
     *
     * @param reporter El usuario que reportó el informe de error.
     */
    public void setReportador(Usuario reporter) {
        this.reportador = reporter;
    }

    /**
     * Obtiene el estado del informe de error.
     *
     * @return El estado del informe de error.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del informe de error.
     *
     * @param status El estado del informe de error.
     */
    public void setEstado(String status) {
        this.estado = status;
    }

    /**
     * Obtiene la fecha de creación del informe de error.
     *
     * @return La fecha de creación del informe de error.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del informe de error.
     *
     * @param creationDate La fecha de creación del informe de error.
     */
    public void setFechaCreacion(Date creationDate) {
        this.fechaCreacion = creationDate;
    }

    /**
     * Obtiene la descripción del informe de error.
     *
     * @return La descripción del informe de error.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del informe de error.
     *
     * @param description La descripción del informe de error.
     */
    public void setDescripcion(String description) {
        this.descripcion = description;
    }

    /**
     * Obtiene la ruta del archivo adjunto al informe de error.
     *
     * @return La ruta del archivo adjunto al informe de error.
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * Establece la ruta del archivo adjunto al informe de error.
     *
     * @param filePath La ruta del archivo adjunto al informe de error.
     */
    public void setRutaArchivo(String filePath) {
        this.rutaArchivo = filePath;
    }

    /**
     * Obtiene el nombre del archivo adjunto al informe de error.
     *
     * @return El nombre del archivo adjunto al informe de error.
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Establece el nombre del archivo adjunto al informe de error.
     *
     * @param fileName El nombre del archivo adjunto al informe de error.
     */
    public void setNombreArchivo(String fileName) {
        this.nombreArchivo = fileName;
    }

    /**
     * Obtiene los comentarios asociados al informe de error.
     *
     * @return Los comentarios asociados al informe de error.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Establece los comentarios asociados al informe de error.
     *
     * @param comments Los comentarios asociados al informe de error.
     */
    public void setComentarios(List<Comentario> comments) {
        this.comentarios = comments;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto InformeError.
     *
     * @return Una representación en forma de cadena del objeto InformeError.
     */
    @Override
    public String toString() {
        return "InformeError{" +
                "id=" + id +
                ", title='" + titulo + '\'' +
                ", project='" + proyecto + '\'' +
                ", priority='" + prioridad + '\'' +
                ", reporter='" + reportador + '\'' +
                ", status='" + estado + '\'' +
                ", description='" + descripcion + '\'' +
                ", filePath='" + rutaArchivo + '\'' +
                ", fileName='" + nombreArchivo + '\'' +
                '}';
    }
}