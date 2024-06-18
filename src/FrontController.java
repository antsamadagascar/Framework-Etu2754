package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import annotation.AnnotationController;
import controller.*;

public class FrontController extends HttpServlet {

    private String controllerPackage;
    private ControllerScanner scanner;
    private List<Class<?>> controllers;
    private HashMap<String, Mapping> methodList;

    @Override
    public void init(ServletConfig config) 
            throws ServletException {
        super.init(config);
        initializeControllerPackage(config);
        scanAndInitializeControllers();
    }

    private void initializeControllerPackage(ServletConfig config) 
            throws ServletException {
        controllerPackage = config.getInitParameter("base_package");
        if (controllerPackage == null) {
            throw new ServletException("Base package is not specified in web.xml");
        }
    }

    private void scanAndInitializeControllers() {
        try {
            this.scanner = new ControllerScanner();
            this.controllers = scanner.findControllers(controllerPackage);
            this.methodList = new HashMap<>();
            initMethodList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Initialization failed", e);
        }
    }

    private void initMethodList() {
        if (this.controllers != null) {
            for (Class<?> controller : this.controllers) {
                System.out.println("Scanning controller: " + controller.getName());
                if (controller.isAnnotationPresent(AnnotationController.class)) {
                    findMethodsAnnotated(controller);
                }
            }
        } else {
            System.out.println("No controllers found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            displayDebugInfo(out);
        } finally {
            out.close();
        }
    }

    private void displayDebugInfo(PrintWriter out) {
        out.println("<h1>Framework</h1>");
        out.println("<h2>You are here now:</h2>");
        out.println("<h3>Controllers found:</h3>");
        if (this.controllers != null && !this.controllers.isEmpty()) {
            for (Class<?> controller : controllers) {
                out.println("<p>" + controller.getName() + "</p>");
            }
        } else {
            out.println("<p>No controllers found</p>");
        }
    }

    public void findMethodsAnnotated(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Get.class)) {
                Get getAnnotation = method.getAnnotation(Get.class);

            }
        }
    }

}
