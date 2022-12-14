package com.example.springboot.service;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.dto.order.ProductDetailDto;
import com.example.springboot.model.exceptions.CustomException;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    ModelMapper modelMapper = new ModelMapper();

    public String createProduct(ProductDto productDto, Category category) {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setImageURL(productDto.getImageURL());
            product.setDescription(productDto.getDescription());
            product.setSlug(productDto.getSlug());
            product.setCategory(category);
            productRepository.save(product);
            return "Create Success";
        } catch (Exception e) {
            return "This slug is available or category is not available";
        }
    }


    public ProductDto getProductDto(Product product) {
        //      mapping dto to entity
        ProductDto productDto1 = modelMapper.map(product, ProductDto.class);
        return productDto1;
    }

    public ProductDto getProductDtoBySlug(String slug) {
        //      mapping dto to entity
        Product product = productRepository.findBySlug(slug);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }

    public void updateProduct(ProductDto productDto, String slug) throws Exception {
        Product existProduct = productRepository.findBySlug(slug);
        if (existProduct == null) {
            throw new Exception("S???n ph???m kh??ng t???n t???i");
        }

        Integer categoryId = productDto.getCategoryId();

        if (categoryId == null) {
            throw new Exception("Danh M???c Kh??ng T???n T???i");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        Category existCategory = categoryRepository.findBySlug(category.get().getSlug());

        Product product = existProduct;
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setSlug(productDto.getSlug());
        product.setCategory(existCategory);
        productRepository.save(product);
    }

    public Product getProductBySlug(String slug) {
        Product existProduct = productRepository.findBySlug(slug);
        return existProduct;
    }

    public String deleteProduct(String slug) throws Exception {
        try {
            Product existProduct = productRepository.findBySlug(slug);
            if (existProduct == null) {
                return "This product is not available to delete";
            }
            productRepository.deleteById(existProduct.getId());
            return "Delete success " + "product id is: " + existProduct.getId();
        } catch (Exception e)
        {
            return "Can not delete this product has been ordered";
        }
    }

    public Product findById(Integer productId) {
        Optional<Product> existProduct = productRepository.findById(productId);
        if (existProduct.isEmpty()) {
            throw new CustomException("S???n ph???m c?? m?? l??: " + productId + " kh??ng t???n t???i");
        }
        return existProduct.get();
    }

    public Product findBySlugOrId(String slug, Integer id) {
        return productRepository.findBySlugOrId(slug, id);
    }

    public ProductDetailDto getProductDetailDtoById(Integer productId) {
        String slug = "";
        Product product = productRepository.findBySlugOrId(slug, productId);
//      mapping dto to entity
        ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
        return productDetailDto;
    }

    public List<ProductDto> searchProducts(String keyword) {
        List<ProductDto> productDtos = getAllProducts();
        List<ProductDto> newProductDtos = new ArrayList<>();
        if (keyword != null) {
            for (ProductDto dto : productDtos) {
                if (dto.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    newProductDtos.add(dto);
                }
            }
            return newProductDtos;
        }

        return productDtos;
    }
}