package com.example.demo.service;

import com.example.demo.entity.Categories;
import com.example.demo.generic.IService;

public interface CategoryService extends IService<Categories, Integer> {
    Categories update(Integer id, Categories category);
}
