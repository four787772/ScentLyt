package com.haui.ScentLyt.repository;

import com.haui.ScentLyt.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Query("SELECT pi FROM ProductImage pi WHERE pi.id IN :imageIds")
    List<ProductImage> findAllImages(@Param("imageIds") List<Integer> imageIds);
}
