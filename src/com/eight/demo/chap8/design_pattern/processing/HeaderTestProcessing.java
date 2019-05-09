package com.eight.demo.chap8.design_pattern.processing;

public class HeaderTestProcessing extends ProcessingObject<String>{
    @Override
    protected String handleWork(String input) {
        return "From Raoul, Mario and Alan: " + input;
    }
}
