package controller;

import annotation.MyAnnotation;

@MyAnnotation(value = "Controller")
public class TestController {
    String name ;

    // @Get("/mg-get")
    public String getOther() {
        return "Okay";
    }

    
    public void setName(String name) {
        this.name = name;
    }
    
}
