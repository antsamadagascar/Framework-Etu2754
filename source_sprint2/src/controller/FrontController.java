package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import annotation.Get;
import other.Mapping;

@WebServlet("/")
public class FrontController extends HttpServlet {

    private HashMap<String, Mapping> urlMappings = new HashMap<>();

    @Override
    public void init() throws ServletException {
        ControllerScanner scanner = new ControllerScanner();
        try {
            List<Class<?>> controllers = scanner.findControllers("controller");
            for (Class<?> controller : controllers) {
                Method[] methods = controller.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Get.class)) {
                        Get getAnnotation = method.getAnnotation(Get.class);
                        String url = getAnnotation.value();
                        urlMappings.put(url, new Mapping(method.getName(), controller.getName()));
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException("Failed to initialize FrontController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath(); 
        Mapping mapping = urlMappings.get(path);
        if (mapping != null) {
            response.getWriter().println("URL: " + path);
            response.getWriter().println("Mapping: " + mapping);
        } else {
            response.getWriter().println("Aucune méthode associée à ce chemin URL: " + path);
        }
    }
}
