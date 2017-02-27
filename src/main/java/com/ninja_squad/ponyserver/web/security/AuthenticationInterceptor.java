package com.ninja_squad.ponyserver.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ninja_squad.ponyserver.web.Database;
import com.ninja_squad.ponyserver.web.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor which checks the presence and validity of a custom header (named Custom-Authentication),
 * and initializes the current user. The header must contain a valid user login. If the header is not there or is
 * invalid, a 401 response is sent.
 * @author JB Nizet
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private Database database;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Custom-Authentication");
        // Allow pre-flight requests (for CORS)
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            if (token == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "You must be authenticated to access this resource");
                return false;
            }
            if (!tokenValid(token)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials");
                return false;
            }
        }
        return true;
    }

    private boolean tokenValid(String token) {
        for (User user : database.getUsers()) {
            if (user.getLogin().equals(token)) {
                currentUser.setLogin(token);
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
    }
}
