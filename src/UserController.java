package controller;

import annotation.Get;
import annotation.MyAnnotation;

@MyAnnotation(value = "")
public class UserController {
    String name ;

    @Get("/mg-get")
    public String getName() {
        return "Bonjour tous le monde.";
    }

    public void setName(String name) {
        this.name = name;
    }
}
