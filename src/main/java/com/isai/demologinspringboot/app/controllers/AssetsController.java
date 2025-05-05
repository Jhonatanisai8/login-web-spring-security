package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.services.impl.WarehouseServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/assets")
@RequiredArgsConstructor
public class AssetsController {
    private final WarehouseServiceImp warehouseServiceImp;

    @GetMapping("/{filename:.+}")
    public Resource getResource(@PathVariable String filename) {
        return warehouseServiceImp.loadFileAsResource(filename);
    }

}
