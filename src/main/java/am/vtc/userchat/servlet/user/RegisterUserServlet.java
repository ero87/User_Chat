package am.vtc.userchat.servlet.user;


import am.vtc.userchat.model.User;
import am.vtc.userchat.servlet.RequestValidator;
import am.vtc.userchat.util.DataValidator;
import am.vtc.userchat.util.EncryptionUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegisterUserServlet extends BaseUserServlet {

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
                    InputStream imageStream = !DataValidator.isNullOrBlank(validator.getFileItems()) ?
                            validator.getFileItems().get(0).getInputStream() : null;
                    super.userService.saveUser(user, imageStream);
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

    private static RequestValidator<User> validator(HttpServletRequest req) throws FileUploadException {
        boolean isValid = true;
        String name = null;
        String surname = null;
        String email = null;
        String password = null;
        String confirmPassword = null;
        List<FileItem> fileItems = new ArrayList<>();

        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "name":
                            name = item.getString();
                            break;
                        case "surname":
                            surname = item.getString();
                            break;
                        case "email":
                            email = item.getString();
                            break;
                        case "password":
                            password = item.getString();
                            break;
                        case "confirmPassword":
                            confirmPassword = item.getString();
                            break;
                    }
                } else {
                    if (item.getSize() > 0) {
                        fileItems.add(item);
                    }
                }
            }
        } else {
            name = req.getParameter("name");
            surname = req.getParameter("surname");
            email = req.getParameter("email");
            password = req.getParameter("password");
            confirmPassword = req.getParameter("confirmPassword");
        }
        System.out.println(req.getParameter("name"));
        if (DataValidator.isNullOrBlank(name)) {
            isValid = false;
            req.setAttribute("errorName", "Name is Required");
        }
        if (DataValidator.isNullOrBlank(surname)) {
            isValid = false;
            req.setAttribute("errorSurname", "Surname is Required");
        }
        if (!DataValidator.isValidEmail(email)) {
            isValid = false;
            req.setAttribute("errorEmail", "Email is Required");
        }
        if (DataValidator.isNullOrBlank(password)) {
            isValid = false;
            req.setAttribute("errorPassword", "password is Required");
        } else {
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
            validator.setFileItems(fileItems);
            validator.setEntity(user);
        }
        return validator;
    }
}