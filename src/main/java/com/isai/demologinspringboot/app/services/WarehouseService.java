package com.isai.demologinspringboot.app.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


public interface WarehouseService {
    void startWarehouseFiles();

    String storeFile(MultipartFile archive);

    Path loadFile(String nameArchive);

    Resource loadFileAsResource(String fileName);

    void deleteFile(String fileName);

}
