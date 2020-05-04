package am.vtc.userchat.repo.impl;


import am.vtc.userchat.model.User;
import am.vtc.userchat.repo.UserRepo;
import am.vtc.userchat.util.DataSource;

import java.sql.*;
import java.util.Optional;

public class UserRepoSql implements UserRepo {

    @Override
    public User insert(User user) throws SQLException {
        String query = "insert into users(name, surname, email, password, image_url) values (?, ?, ?, ?, ?);";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getImageUrl());
            preparedStatement.execute();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
        }
        return user;
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        String query = "select count(id) as count from users;";
        try(Connection connection = DataSource.getConnection();
            Statement statement = connection.prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery(query)) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        }
    }

    @Override
    public boolean existEmail(String email) throws SQLException {
        String query = "select count(*) from users where email = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getInt(1) == 1;
            }
        }
    }

    @Override
    public String getPasswordByEmail(String email) throws SQLException {
        String query = "select password from users where email = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        }
        return "";
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws SQLException {
        String query = "select * from users where email = ? and password = ?";
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(UserRepoSql.toUser(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    private static User toUser(ResultSet resultSet) throws SQLException{
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setImageUrl(resultSet.getString("image_url"));
        return user;
    }
}
