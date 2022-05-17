package com.machine.vending.services;

import com.machine.vending.exceptions.VendingException;
import com.machine.vending.products.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    //TODO: maybe a map would have been better
    @Getter
    @Setter
    private List<Product> products = new ArrayList<>();

    @PostConstruct
    private void init() {
        var cola = new Product("CocaCola", 2.0);
        var moreni = new Product("Moreni", 0.5);
        var sluncho = new Product("Sluncho", 1.5);

        var batchOfProducts = Arrays.asList(cola, moreni, sluncho);

        products.addAll(batchOfProducts);
    }

    public void add(Product product) {
        products.add(product);
    }

    public void update(Product product) throws VendingException {
        var index = products.indexOf(product);

        var name = product.getName();

        remove(name);

        products.add(index, product);
    }

    public void remove(String name) throws VendingException {

        if (name == null) {
            throw new VendingException("No product was selected");
        }

        var product = getProductByName(name);

        products.remove(product);
    }

    public Product getProductByName(String name) throws VendingException {
        var optionalProduct = findProductByName(name);

        if (optionalProduct.isEmpty()) {
            String msg = "Product not found.";
            throw new VendingException(msg);
        }

        return optionalProduct.get();
    }

    //TODO: maybe create an inventory service to store products and search products.
    private Optional<Product> findProductByName(String name) {
        Integer index = null;

        for (Product product : products) {
            boolean selected = name.equals(product.getName());

            if (selected) {
                index = products.indexOf(product);
                break;
            }
        }

        if (index == null) {
            return Optional.empty();
        }

        var product = products.get(index);

        return Optional.of(product);
    }
}
