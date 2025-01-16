package com.example.demo.service.impl;

import com.example.demo.dto.request.ProductDTO;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.exception.ErrorHandler;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoriesRepository categoriesRepository) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Products update(Integer id, ProductDTO product) {
        try {
            // Kiểm tra xem product có tồn tại không
            Products existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ErrorHandler(
                            HttpStatus.NOT_FOUND,
                            "Product with ID " + id + " not found")
                    );

            // Chỉ cập nhật những trường được cung cấp
            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }

            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }

            if (product.getDescription() != null) {
                existingProduct.setDescription(product.getDescription());
            }

            if (product.getCategory_id() != null) {
                // Kiểm tra và lấy category từ category_id
                Integer categoryId = product.getCategory_id();
                Categories category = categoriesRepository.findById(categoryId)
                        .orElseThrow(() -> new ErrorHandler(
                                        HttpStatus.NOT_FOUND,
                                        "Category with ID " + categoryId + " not found")
                        );

                existingProduct.setCategory(category);
            }

            return productRepository.save(existingProduct);

        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Page<Products> findByCategoryId(Integer categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Products> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void save(Products products) {
        try {
            productRepository.save(products);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ErrorHandler(
                        HttpStatus.NOT_FOUND,
                        "Product with ID " + id + " not found"
                );

            }

            productRepository.deleteById(id);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Iterator<Products> findAll() {
        try {
            return productRepository.findAll().iterator();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Products findOne(Integer id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(
                            () -> new ErrorHandler(
                                    HttpStatus.NOT_FOUND,
                                    "Product with ID " + id + " not found")
                    );
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
