package com.example.demo.service.impl;

import com.example.demo.entity.Categories;
import com.example.demo.exception.ErrorHandler;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Categories update(Integer id, Categories category) {
        try {
            // Kiểm tra xem category có tồn tại không
            Categories existingCategory = categoriesRepository.findById(id)
                    .orElseThrow(() -> new ErrorHandler(
                            HttpStatus.NOT_FOUND,
                            "Category with ID " + id + " not found")
                    );

            // Chỉ cập nhật những trường được cung cấp
            if (category.getName() != null) {
                existingCategory.setName((category.getName()));
            }

            if (category.getDescription() != null) {
                existingCategory.setDescription(category.getDescription());
            }

            return categoriesRepository.save(existingCategory);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void save(Categories categories) {
        try {
            categoriesRepository.save(categories);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!categoriesRepository.existsById(id)) {
                throw new ErrorHandler(
                        HttpStatus.NOT_FOUND,
                        "Category with ID " + id + " not found"
                );
            }

            categoriesRepository.deleteById(id);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Iterator<Categories> findAll() {
        try {
            return categoriesRepository.findAll().iterator();
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Categories findOne(Integer id) {
        try {
            return categoriesRepository.findById(id)
                    .orElseThrow(
                            () -> new ErrorHandler(
                                    HttpStatus.NOT_FOUND,
                                    "Category with ID " + id + " not found")
                    );
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
