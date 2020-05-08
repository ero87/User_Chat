package am.vtc.userchat.servlet.message;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.Message;
import am.vtc.userchat.servlet.RequestValidator;
import am.vtc.userchat.util.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createMessage")
public class MessageCreatorServlet extends BaseMessageServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strMessage = req.getParameter("message");
        int senderId = Integer.parseInt(req.getParameter("senderId"));
        int receiverId = Integer.parseInt(req.getParameter("receiverId"));

        Message message = new Message();
        message.setMessage(strMessage);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        try {
            this.messageService.add(message);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private RequestValidator<Message> validate(HttpServletRequest req) {
        boolean hasError = false;
        String strMessage = req.getParameter("message");
        String senderId = req.getParameter("senderId");
        String receiverId = req.getParameter("receiverId");

        if (DataValidator.isNullOrBlank(strMessage)) {
            req.setAttribute("message", "Message is required!");
            hasError = true;
        }
        if (DataValidator.isNullOrBlank(senderId) || DataValidator.isNumber(senderId)
                || DataValidator.isNullOrBlank(receiverId) || DataValidator.isNumber(receiverId)) {
            req.setAttribute("badRequest", "badRequest");
            hasError = true;
        }
        RequestValidator<Message> requestValidator = new RequestValidator<Message>();
        if (!hasError) {
            Message message = new Message();
            message.setMessage(strMessage);
            message.setSenderId(Integer.parseInt(senderId));
            message.setReceiverId(Integer.parseInt(receiverId));
            requestValidator.setEntity(message);
        } else {
            requestValidator.setValid(false);
        }
        return requestValidator;
    }
}
