package mg.itu.nyantsa.auth;

import mg.itu.nyantsa.annotation.auth.Authentification;
import mg.itu.nyantsa.auth.AuthentificationManager;
import mg.itu.nyantsa.exception.AuthentificationException;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AuthentificationInterceptor {
    
    public static void validateAuthentification(Method method, Class<?> clazz, HttpServletRequest request) 
        throws AuthentificationException 
    {
        // Vérifier d'abord l'annotation au niveau de la classe
        Authentification classAuth = clazz.getAnnotation(Authentification.class);
        Authentification methodAuth = method.getAnnotation(Authentification.class);
        
        // Si la méthode a ignoreAuth=true, on ignore toute authentification
        if (methodAuth != null && methodAuth.ignoreAuth()) {
            return;
        }
        
        // Si ni la classe ni la méthode n'ont d'annotation, pas de vérification
        if (classAuth == null && methodAuth == null) {
            return;
        }
        
        // Utiliser l'annotation de la méthode si elle existe, sinon celle de la classe
        Authentification effectiveAuth = (methodAuth != null) ? methodAuth : classAuth;
        
        // Vérifier si l'utilisateur est authentifié
        if (!AuthentificationManager.isAuthenticated(request)) {
            throw new AuthentificationException("User must be authenticated to access this resource");
        }
        
        // Vérifier le rôle si spécifié
        String requiredRole = effectiveAuth.value();
        if (!AuthentificationManager.hasRole(request, requiredRole)) {
            throw new AuthentificationException("User does not have the required role: " + requiredRole);
        }
    }
}
