package com.example.productrestapi.Service;

import com.example.productrestapi.Exception.ResourceNotFoundException;
import com.example.productrestapi.Model.Product;
import com.example.productrestapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Method to save product to database
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(int id, Product product) {
        Optional<Product> existingProductOpt = productRepository.findById((long) id);
        if (!existingProductOpt.isPresent()) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        Product existingProduct = existingProductOpt.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        // Make sure all fields are updated as needed

        return productRepository.save(existingProduct); // This should persist the updated product
    }

    // Method to delete product
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById((long) id);
    }

}