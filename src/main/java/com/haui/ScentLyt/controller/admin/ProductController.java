package com.haui.ScentLyt.controller.admin;

import com.haui.ScentLyt.DTO.ProductDTO;
import com.haui.ScentLyt.entity.Product;
import com.haui.ScentLyt.entity.ProductImage;
import com.haui.ScentLyt.repository.ProductImageRepository;
import com.haui.ScentLyt.response.ResponseObject;
import com.haui.ScentLyt.response.product.ListProductResponse;
import com.haui.ScentLyt.response.product.ProductResponse;
import com.haui.ScentLyt.service.ProductImageService;
import com.haui.ScentLyt.service.ProductService;
import com.haui.ScentLyt.utils.FileUtils;
import com.haui.ScentLyt.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/open-api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;
    private final ProductImageService productImageService;
    private final ProductImageRepository productImageRepository;

    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN') or hasRole('CREATE')")
    public ResponseEntity<ResponseObject> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                     BindingResult result) {
        List<String> errors = productService.validateInsert(productDTO, result);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(MessageKeys.INSERT_PRODUCT_FAILED)
                    .data(errors)
                    .status(HttpStatus.BAD_REQUEST).build());
        }

        ProductResponse productResponse = productService.save(productDTO);

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message(MessageKeys.INSERT_PRODUCT_SUCCESSFULLY)
                .data(productResponse)
                .status(HttpStatus.OK).build());
    }

    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImages(
            @PathVariable("id") Integer productId,
            @ModelAttribute("files") List<MultipartFile> files) {
        try {
            Product product = productService.getProductById(productId);

            if (productId == null) {
                return ResponseEntity.notFound().build();
            }

            files = files == null ? new ArrayList<>() : files;

            int fileSize = files.size();
            if (fileSize > MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body(
                        ResponseObject.builder()
                                .message("File is too large.")
                                .status(HttpStatus.BAD_REQUEST).build());
            } else {
                int totalImages = 0;
                int totalImageOfVariant = product.getImages().size();
                if (product.getImages() != null) totalImages = totalImageOfVariant + fileSize;

                if (totalImages > MAXIMUM_IMAGES_PER_PRODUCT) {
                    int images = Math.max(fileSize - totalImageOfVariant, 0);
                    ResponseEntity.badRequest().body(
                            ResponseObject.builder()
                                    .message(String.format(
                                            "File is larger than the maximum number of images per product.",
                                            images, totalImageOfVariant))
                                    .status(HttpStatus.BAD_REQUEST).build());
                }
            }


            List<ProductImage> images = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() == 0) continue;
                //kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) {// > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(
                            ResponseObject.builder().message(MessageKeys.FILE_IMAGE_LARGE)
                                    .status(HttpStatus.BAD_REQUEST).build());
                }

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body(ResponseObject.builder().message(MessageKeys.FILE_MUST_BE_IMAGE)
                                    .status(HttpStatus.BAD_REQUEST).build());
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = FileUtils.storeFile(file);
                //lưu vào đối tượng product trong DB
                ProductImage image = ProductImage.builder()
                        .imageUrl(filename)
                        .product(product)
                        .build();
                images.add(image);
            }

            productImageRepository.saveAll(images);

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(MessageKeys.UPLOAD_IMAGE_SUCCESSFULLY)
                    .status(HttpStatus.OK).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message(MessageKeys.UPLOAD_IMAGE_FAILED)
                            .status(HttpStatus.BAD_REQUEST).build());
        }
    }

    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_READ')")
    public ResponseEntity<ResponseObject> getAllVariants(@RequestParam(defaultValue = "") String name,
                                                         @RequestParam(defaultValue = "") String fragrance,
                                                         @RequestParam(defaultValue = "") String color,
                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        try {
            int totalPages = 0;
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<ProductResponse> variantPage = productService.getProducts(name, fragrance, color, pageRequest);
            totalPages = variantPage.getTotalPages();
            List<ProductResponse> variantResponses = variantPage.getContent();

            ListProductResponse listVariantResponse = ListProductResponse.builder()
                    .products(variantResponses)
                    .totalPages(totalPages).build();

            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(MessageKeys.GET_VARIANT_SUCCESSFULLY)
                    .status(HttpStatus.OK)
                    .data(listVariantResponse)
                    .build());
        } catch (
                Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message(MessageKeys.GET_VARIANT_FAILED)
                    .status(HttpStatus.OK)
                    .build());
        }
    }
}
