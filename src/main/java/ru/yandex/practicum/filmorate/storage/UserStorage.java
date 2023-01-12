package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    Collection<User> findAll();

    User createUser(User user);

    void updateUser(User user);

    void addFriend(int yourId, int friendId);

    void deleteFriend(int yourId, int friendId);

    Set<User> getCommonFriends(int yourId, int friendId);

    User getUser(int id);

    Set<User> getUserFriends(int userId);
}
