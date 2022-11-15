package com.example.springboot.service;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.dto.order.ProductDetailDto;
import com.example.springboot.exceptions.CustomException;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.repository.ProductRepository;
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

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setSlug(productDto.getSlug());
        product.setCategory(category);
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product)
    {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageURL());
        productDto.setSlug(product.getSlug());
        productDto.setCategoryId(product.getCategory().getId());
        return  productDto;
    }

    public ProductDto getProductDtoBySlug(String slug)
    {
        Product product = productRepository.findBySlug(slug);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageURL());
        productDto.setSlug(product.getSlug());
        productDto.setCategoryId(product.getCategory().getId());
        return  productDto;
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product: products) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }

    public void updateProduct(ProductDto productDto, String slug) throws Exception {
        Product existProduct = productRepository.findBySlug(slug);
        if(existProduct == null){
            throw new Exception("Sản phẩm không tồn tại");
        }

        Integer categoryId = productDto.getCategoryId();

        if(categoryId == null){
            throw new Exception("Danh Mục Không Tồn Tại");
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

    public void deleteProduct(String slug) throws Exception {
        Product existProduct = productRepository.findBySlug(slug);
        if(existProduct == null){
            throw new Exception("Sản phẩm không tồn tại");
        }
        productRepository.deleteById(existProduct.getId());
    }

    public Product findById(Integer productId) {
        Optional<Product> existProduct = productRepository.findById(productId);
        if(existProduct.isEmpty())
        {
            throw new CustomException("Sản phẩm có mã là: " + productId + " không tồn tại");
        }
        return existProduct.get();
    }

    public ProductDetailDto getProductDetailDtoById(Integer productId)
    {
        Optional<Product> product = productRepository.findById(productId);
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(product.get().getId());
        productDetailDto.setName(product.get().getName());
        productDetailDto.setImageURL(product.get().getImageURL());
        productDetailDto.setSlug(product.get().getSlug());
        return  productDetailDto;
    }

    public List<ProductDto> searchProducts(String keyword)
    {
        List<ProductDto> productDtos = getAllProducts();
        List<ProductDto> newProductDtos = new ArrayList<>();
        if(keyword != null)
        {
            for (ProductDto dto: productDtos)
            {
                if(dto.getName().toLowerCase().contains(keyword.toLowerCase()))
                {
                    newProductDtos.add(dto);
                }
            }
            return newProductDtos;
        }

        return productDtos;
    }
}
