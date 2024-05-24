package other;

import controller.FrontController;

import javax.servlet.ServletException;

public class Main {
    public static void main(String[] args) {
        FrontController frontController = new FrontController();
        try {
            frontController.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        // Utilisez la méthode doGet(HttpServletRequest request, HttpServletResponse response)
        // de FrontController pour simuler une requête GET
        // Créez une instance de HttpServletRequest avec les informations nécessaires
        // Créez une instance de HttpServletResponse pour capturer la réponse
        // Appelez frontController.doGet(request, response) pour simuler la requête
    }
}
