package com.ericsson.edca.catalog.controller;

import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.service.CatalogFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edca")
public class CatalogFileController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    CatalogFileService catalogProcessorService;


    @GetMapping("/file/catalog")
    public List<Catalog> getFileCatalogs() throws Exception {
        logger.info("GET Request:: Get all Catalogs");
        return catalogProcessorService.getFileCatalogs();
    }

    @GetMapping("/file/catalogbyid")
    public ResponseEntity<Catalog> getFileCatalogByCatalogId(@RequestParam(name = "datatype") String datatype,
                                                             @RequestParam(name = "datanotification") String datanotification) throws Exception {
        if (datatype != null && datanotification != null) {
            logger.info("GET Request:: Get catalogs with datatype ->>" + datatype + "and datanotification -->" + datanotification);
            return catalogProcessorService.getFileCatalogById(datatype, datanotification)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            logger.info("GET Request:: Bad Request :: Either datatype or datanotification missing in request");
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/file/catalog")
    public ResponseEntity<?> createFileCatalog(@RequestBody Catalog catalog) throws Exception {
        if (catalog != null) {
            boolean isUpdationAllowed = false;
            Catalog persistedCatalog = catalogProcessorService.processCatalog(catalog, isUpdationAllowed);
            if (persistedCatalog == null) {
                logger.info("POST Request:: Calalog already exits :: No data persisted");
                return ResponseEntity.status(HttpStatus.OK).body("File already exist.");
            } else {
                logger.info("POST Request:: Calalog doesn't exits :: New Catalog created");
                return ResponseEntity.status(HttpStatus.CREATED).body("File created sucessfully.");
            }
        } else {
            logger.info("POST Request:: Bad Request :: No request Body");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/file/catalog")
    public ResponseEntity<?> updateFileCatalog(@RequestBody Catalog catalog) throws Exception {
        if (catalog != null) {
            boolean isUpdationAllowed = true;
            Catalog persistedCatalog = catalogProcessorService.processCatalog(catalog, isUpdationAllowed);
            if (persistedCatalog != null) {
                logger.info("PUT Request:: Calalog already exits :: Catalog details updated");
                return ResponseEntity.status(HttpStatus.OK).body("File updated.");

            } else {
                logger.info("PUT Request:: Calalog doesn't exits :: New Catalog created");
                return ResponseEntity.status(HttpStatus.OK).body("File doesn't exist.");
            }

        } else {
            logger.info("PUT Request:: :: Bad Request :: No request Body");
            return ResponseEntity.badRequest().build();
        }
    }

}
