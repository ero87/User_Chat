package am.vtc.userchat.repo;


import am.vtc.userchat.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepo {
    User insert(User user) throws SQLException;
    Optional<User> findById(int id) throws SQLException;
    long count() throws SQLException;
    boolean existEmail(String email) throws SQLException;
    String getPasswordByEmail(String email) throws SQLException;
    Optional<User> findByEmailAndPassword(String email, String password) throws SQLException;

}
