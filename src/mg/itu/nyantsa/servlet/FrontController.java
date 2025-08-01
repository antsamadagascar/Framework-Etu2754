package mg.itu.nyantsa.servlet;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import mg.itu.nyantsa.controller.*;
import mg.itu.nyantsa.other.*;
import mg.itu.nyantsa.exception.AuthentificationException;
import mg.itu.nyantsa.exception.ValidationException;
import mg.itu.nyantsa.annotation.ValidateForm;
import mg.itu.nyantsa.auth.*;

@MultipartConfig
public class FrontController extends HttpServlet {

    private String controllerPackage;
    private ControllerScanner scanner;
    private List<Class<?>> controllers;
    private HashMap<String, Mapping> methodList;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controllerPackage = Utils.initializeControllerPackage(config);
        scanAndInitializeControllers();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            processRequest(request, response);
        } catch (NoSuchMethodException | ClassNotFoundException | IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchMethodException | ClassNotFoundException | IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException, NoSuchMethodException, ClassNotFoundException 
    {
        PrintWriter out = response.getWriter();

        try {
            HashMap<String, String> formData = Utils.getFormParameters(request);
            String relativeURI = Utils.getRelativeURI(request);
            
            Mapping mapping = methodList.get(relativeURI);
            if (mapping != null) {
                Class<?> controllerClass = Class.forName(mapping.getClassName());
                Method method = null;
                
                String httpMethod = request.getMethod();
                for (VerbAction verbAction : mapping.getVerbMethodes()) {
                    if (verbAction.getVerbe().equalsIgnoreCase(httpMethod)) {
                        method = Utils.findMethod(controllerClass, verbAction.getMethode());
                        break;
                    }
                }
                
                if (method != null) {
                    try {
                        AuthentificationInterceptor.validateAuthentification(method, controllerClass, request);
                    
                    } catch (AuthentificationException e) {
                        System.err.println("Message d'erreur dans l'authentification de la methode ou dela classe = " +e.getMessage());

                        request.getSession().setAttribute("requested_url", relativeURI);
                        response.sendRedirect(request.getContextPath() + "/login-page");
                        return;
                    }
                
                    Utils.displayDebugInfo(out, relativeURI, methodList);
                    Utils.displayFormData(out, formData); 
                    Utils.executeMappingMethod(relativeURI, methodList, out, request, response, formData);
                } else {
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (ValidationException ve) {
            ModelView errorView = ve.getModelView();
            Utils.handleModelView(errorView, request, response);
        } finally {
            out.close();
        }
    }

    // Section for "init()" Function 
    private void scanAndInitializeControllers() {
        try {

            this.scanner = new ControllerScanner();
            this.controllers = scanner.findControllers(controllerPackage);
            this.methodList = new HashMap<>();
            Utils.validateUniqueMappingValues(controllers);
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
                Utils.findMethodsAnnotated(controller, methodList);
            }
        } 
        else 
        {    System.out.println("No controllers found");    }
    }
}
