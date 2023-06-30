package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

// add the annotations to make this a REST controller @
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories @
// add annotation to allow cross site origin requests@


@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoriesController{

    private CategoryDao categoryDao;
    private ProductDao productDao;

    // create an Autowired controller to inject the categoryDao and ProductDao @
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao){


        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // Add the appropriate annotation for a GET action
    @GetMapping("/categories")
    public List<Category> getAll() {
        // Find and return all categories
        List<Category> categories = categoryDao.getAllCategories();
        return categories;
    }

    // Add the appropriate annotation for a GET action
    @GetMapping("/categories/{id}")
    public Category getById(@PathVariable int categoryId) {
        // Get the category by id
        Category category = categoryDao.getById(categoryId);
        System.out.println("Category id: " + categoryId);
        return category;
    }

    // The URL to return all products in category 1 would look like this:
    // https://localhost:8080/categories/1/products
    @GetMapping("/categories/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // Get a list of products by categoryId
        List<Product> products = productDao.listByCategoryId(categoryId);
        return products;
    }

    // Add annotation to call this method for a POST action
    // Add annotation to ensure that only an ADMIN can call this function
    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        // Insert the category
        categoryDao.create(category);
        return category;
    }

    // Add annotation to call this method for a PUT (update) action - the URL path must include the categoryId
    // Add annotation to ensure that only an ADMIN can call this function
    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HashMap<String, String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        // Update the category by id
        categoryDao.update(id, category);

        HashMap<String, String> updateRequest = new HashMap<>();

        updateRequest.put("Message", "A category has been updated!");

        return updateRequest;
    }

    // Add annotation to call this method for a DELETE action - the URL path must include the categoryId
    // Add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HashMap<String, String> deleteCategory(@PathVariable int id) {
        // Delete the category by id
        categoryDao.delete(id);
        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put("Message", "A category has been deleted!");

        return deleteRequest;
    }
}

