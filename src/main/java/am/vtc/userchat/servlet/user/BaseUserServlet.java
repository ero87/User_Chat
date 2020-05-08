package am.vtc.userchat.servlet.user;


import am.vtc.userchat.service.MessageService;
import am.vtc.userchat.service.UserService;
import am.vtc.userchat.service.impl.MessageServiceImpl;
import am.vtc.userchat.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

abstract class BaseUserServlet extends HttpServlet {
    protected UserService userService;
    protected MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        messageService = new MessageServiceImpl();
    }
}
