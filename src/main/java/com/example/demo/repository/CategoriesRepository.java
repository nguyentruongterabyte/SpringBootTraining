package com.example.demo.repository;

import com.example.demo.entity.Categories;
import com.example.demo.generic.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends IRepository<Categories, Integer> {}
