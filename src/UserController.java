package controller;

import annotation.Get;
import annotation.MyAnnotation;
import annotation.Param;
import other.ModelView;

@MyAnnotation(value = "Controller")
public class UserController {
    String name ;

    @Get("/mg-get")
    public String getName() {
        return "Bonjour.";
    }
    
    public void setName() {
        this.name = name;
    }
    @Get("/mg-number")
    public int getNumber() {
        return 1;
    }

    @Get("/mv-view2") 
    public ModelView getNotView() {
         ModelView mv = new ModelView();

        // Définition de l'URL
        mv.setUrl("view1.jsp");

        // Ajout de données
        mv.add("username", "john_doe");
        mv.add("age", 30);
        mv.add("isLoggedIn", true);

        return mv;
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
    
    @Get("/user-info")
    public ModelView getUserInfo(@Param(name = "username") String username, @Param(name = "age") int age) {
        ModelView modelView = new ModelView(); 
        modelView.setUrl("view.jsp");
        modelView.add("username" , username);
        modelView.add("age" , age); 
        
        return modelView;
    }
}
