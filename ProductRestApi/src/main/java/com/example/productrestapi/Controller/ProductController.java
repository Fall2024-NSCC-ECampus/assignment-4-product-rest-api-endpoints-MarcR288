package com.example.productrestapi.Controller;

import com.example.productrestapi.Exception.ResourceNotFoundException;
import com.example.productrestapi.Model.Product;
import com.example.productrestapi.Repository.ProductRepository;
import com.example.productrestapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productRepository.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productRepository.findById((long) id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        // Retrieve the existing product from the database
        Optional<Product> existingProductOpt = productRepository.findById((long) id);

        if (!existingProductOpt.isPresent()) {
            // If the product is not found, throw the custom exception
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        Product existingProduct = existingProductOpt.get();

        // Update the fields of the existing product
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        // Save the updated product back to the database
        Product updatedProduct = productRepository.save(existingProduct);

        // Return the updated product with a 200 OK response
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        // Check if the product exists in the database
        Optional<Product> productOpt = productRepository.findById((long) id);

        if (!productOpt.isPresent()) {
            // If the product does not exist, return a 404 NOT FOUND status
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        // Product exists, now delete it
        productRepository.deleteById((long) id);

        // Return a success message with a 200 OK status
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }
}
