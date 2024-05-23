package com.bugops.inicio.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Esta clase representa un proyecto en el sistema.
 */
@Entity
@Table(name = "proyecto")
public class Proyecto {

    /**
     * El identificador único del proyecto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El nombre del proyecto.
     */
    private String nombre;

    /**
     * La fecha de creación del proyecto.
     */
    private Date fechaCreacion;

    /**
     * Indica si el proyecto está archivado o no.
     */
    private Boolean archivado;

    /**
     * La lista de informes de error asociados al proyecto.
     */
    @OneToMany(mappedBy = "proyecto")
    private List<InformeError> informesError;

    /**
     * La lista de usuarios asociados al proyecto.
     */
    @ManyToMany
    @JoinTable(name = "proyecto_usuario", joinColumns = @JoinColumn(name = "proyecto_id"), inverseJoinColumns = @JoinColumn(name = "nombreusuario"))
    private List<Usuario> usuarios;

    /**
     * Constructor por defecto de la clase Proyecto.
     */
    public Proyecto() {
    }

    /**
     * Constructor de la clase Proyecto.
     *
     * @param nombre        El nombre del proyecto.
     * @param fechaCreacion La fecha de creación del proyecto.
     * @param archivado     Indica si el proyecto está archivado o no.
     */
    public Proyecto(String nombre, Date fechaCreacion, Boolean archivado) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.archivado = archivado;
    }

    /**
     * Obtiene el ID del proyecto.
     *
     * @return El ID del proyecto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del proyecto.
     *
     * @param id El ID del proyecto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del proyecto.
     *
     * @return El nombre del proyecto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del proyecto.
     *
     * @param nombre El nombre del proyecto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha de creación del proyecto.
     *
     * @return La fecha de creación del proyecto.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del proyecto.
     *
     * @param fechaCreacion La fecha de creación del proyecto.
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Indica si el proyecto está archivado o no.
     *
     * @return true si el proyecto está archivado, false de lo contrario.
     */
    public Boolean getArchivado() {
        return archivado;
    }

    /**
     * Establece si el proyecto está archivado o no.
     *
     * @param archivado true si el proyecto está archivado, false de lo contrario.
     */
    public void setArchivado(Boolean archivado) {
        this.archivado = archivado;
    }

    /**
     * Obtiene la lista de informes de error asociados al proyecto.
     *
     * @return La lista de informes de error asociados al proyecto.
     */
    public List<InformeError> getInformesError() {
        return informesError;
    }

    /**
     * Establece la lista de informes de error asociados al proyecto.
     *
     * @param informesError La lista de informes de error asociados al proyecto.
     */
    public void setInformesError(List<InformeError> informesError) {
        this.informesError = informesError;
    }

    /**
     * Obtiene la lista de usuarios asociados al proyecto.
     *
     * @return La lista de usuarios asociados al proyecto.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Establece la lista de usuarios asociados al proyecto.
     *
     * @param usuarios La lista de usuarios asociados al proyecto.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Método hashCode.
     *
     * @return Código hash del objeto.
     */

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Método equals.
     *
     * @param obj Objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Proyecto proyecto = (Proyecto) obj;
        return id.equals(proyecto.id);
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Proyecto.
     *
     * @return Una representación en forma de cadena del objeto Proyecto.
     */

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", archivado=" + archivado +
                '}';
    }
}