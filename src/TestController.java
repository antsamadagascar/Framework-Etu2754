package controller;

import annotation.Get;
import annotation.MyAnnotation;

@MyAnnotation(value = "")
public class TestController {
    String name ;

    @Get("/itu")
    public String getName() {
        return "Bienvenu dans le site de Itu!";
    }

    public void setName(String name) {
        this.name = name;
    }
}
