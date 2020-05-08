package am.vtc.userchat.servlet.user;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.Message;
import am.vtc.userchat.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeUserServlet extends BaseUserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");
            List<User> users = super.userService.getAll();
            List<Message> messages = super.messageService.getAllMessages(user.getId(), user.getId());
            req.setAttribute("users", users);
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("WEB-INF/home.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}