package com.machine.vending.controllers;

import com.machine.vending.exceptions.VendingException;
import com.machine.vending.products.Product;
import com.machine.vending.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getProducts();
    }

    @GetMapping("{name}")
    public Product get(@PathVariable String name)  throws VendingException {
        return productService.getProductByName(name);
    }

    @PostMapping
    public void add(@RequestBody Product product) {
        productService.add(product);
    }

    @PutMapping
    public void update(@RequestBody Product product) throws VendingException {
        productService.update(product);
    }

    @DeleteMapping("/{name}")
    public void remove(@PathVariable String name) throws VendingException {
        productService.remove(name);
    }
}
