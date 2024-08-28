package com.ericsson.edca.catalog.service;

import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.repository.CatalogRepository;
import com.ericsson.edca.catalog.model.CatalogId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    /**
     * get all catalogs
     *
     * @return
     */
    public List<Catalog> getCatalogs() {
        return catalogRepository.findAll();
    }

    /*
     * public Optional<Catalog> getSubTypeByDatatype(String datatype) { // TODO
     * Auto-generated method stub return null; }
     */

    /**
     * Get catalog by composite key - datatype and datanotification
     *
     * @param datatype
     * @param dataNotification
     * @return
     */
    public Optional<Catalog> getCatalogById(String datatype, String dataNotification) {
        return catalogRepository.findById(new CatalogId(datatype, dataNotification));
    }

    /**
     * get catalog by datatype.
     *
     * @param datatype
     * @return
     */
    public Optional<Catalog> getCatalogByDataType(String datatype) {
        return catalogRepository.findByDataType(datatype);
    }

    /**
     * create catalog
     *
     * @param catalog
     * @return
     */
    public Catalog createCatalog(Catalog catalog) {
        //Catalog persistedCatalog = getCatalogById(catalog.getDataType(), catalog.getDataNotification()).orElseGet(null);
        Optional<Catalog> persistedCatalog = getCatalogById(catalog.getDataType(), catalog.getDataNotification());
        if (persistedCatalog.isPresent()) {
            //System.out.println("service::create catalog::"+persistedCatalog.get().getDataType());
            return null;
        } else {
            //System.out.println("Inside save if");
            return catalogRepository.save(catalog);
        }
    }

    /**
     * update catalog
     *
     * @param catalog
     * @return
     */
    public Catalog updateCatalog(Catalog catalog) {
        //Catalog persistedCatalog = getCatalogById(catalog.getDataType(), catalog.getDataNotification()).get();
        Optional<Catalog> persistedCatalog = getCatalogById(catalog.getDataType(), catalog.getDataNotification());
        if (persistedCatalog.isPresent()) {
            //update record
            persistedCatalog.get().setDataSubType(catalog.getDataSubType());
            persistedCatalog.get().setEncoding(catalog.getEncoding());
            persistedCatalog.get().setSourceType(catalog.getSourceType());
            persistedCatalog.get().setSpecRef(catalog.getSpecRef());
            return catalogRepository.save(persistedCatalog.get());
        } else {
            //new record
            catalogRepository.save(catalog);
            return null;
        }

    }


    /**
     * Create/update catalog - deprecated. To be used if only datatype is PK
     *
     * @param catalog
     * @return
     */
    public Catalog createCatalogDataType(Catalog catalog) {
        Catalog persistedCatalog = getCatalogByDataType(catalog.getDataType()).get();
        if (persistedCatalog == null) {
            persistedCatalog = catalog;
        } else {
            persistedCatalog.setDataSubType(catalog.getDataSubType());
            persistedCatalog.setEncoding(catalog.getEncoding());
            persistedCatalog.setSourceType(catalog.getSourceType());
            persistedCatalog.setSpecRef(catalog.getSpecRef());
            persistedCatalog.setDataNotification(catalog.getDataNotification());
        }
        return catalogRepository.save(persistedCatalog);
    }

}
