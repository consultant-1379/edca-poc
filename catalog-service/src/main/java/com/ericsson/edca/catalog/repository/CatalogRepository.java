package com.ericsson.edca.catalog.repository;

import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.model.CatalogId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, CatalogId> {

    Optional<Catalog> findByDataType(String datatype);

}


/*
 * @Repository public interface CatalogRepository extends
 * JpaRepository<Catalog,String>{
 *
 * Optional<Catalog> findByDataType(String datatype);
 *
 * }
 */

