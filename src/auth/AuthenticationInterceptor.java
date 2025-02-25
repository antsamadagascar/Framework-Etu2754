package auth;

import annotation.auth.Authentication;
import exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class AuthenticationInterceptor {
    
    public static void validateAuthentication(Method method, HttpServletRequest request) 
        throws AuthenticationException 
    {
        Authentication methodAuth = method.getAnnotation(Authentication.class);
        
        if (methodAuth != null && methodAuth.ignoreAuth()) {
            return; // Auth désactivée pour cette méthode
        }

        if (methodAuth == null) {
            return; // Pas de restriction d'accès
        }

        if (!AuthenticationManager.isAuthenticated(request)) {
            throw new AuthenticationException("User must be authenticated to access this resource");
        }

        String requiredRole = methodAuth.value();
        if (!AuthenticationManager.hasRole(request, requiredRole)) {
            throw new AuthenticationException("User does not have the required role: " + requiredRole);
        }
    }
}
