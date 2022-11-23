package com.example.springboot.service;

import com.example.springboot.model.Category;
import com.example.springboot.model.Enum.repository.CategoryRepository;
import com.example.springboot.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //  22/11/2022 - tao ham de tim category theo ca slug va id
    @Override
    public Category findCategoryBySlugAndId(String slug, Integer id) {
        return categoryRepository.findBySlugAndId(slug, id);
    }

    //  22/11/2022 - tao ham tim category theo slug hoac la id
    @Override
    public Category findCategoryBySlugOrId(String slug, Integer id) {
        return categoryRepository.findBySlugOrId(slug, id);
    }
}
