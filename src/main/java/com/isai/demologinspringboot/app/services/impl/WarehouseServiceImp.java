package com.isai.demologinspringboot.app.services.impl;

import com.isai.demologinspringboot.app.exceptions.FileNotFoundException;
import com.isai.demologinspringboot.app.exceptions.WarehouseException;
import com.isai.demologinspringboot.app.services.WarehouseService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class WarehouseServiceImp
        implements WarehouseService {

    @Value("${storage.location}")
    private String storegeLocation;

    // metodo que se ejecuta cada vez que se inicia la instancia de la clase
    @PostConstruct
    @Override
    public void startWarehouseFiles() {
        try {
            Files.createDirectories(Paths.get(storegeLocation));
        } catch (IOException e) {
            throw new WarehouseException("Error al crear el directorio de almacenamiento", e);
        }
    }

    @Override
    public String storeFile(MultipartFile archive) {
        if (archive.isEmpty()) {
            throw new WarehouseException("No se puede almacenar un archivo vacío");
        }

        try {
            String originalFilename = archive.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            Path directoryPath = Paths.get(storegeLocation);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path destinationFile = directoryPath.resolve(Paths.get(uniqueFilename))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = archive.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return uniqueFilename;
        } catch (IOException e) {
            throw new WarehouseException("Error al almacenar el archivo " + archive.getOriginalFilename(), e);
        }
    }

    @Override
    public Path loadFile(String nameArchive) {
        return Paths.get(storegeLocation)
                .resolve(nameArchive);
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = loadFile(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            // si el archivo no existe o no es legible, lanzamos una excepcion
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("No se puede encontrar el archivo: ".concat(fileName));
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("No se puede encontrar el archivo: ".concat(fileName), e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        Path filePath = loadFile(fileName);
        try {
            FileSystemUtils.deleteRecursively(filePath);
        } catch (IOException e) {
            throw new WarehouseException("Error al eliminar el archivo: ".concat(fileName), e);
        }
    }

}
