package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Component
@Qualifier("daoImpl")
public class UserDbStorage implements UserStorage {
   private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> findAll() {
        String sqlQuery = "SELECT id, name, email, login, birthday FROM users";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "INSERT INTO USERS(name, email, login, birthday) " +
                "values (?, ?, ?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLogin());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        int newId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        user.setId(newId);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sqlQuery = "update users set " +
                "name = ?, email = ?, login = ?, birthday = ?" +
                "where id = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getEmail()
                , user.getLogin()
                , user.getBirthday()
                , user.getId());
    }

    @Override
    public void addFriend(int yourId, int friendId) {

    }

    @Override
    public void deleteFriend(int yourId, int friendId) {

    }

    @Override
    public Set<User> getCommonFriends(int yourId, int friendId) {
        return null;
    }

    @Override
    public User getUser(int id) {
        String sqlQuery = "SELECT id, name, email, login, birthday FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
    }

    @Override
    public Set<User> getUserFriends(int userId) {
        return null;
    }

    public boolean delete(int id) {
        String sqlQuery = "delete from users where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getObject("birthday", LocalDate.class));
    }
}
