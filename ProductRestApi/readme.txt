Overview
This is a simple Spring Boot RESTful API for managing products in an inventory system. The API provides CRUD
(Create, Read, Update, Delete) operations for handling products, allowing users to create new products, fetch existing
products, update product details, and delete products from the system.

Product Controller:

POST /api/v1/product - Create a new product.
GET /api/v1/products - Retrieve a list of all products.
GET /api/v1/products/{id} - Retrieve a product by its ID.
PUT /api/v1/products/{id} - Update an existing product by its ID.
DELETE /api/v1/products/{id} - Delete a product by its ID.

The Product class represents the product entity in the inventory management system. It is a JPA entity, meaning it is
mapped to a database table. The class defines the attributes of a product, including its ID, name, price, and quantity.

The ProductService class contains business logic for managing products in the inventory system. It serves as an
intermediary between the ProductController and ProductRepository, handling operations like saving, updating, and
deleting products. The service uses the ProductRepository for database interactions and ensures that operations such as
updating or deleting a product are done with proper transactional handling.

The GlobalExceptionHandler class is a centralized exception handler in a Spring Boot application.
It handles exceptions thrown by the application and returns appropriate HTTP responses. The
@RestControllerAdvice annotation ensures that exceptions are handled globally across all @RequestMapping methods
in controllers.

This class is used to catch specific exceptions (such as ResourceNotFoundException) and return a custom response
with the appropriate HTTP status code.