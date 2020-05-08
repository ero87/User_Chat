package am.vtc.userchat.servlet.user;


import am.vtc.userchat.model.User;
import am.vtc.userchat.servlet.RequestValidator;
import am.vtc.userchat.util.DataValidator;
import am.vtc.userchat.util.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginUserServlet extends BaseUserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestValidator<LoginPayLoad> validator = LoginUserServlet.validator(req);
            if (validator.isValid()) {
                Optional<User> user = super.userService.getUser(validator.getEntity().email, validator.getEntity().password);
                if(user.isPresent()) {
                    req.getSession().setAttribute("user", user.get());
                    resp.sendRedirect("/home");
                    return;
                }
                req.setAttribute("errorEmailPassword", "Incorrect Email/Password.");
            }
            req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static RequestValidator<LoginPayLoad> validator(HttpServletRequest req) {
        boolean isValid = true;
        String email = req.getParameter("email");
        if (!DataValidator.isValidEmail(email)) {
            isValid = false;
            req.setAttribute("errorEmail", "Email is Required");
        }
        String password = req.getParameter("password");
        if (DataValidator.isNullOrBlank(password)) {
            isValid = false;
            req.setAttribute("errorPassword", "password is Required");
        }

        RequestValidator<LoginPayLoad> validator = new RequestValidator<>();
        validator.setValid(isValid);
        if (isValid) {
            LoginPayLoad loginPayLoad = new LoginPayLoad();
            loginPayLoad.email = email;
            loginPayLoad.password = EncryptionUtil.encrypt(password);
            validator.setEntity(loginPayLoad);
        }
        return validator;
    }

    private static class LoginPayLoad {
        String email;
        String password;
    }
}
