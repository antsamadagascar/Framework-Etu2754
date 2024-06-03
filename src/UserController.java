package controller;

import annotation.Get;
import annotation.MyAnnotation;
import other.ModelView;

@MyAnnotation(value = "")
public class UserController {
    String name ;

    @Get("/mg-get")
    public String getName() {
        return "Bonjour.";
    }

    public void setName(String name) {
        this.name = name;
    }
    @Get("/mv-view") 
    public ModelView getView() {
         ModelView mv = new ModelView();

        // Définition de l'URL
        mv.setUrl("view.jsp");

        // Ajout de données
        mv.add("username", "john_doe");
        mv.add("age", 30);
        mv.add("isLoggedIn", true);

        return mv;
    }
}
