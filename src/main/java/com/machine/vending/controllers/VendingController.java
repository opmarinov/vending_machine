package com.machine.vending.controllers;

import com.machine.vending.exceptions.VendingException;
import com.machine.vending.products.Product;
import com.machine.vending.services.VendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vending")
public class VendingController {

    @Autowired
    private VendingService vendingService;

    @GetMapping("/buy/product")
    public Product buy(@RequestParam String name) throws VendingException {
        return vendingService.buyProduct(name);
    }

    //TODO: these could be in a different controller
    @PostMapping("/insert/coins")
    public void insert(@RequestBody Double coins) {
        vendingService.insertCoins(coins);
    }

    @DeleteMapping("/reset/coins")
    public void reset() {
        vendingService.resetCoins();
    }
}
