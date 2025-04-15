package com.haui.ScentLyt.repository;

import com.haui.ScentLyt.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByIdAndActive(Integer id, Boolean active);

    @Query("SELECT c FROM Category c WHERE " +
            "(:name IS NULL OR :name = '' OR c.categoryName LIKE %:name%) " +
            "AND (:active IS NULL OR c.active = :active)")
    Page<Category> findAllCategories(@Param("name") String name,
                                     @Param("active") Boolean active,
                                     Pageable pageable);

    @Query("SELECT c FROM Category c WHERE " +
            "(:name IS NULL OR :name = '' OR c.categoryName LIKE %:name%) " +
            "AND (:active IS NULL OR c.active = :active)")
    List<Category> findAllCategories(@Param("name") String name,
                                     @Param("active") Boolean active);

    Category findByIdAndActive(Integer id, boolean active);

    @Query("SELECT c FROM Category c WHERE (:active IS NULL OR c.active = :active)")
    List<Category> findAllCategoriesAndActive(@Param("active") Boolean active);
}
