package org.mustapha.smartShop.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();

        // Logging for debug
        System.out.println("Session: " + session);
        System.out.println("Request path: " + path);
        System.out.println("userId: " + (session != null ? session.getAttribute("userId") : "null"));
        System.out.println("role: " + (session != null ? session.getAttribute("role") : "null"));

        // check if user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Please login first");
            return false;
        }

        String role = (String) session.getAttribute("role");

        // role-based access control
        if ("CLIENT".equals(role)) {
            // Allow only client-specific endpoints
            if (!(path.contains("/my-history") || path.contains("/products"))) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Forbidden: Clients cannot access this resource");
                return false;
            }
        }

        // ADMIN can access everything

        return true; // Allow the request
    }
}
