package com.example.demo.controller;

import com.example.demo.dto.RequestResponse;
import com.example.demo.dto.request.ProductDTO;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.exception.ErrorHandler;
import com.example.demo.exception.ExceptionResponse;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(
            ProductService productService,
            CategoryService categoryService
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // Create a new product
    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @RequestBody ProductDTO productDTO
    ) {

        try {
            Categories category = categoryService.findOne(
                    productDTO.getCategory_id()
            );

            Products product = new Products();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category);
            productService.save(product);
            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Product created successfully",
                            product));
        } catch (ErrorHandler e) {
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            e.getMessage()),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    "Failed to add product: "
                            + e.getMessage());
        }
    }

    // Get a list of all products
    // Get a paginated list of products
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        try {

            if (page != null && size != null) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Products> productsPage = productService.getAllProducts(pageable);
                return ResponseEntity.ok(
                        new RequestResponse(
                                LocalDateTime.now().toString(),
                                "Products fetched successfully (paginated)",
                                productsPage
                        )
                );
            } else {
                Iterator<Products> products = productService.findAll();
                return ResponseEntity.ok(
                        new RequestResponse(
                                LocalDateTime.now().toString(),
                                "All products fetched successfully",
                                products
                        )
                );
            }

        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    "Failed to add category: " +
                            e.getMessage());
        }
    }

    // Get details of a specific product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(
            @PathVariable Integer productId
    ) {
        try {
            Products product = productService.findOne(productId);
            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Product retrieved successfully",
                            product
                    )
            );
        } catch (ErrorHandler e) {
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            e.getMessage()),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    // Update a product by ID
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductDTO productDTO
    ) {
        try {
            Products updatedProduct = productService.update(
                    productId,
                    productDTO
            );

            return ResponseEntity.ok(new RequestResponse(
                    LocalDateTime.now().toString(),
                    "Product updated successfully",
                    updatedProduct
            ));

        } catch (ErrorHandler e) {
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            e.getMessage()
                    ),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Integer productId
    ) {
        try {
            productService.delete(productId);
            return ResponseEntity.ok(
                    new RequestResponse(
                            "Product deleted successfully")
            );
        } catch (ErrorHandler e) {
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            e.getMessage()),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    // Update the category of a product
    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> updateCategoryOfProduct(
            @PathVariable Integer productId,
            @PathVariable Integer categoryId
    ) {
        try {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setCategory_id(categoryId);

            Products updatedProduct = productService.update(
                    productId,
                    productDTO
            );

            return ResponseEntity.ok(new RequestResponse(
                    LocalDateTime.now().toString(),
                    "Category of product updated successfully",
                    updatedProduct
            ));

        } catch (ErrorHandler e) {
            return new ResponseEntity<>(
                    new ExceptionResponse(
                            e.getMessage()),
                    e.getStatus()
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}
