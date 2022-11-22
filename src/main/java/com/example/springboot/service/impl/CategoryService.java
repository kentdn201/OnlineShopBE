package com.example.springboot.service.impl;

import com.example.springboot.model.Category;

import java.util.List;

public interface CategoryService {
    Category findCategoryBySlugAndId(String slug, Integer id);
    Category findCategoryBySlugOrId(String slug, Integer id);
}
