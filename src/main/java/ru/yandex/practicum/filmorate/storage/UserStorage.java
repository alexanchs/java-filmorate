package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    int generateId();
    Collection<User> findAll();
    User createUser(User user);
    User updateUser(User user);
    void addFriend(int yourId, int friendId);
    void deleteFriend(int yourId, int friendId);
    Set<Long> getCommonFriends(int yourId, int friendId);
}
