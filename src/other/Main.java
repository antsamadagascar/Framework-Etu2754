package other;

import controller.ControllerScanner;

import java.lang.reflect.Method;

import annotation.Get;

public class Main {
    public static void main(String[] args) {
         // Obtient toutes les méthodes de la classe MyService
        Method[] methods = ControllerScanner.class.getDeclaredMethods();

        for (Method method : methods) {
            // Vérifie si la méthode est annotée avec @Get
            if (method.isAnnotationPresent(Get.class)) {
                // Obtient l'annotation @Get
                Get getAnnotation = method.getAnnotation(Get.class);
                // Affiche les informations de l'annotation
                System.out.println("Method: " + method.getName() + ", Path: " + getAnnotation.value());
            }
        }
    }
}
