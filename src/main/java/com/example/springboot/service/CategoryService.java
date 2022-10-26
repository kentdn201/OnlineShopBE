package com.example.springboot.service;

import com.example.springboot.model.Category;
import com.example.springboot.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Category updateCategory(Category category)
    {
        Category existCategory = categoryRepository.findById(category.getId()).orElse(null);
        existCategory.setName(category.getName());
        existCategory.setSlug(category.getSlug());
        return categoryRepository.save(existCategory);
    }

    public String deleteCategory(int id)
    {
        categoryRepository.deleteById(id);
        return "Delete Success: category id" + " " + id;
    }

}
