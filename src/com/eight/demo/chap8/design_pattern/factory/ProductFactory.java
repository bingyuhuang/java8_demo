package com.eight.demo.chap8.design_pattern.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {
    public static Product createProduct(String name) {
        switch(name) {
            case "loan": return new Loan();
            case "stock": return new Stock();
            case "bond": return new Bond();
            default:throw new RuntimeException("No such product" + name);
        }
    }

    public static void main(String[] args) {
        //创建一个产品
        Product p = ProductFactory.createProduct("loan");

        /**
         * 像引用方式一样引用构造函数
         */
    }

    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static{
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    public static Product createProductByLambda(String name) throws IllegalAccessException {
        Supplier<Product> p = map.get(name);
        if (p != null) return p.get();
        throw new IllegalAccessException("No such product" + name);
    }
}
