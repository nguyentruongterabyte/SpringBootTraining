package com.example.demo.dto.request;

import com.example.demo.entity.Products;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDTO extends Products {
    private Integer category_id;
}
