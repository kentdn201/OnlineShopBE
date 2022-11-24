package com.example.springboot.service;

import com.example.springboot.config.ObjectMapperUtils;
import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Category;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.repository.ProductRepository;
import com.example.springboot.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category findBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(String slug, Category category) {
        Category existCategory = categoryRepository.findBySlug(slug);
        existCategory.setName(category.getName());
        existCategory.setSlug(category.getSlug());
        existCategory.setImage(category.getImage());
        return categoryRepository.save(existCategory);
    }

    public String deleteCategory(Integer id) {
        categoryRepository.deleteCategoryById(id);

        return "Delete Success: category id" + " " + id;
    }

    public List<ProductDto> getProductsByCategorySlug(String slug) {
        List<Product> products = productRepository.getProductsByCategorySlug(slug);
        List<ProductDto> productDtos = ObjectMapperUtils.mapAll(products, ProductDto.class);

        return productDtos;
    }
}
