package com.example.demo.repository;

import com.example.demo.entity.Products;
import com.example.demo.generic.IRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends IRepository<Products, Integer> {
    Page<Products> findAllByCategoryId(Integer categoryId, Pageable pageable);

    @NonNull
    Page<Products> findAll(@NonNull Pageable pageable);
}
