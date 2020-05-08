package am.vtc.userchat.filter;

import am.vtc.userchat.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(filterName = "AuthFilter",
        urlPatterns = {"/home", "/usersList",
                "/messages", "/createMessage", "/"})
public class AuthFilter implements Filter {

    private final Map<Integer, Date> USERS_ACTIVITY_MAP = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().setAttribute("users_activity", USERS_ACTIVITY_MAP);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            response.sendRedirect("/login");
            return;
        } else {
            USERS_ACTIVITY_MAP.put(user.getId(), new Date());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
