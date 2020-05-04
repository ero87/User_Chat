package am.vtc.userchat.service.impl;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.User;
import am.vtc.userchat.repo.UserRepo;
import am.vtc.userchat.repo.impl.UserRepoSql;
import am.vtc.userchat.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl() {
        this.userRepo = new UserRepoSql();
    }

    @Override
    public boolean userExist(String email) throws DatabaseException {
        try {
            return this.userRepo.existEmail(email);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void saveUser(User user) throws DatabaseException {
        try {
            if (user.getId() > 0) {

            } else {
                this.userRepo.insert(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public long count() throws DatabaseException {
        try {
            return this.userRepo.count();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public String getPasswordByEmail(String email) throws DatabaseException {
        try {
            return this.userRepo.getPasswordByEmail(email);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional getUser(String email, String password) throws DatabaseException {
        try {
            return this.userRepo.findByEmailAndPassword(email, password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
