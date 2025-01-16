package com.example.demo.service;

import com.example.demo.dto.request.ProductDTO;
import com.example.demo.entity.Products;
import com.example.demo.generic.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends IService<Products, Integer> {
    Products update(Integer id, ProductDTO product);
    Page<Products> findByCategoryId(
            Integer categoryId,
            Pageable pageable
    );

    Page<Products> getAllProducts(Pageable pageable);
}
