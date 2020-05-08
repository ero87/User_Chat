package am.vtc.userchat.servlet.message;

import am.vtc.userchat.service.MessageService;
import am.vtc.userchat.service.impl.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseMessageServlet extends HttpServlet {
    protected MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageServiceImpl();
    }
}
