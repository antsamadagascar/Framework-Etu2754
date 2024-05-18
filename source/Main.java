package other;

import controller.ControllerScanner;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ControllerScanner scanner = new ControllerScanner();
            List<Class<?>> controllerClasses = scanner.findControllers("controller");

            System.out.println("Classes detected:");
            for (Class<?> clazz : controllerClasses) {
                System.out.println(clazz.getName());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}

