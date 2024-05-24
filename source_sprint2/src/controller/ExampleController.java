package controller;

import annotation.Get;
import annotation.MyAnnotation;


@MyAnnotation("exampleController")
public class ExampleController {

    @Get("/example")
    public void exampleMethod() {
        System.out.println("exampleMethod called");
    }
}

