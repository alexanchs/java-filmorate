package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class UserService {

    private final UserStorage userStorage;
    @Autowired
   public UserService(@Qualifier("daoImpl") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public void addFriend(int yourId, int friendId) {
        userStorage.addFriend(yourId, friendId);
    }

    public void deleteFriend(int yourId, int friendId) {
        userStorage.deleteFriend(yourId, friendId);
    }

    public Set<User> getCommonFriends(int yourId, int friendId) {
        return userStorage.getCommonFriends(yourId, friendId);
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public Set<User> getFriends(int id) {
        return userStorage.getUserFriends(id);
    }
}
