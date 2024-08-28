package com.ericsson.edca.catalog.controller;

import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edca")
public class CatalogController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    CatalogService catalogService;

    //Get Call: http://localhost:8080/api/catalogs
    @GetMapping("/catalog")
    public List<Catalog> getCatalogs() {
        logger.info("GET Request:: Get all Catalogs");
        return catalogService.getCatalogs();
    }

    @GetMapping("/catalogbyid")
    public ResponseEntity<Catalog> getCatalogByCatalogId(@RequestParam(name = "datatype") String datatype,
                                                         @RequestParam(name = "datanotification") String datanotification) {
        if (datatype != null && datanotification != null) {
            logger.info("GET Request:: Get catalogs with datatype ->>" + datatype + "and datanotification -->" + datanotification);
            return catalogService.getCatalogById(datatype, datanotification)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            logger.info("GET Request:: Bad Request :: Either datatype or datanotification missing in request");
            return ResponseEntity.badRequest().build();
        }
    }


    //Post Call
    @PostMapping("/catalog")
    public ResponseEntity<?> createCatalog(@RequestBody Catalog catalog) {
        if (catalog != null) {
            Catalog persistedCatalog = catalogService.createCatalog(catalog);
            if (persistedCatalog == null) {
                logger.info("POST Request:: Calalog already exits :: No data persisted");
                return ResponseEntity.status(HttpStatus.OK).body("Record already exists.");
            } else {
                logger.info("POST Request:: Calalog doesn't exits :: New Catalog created");
                return new ResponseEntity<Catalog>(persistedCatalog, HttpStatus.CREATED);
            }
        } else {
            logger.info("POST Request:: Bad Request :: No request Body");
            return ResponseEntity.badRequest().build();
        }

    }

    //Put Call
    @PutMapping("/catalog")
    public ResponseEntity<?> updateCatalog(@RequestBody Catalog catalog) {
        if (catalog != null) {
            Catalog persistedCatalog = catalogService.updateCatalog(catalog);
            if (persistedCatalog != null) {
                logger.info("PUT Request:: Calalog already exits :: Catalog details updated");
                return ResponseEntity.status(HttpStatus.OK).body("Record already exists.");
            } else {
                logger.info("PUT Request:: Calalog doesn't exits :: New Catalog created");
                return new ResponseEntity<Catalog>(catalog, HttpStatus.CREATED);
            }

        } else {
            logger.info("PUT Request:: :: Bad Request :: No request Body");
            return ResponseEntity.badRequest().build();
        }
    }


}
