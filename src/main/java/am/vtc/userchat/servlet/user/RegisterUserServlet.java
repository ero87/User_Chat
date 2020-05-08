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

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestValidator<User> validator = validator(req);
            if (validator.isValid()) {
                User user = validator.getEntity();
                if (super.userService.userExist(user.getEmail())) {
                    req.setAttribute("errorEmail", "User already exist");
                } else {
                    super.userService.saveUser(user);
                    req.getSession().setAttribute("successfully", "User successfully registered");
                    resp.sendRedirect("/login");
                    return;
                }
            }
            req.getRequestDispatcher("WEB-INF/register.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static RequestValidator<User> validator(HttpServletRequest req) {
        boolean isValid = true;
        String name = req.getParameter("name");
        if (DataValidator.isNullOrBlank(name)) {
            isValid = false;
            req.setAttribute("errorName", "Name is Required");
        }
        String surname = req.getParameter("surname");
        if (DataValidator.isNullOrBlank(surname)) {
            isValid = false;
            req.setAttribute("errorSurname", "Surname is Required");
        }
        String email = req.getParameter("email");
        if (!DataValidator.isValidEmail(email)) {
            isValid = false;
            req.setAttribute("errorEmail", "Email is Required");
        }
        String password = req.getParameter("password");

        if (DataValidator.isNullOrBlank(password)) {
            isValid = false;
            req.setAttribute("errorPassword", "password is Required");
        } else {
            String confirmPassword = req.getParameter("confirmPassword");
            if (!password.trim().equals(confirmPassword)) {
                isValid = false;
                req.setAttribute("errorConfirmPassword", "Passwords doesn't matches");
            }
        }
        RequestValidator<User> validator = new RequestValidator<>();
        validator.setValid(isValid);
        if (isValid) {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(EncryptionUtil.encrypt(password));
            user.setImageUrl("/images/incognito.png");
            validator.setEntity(user);
        }
        return validator;
    }

//    public static String encryptMd5(String password) {
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.update(password.getBytes(), 0, password.length());
//            BigInteger i = new BigInteger(1,messageDigest.digest());
//            return String.format("%1$032x", i);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}