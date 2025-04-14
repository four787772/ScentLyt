package com.haui.ScentLyt.repository;

import com.haui.ScentLyt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Integer id);

    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR :name = '' OR p.name LIKE %:name%) " +
            "AND (:fragrance IS NULL OR :fragrance = '' OR p.fragrance LIKE %:fragrance%) " +
            "AND (:color IS NULL OR :color = '' OR p.color LIKE %:color%)")
    Page<Product> findAllProducts(@Param("name") String name,
                                  @Param("fragrance") String fragrance,
                                  @Param("color") String color,
                                  Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR :name = '' OR p.name LIKE %:name%)")
    List<Product> findProductsByName(@Param("name") String name);

    @Query("SELECT p FROM Product p")
    List<Product> findAllProductsNoFilter();
}
