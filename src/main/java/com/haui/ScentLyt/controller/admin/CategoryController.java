package com.haui.ScentLyt.controller.admin;

import com.haui.ScentLyt.DTO.CategoryDTO;
import com.haui.ScentLyt.entity.Category;
import com.haui.ScentLyt.response.ResponseObject;
import com.haui.ScentLyt.response.category.CategoryResponse;
import com.haui.ScentLyt.response.category.ListCategoryResponse;
import com.haui.ScentLyt.service.CategoryService;
import com.haui.ScentLyt.utils.LocalizationUtils;
import com.haui.ScentLyt.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/open-api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CREATE')")
    public ResponseEntity<ResponseObject> create(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_FAILED))
                            .status(HttpStatus.BAD_REQUEST)
                            .data(errors)
                            .build());
        }

        Category category = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .data(category)
                        .build());
    }

    @GetMapping("/by-name")
    public ResponseEntity<ResponseObject> getCategories(@RequestParam(defaultValue = "", name = "category_name") String name) {
        try {
            List<CategoryResponse> categoryResponses = categoryService.getAllCategories(name, true);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_CATEGORY_SUCCESSFULLY))
                            .status(HttpStatus.OK)
                            .data(categoryResponses)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_CATEGORY_FAILED))
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READ')")
    public ResponseEntity<ResponseObject> getCategories(@RequestParam(defaultValue = "") String name,
                                                        @RequestParam(defaultValue = "true", name = "active") Boolean active,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<CategoryResponse> categoryPage = categoryService.getAllCategories(name, active, pageRequest);
        ListCategoryResponse listCategoryResponse = ListCategoryResponse.builder()
                .categories(categoryPage.getContent())
                .totalPages(categoryPage.getTotalPages())
                .build();

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_CATEGORY_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .data(listCategoryResponse)
                        .build());
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READ')")
    public ResponseEntity<ResponseObject> getCategoryDetail(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);

        if (category == null) {
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_IS_NOT_FOUND))
                            .status(HttpStatus.NOT_FOUND)
                            .data("")
                            .build());
        }

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.GET_CATEGORY_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .data(category)
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_UPDATE')")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable Integer id,
                                                         @Valid @RequestBody CategoryDTO categoryDTO,
                                                         BindingResult result) {
        Category category = categoryService.getCategory(id);

        if (category == null) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_IS_NOT_FOUND))
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_FAILED))
                            .status(HttpStatus.BAD_REQUEST)
                            .data(errors)
                            .build());
        }

        category = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .data(category)
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DELETE')")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);

        if (category == null) {
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_IS_NOT_FOUND))
                            .status(HttpStatus.NOT_FOUND)
                            .build());
        }

        categoryService.delete(id);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_CATEGORY_SUCCESSFULLY))
                        .status(HttpStatus.OK)
                        .build());
    }
}
