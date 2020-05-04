package am.vtc.userchat.service;


import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.User;

import java.util.Optional;

public interface UserService {
    boolean userExist(String email) throws DatabaseException;
    void saveUser(User user) throws DatabaseException;
    long count() throws DatabaseException;
    String getPasswordByEmail(String email) throws DatabaseException;
    Optional getUser(String email, String password) throws DatabaseException;
}
