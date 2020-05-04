package am.vtc.userchat.servlet;


import am.vtc.userchat.service.UserService;
import am.vtc.userchat.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseServlet extends HttpServlet {
    protected UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }
}
