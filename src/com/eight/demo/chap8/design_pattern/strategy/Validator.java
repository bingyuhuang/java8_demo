package com.eight.demo.chap8.design_pattern.strategy;

public class Validator {
    private final ValidationStrategy strategy;


    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }


    public static void main(String[] args) {
        Validator validator = new Validator(new IsAllLowerCase());
        System.out.println(validator.validate("aaa"));

        Validator validator1 = new Validator(new IsNumeric());
        System.out.println(validator1.validate("bbb"));


        /**
         * 使用Lambda表达式 T-boolean Predicate<String>
         */
        Validator validator2 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(validator2.validate("aaa"));

        Validator validator3 = new Validator((String s) -> s.matches("//d+"));
        System.out.println(validator3.validate("bbb"));
    }
}
