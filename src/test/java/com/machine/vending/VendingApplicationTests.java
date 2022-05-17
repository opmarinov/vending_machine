package com.machine.vending;

import com.machine.vending.products.Product;
import com.machine.vending.services.ProductService;
import com.machine.vending.services.VendingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VendingApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private VendingService vendingService;

    @Test
    void testProductSize_withThreeProducts_ExpectedNotEmpty() {
        var products = productService.getProducts();

        assertFalse(products.isEmpty());
    }

    @Test
    void testProductSize_withThreeProducts_ExpectedThreeProducts() {
        var products = productService.getProducts();

        assertEquals(3, products.size());
    }

    @Test
    void testAddingProducts_withThreePreAddedProducts_ExpectedFourProducts() {
        var product = new Product("test_product", 3.0);

        productService.add(product);

        var products = productService.getProducts();

        assertEquals(4, products.size());
    }

    @Test
    void testUpdatingProducts_withThreePreAddedProducts_ExpectedUpdatedProducts() {

        var product = productService.getProductByName("CocaCola");

        //TODO: due to inflation
        var newPrice = 10.0;

        product.setPrice(newPrice);
        productService.update(product);

        product = productService.getProductByName("CocaCola");;

        assertEquals(newPrice, product.getPrice());
    }

    @Test
    void testProductCurrency_withTestProduct_ExpectedLVCurrency() {
        var product = new Product("test_product", 3.0);

        var priceWithCurrency = product.getPriceWithCurrency();

        assertTrue(priceWithCurrency.contains("lv"));
    }

    @Test
    void testProductCurrency_withTestProduct_ExpectedSTCurrency() {
        var product = new Product("test_product", 0.2);

        var priceWithCurrency = product.getPriceWithCurrency();

        assertTrue(priceWithCurrency.contains("st"));
    }

    @Test
    void testInsertedCoins_withZeroAtStart_Expected20() {
        var expectedCoins = 0.5;

        vendingService.insertCoins(expectedCoins);
        vendingService.insertCoins(expectedCoins);

        assertEquals(expectedCoins * 2, vendingService.getInsertedCoins());
    }
}
