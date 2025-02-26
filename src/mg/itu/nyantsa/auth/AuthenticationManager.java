package mg.itu.nyantsa.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;

public class AuthenticationManager {
    private static Properties properties;
    private static String userSessionKey;
    private static String rolesSessionKey;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        properties = new Properties();
        userSessionKey = "auth";
        rolesSessionKey = "roleUser";
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(userSessionKey) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String requiredRole) {
        if (!isAuthenticated(request)) {
            return false;
        }

        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute(rolesSessionKey);
        
        return requiredRole.isEmpty() || "public".equals(requiredRole) || requiredRole.equals(userRole);
    }
}
