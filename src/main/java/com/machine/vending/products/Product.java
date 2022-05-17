package com.machine.vending.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private static final String LV = "lv";
    private static final String ST = "st";

    private String name;
    private Double price;

    @JsonIgnore
    public String getPriceWithCurrency() {
        var currency = this.getPrice() > 1 ? LV : ST;

        return String.format("%s%s", this.getPrice(), currency);
    }
}
