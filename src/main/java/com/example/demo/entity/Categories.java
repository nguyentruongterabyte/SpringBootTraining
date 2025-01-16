package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Categories {
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name= "name", nullable = false, length = 255)
    private String name;

    @Basic
    @Column(name="description", length = -1)
    private String description;
}
