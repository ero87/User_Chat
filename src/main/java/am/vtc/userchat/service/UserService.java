package am.vtc.userchat.service;


import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.exception.FileUploadException;
import am.vtc.userchat.model.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean userExist(String email) throws DatabaseException;
    User saveUser(User user, InputStream imageContent) throws DatabaseException, FileUploadException;
    long count() throws DatabaseException;
    String getPasswordByEmail(String email) throws DatabaseException;
    Optional getUser(String email, String password) throws DatabaseException;
    List<User> getAll() throws DatabaseException;
}
