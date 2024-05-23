package com.bugops.inicio.service;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;

/**
 * Esta interfaz proporciona métodos para almacenar y cargar archivos.
 */
public interface FileStorageService {
    /**
     * Carga un archivo como recurso.
     *
     * @param fileName el nombre del archivo a cargar
     * @return el recurso cargado
     * @throws FileNotFoundException si el archivo no se encuentra
     */
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException;
}