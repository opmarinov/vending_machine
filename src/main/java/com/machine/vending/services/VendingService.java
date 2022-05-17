package com.machine.vending.services;

import com.machine.vending.exceptions.VendingException;
import com.machine.vending.products.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class VendingService {

    @Getter
    @Setter
    private Double insertedCoins = 0.0;

    //TODO: extract in different class
    private final List<Double> validCoins = Arrays.asList(0.5, 1.0, 2.0);

    @Autowired
    private ProductService productService;

    public void insertCoins(Double coins) {

        var areValidCoins = validateCoins(coins);

        if (!areValidCoins) {
            var msg = String.format("%s coins are not accepted. Accepted coins are 50st, 1lv, 2lv", coins);
            throw new VendingException(msg);
        }

        var insertedCoins = getInsertedCoins();

        setInsertedCoins(insertedCoins + coins);
    }

    //TODO: extract in different class
    private boolean validateCoins(Double coins) {
        return validCoins.contains(coins);
    }

    public void resetCoins() {
        this.setInsertedCoins(0.0);
    }

    public Product buyProduct(String name) throws VendingException {
        var product = productService.getProductByName(name);

        var price = product.getPrice();

        var availableCoins = getInsertedCoins();

        if (availableCoins == 0.0) {
            throw new VendingException("Please insert coins in order to buy a product.");
        }

        if (price > availableCoins) {
            throw new VendingException("Inserted coins are not enough to buy the product.");
        }

        return product;
    }
}
