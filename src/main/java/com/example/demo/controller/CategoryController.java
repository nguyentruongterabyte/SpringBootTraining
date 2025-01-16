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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // Create a new category
    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @RequestBody Categories category
    ) {
        try {
            categoryService.save(category);
            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Category created successfully",
                            category
                    )
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    "Failed to add category: "
                            + e.getMessage());
        }
    }

    // Get a list of all categories
    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        try {
            Iterator<Categories> categories =
                    categoryService.findAll();
            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Successfully",
                            categories
                    )
            );
        } catch (Exception e) {
            throw new ErrorHandler(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    // Get details of a specific category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Integer categoryId
    ) {
        try {
            Categories category = categoryService.findOne(categoryId);
            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Category retrieved successfully",
                            category)
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

    // Update a category by ID
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody Categories category
    ) {
        try {
            Categories updatedCategory = categoryService.update(
                    categoryId,
                    category
            );

            return ResponseEntity.ok(new RequestResponse(
                    LocalDateTime.now().toString(),
                    "Category updated successfully",
                    updatedCategory
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

    // Delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Integer categoryId
    ) {
        try {
            categoryService.delete(categoryId);
            return ResponseEntity.ok(
                    new RequestResponse(
                            "Category deleted successfully")
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

    // Get a list of products by category (with pagination)
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {
        try {
            PageRequest pageable = PageRequest.of(page, size);
            Page<Products> productsPage = productService
                    .findByCategoryId(categoryId, pageable);
            return ResponseEntity.ok(new RequestResponse(
               LocalDateTime.now().toString(),
               "Products fetched successfully",
               productsPage
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

    // Add a product to a specific category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<?> addProductToCategory(
            @PathVariable Integer categoryId,
            @RequestBody ProductDTO productDTO
    ) {
        try {
            // Get details of a specific category by ID
            Categories category = categoryService.findOne(categoryId);

            Products product = new Products();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setCategory(category);

            productService.save(product);

            return ResponseEntity.ok(
                    new RequestResponse(
                            LocalDateTime.now().toString(),
                            "Product added to category successfully",
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
                    e.getMessage()
            );
        }
    }
}
