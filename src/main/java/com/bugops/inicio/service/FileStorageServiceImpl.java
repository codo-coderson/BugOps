package com.bugops.inicio.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementación de la interfaz FileStorageService que proporciona métodos para
 * almacenar y cargar archivos.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation = Paths.get("./adjuntos/");

    /**
     * Carga un archivo como recurso a partir de su nombre.
     *
     * @param fileName el nombre del archivo a cargar
     * @return el recurso del archivo cargado
     * @throws FileNotFoundException si el archivo no se encuentra
     */
    @Override
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Archivo no encontrado: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        }
    }
}
