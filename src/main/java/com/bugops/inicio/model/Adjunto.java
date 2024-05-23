package com.bugops.inicio.model;

import jakarta.persistence.*;

/**
 * Clase que representa un archivo adjunto de un comentario.
 */
@Entity
@Table(name = "adjunto")
public class Adjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_comentario")
    private Comentario comentario;

    private String nombre;
    private String ruta;

    /**
     * Constructor por defecto.
     */
    public Adjunto() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param comentario Comentario al que pertenece el adjunto.
     * @param nombre     Nombre del adjunto.
     * @param ruta       Ruta del adjunto.
     */
    public Adjunto(Comentario comentario, String nombre, String ruta) {
        this.comentario = comentario;
        this.nombre = nombre;
        this.ruta = ruta;
    }

    /**
     * Devuelve el id del adjunto.
     *
     * @return Id del adjunto.
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Devuelve el comentario al que pertenece el adjunto.
     *
     * @return Comentario al que pertenece el adjunto.
     */
    public Comentario getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario al que pertenece el adjunto.
     *
     * @param comentario
     */
    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    /**
     * Devuelve el nombre del adjunto.
     *
     * @return Nombre del adjunto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del adjunto.
     *
     * @param nombre Nombre del adjunto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la ruta del adjunto.
     *
     * @return Ruta del adjunto.
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Establece la ruta del adjunto.
     *
     * @param ruta Ruta del adjunto.
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
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
        Adjunto adjunto = (Adjunto) obj;
        return id.equals(adjunto.id);
    }

    /**
     * Método toString.
     *
     * @return Representación en cadena del objeto.
     */
    @Override
    public String toString() {
        return "Adjunto{" +
                "id=" + id +
                ", comentario=" + comentario +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}