package am.vtc.userchat.service.impl;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.exception.FileUploadException;
import am.vtc.userchat.model.User;
import am.vtc.userchat.repo.UserRepo;
import am.vtc.userchat.repo.impl.UserRepoSql;
import am.vtc.userchat.service.UserService;
import am.vtc.userchat.util.Settings;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public List<User> getAll() throws DatabaseException {
        try {
            return this.userRepo.fetchAll();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User saveUser(User user, InputStream imageContent) throws DatabaseException, FileUploadException {
        String imageName = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
        String path = Settings.getInstance().getString("images.path") + imageName;
        try {
            if (imageContent != null) {
                try (OutputStream out = new FileOutputStream(path)) {
                    byte[] buffer = new byte[2048];
                    int readCount;
                    while ((readCount = imageContent.read(buffer)) > -1) {
                        out.write(buffer, 0, readCount);
                    }
                    user.setImageUrl("/images/" + imageName);
                } catch (IOException e) {
                    throw new FileUploadException(e);
                }
            } else {
                user.setImageUrl("/static/images/incognito.png");
            }
            user = this.userRepo.insert(user);
            return user;
        } catch (SQLException e) {
            if (user.getId() > 0) {
                new File(path).delete();
            }
            throw new DatabaseException(e);
        }
    }
}
