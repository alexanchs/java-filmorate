package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
@Qualifier("inMemoryImpl")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int usersId = 0;

    public int generateId() {
        usersId += 1;
        return usersId;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Создан юзер: '{}'!", user.getName());
        return user;
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Данные юзера '{}' обновлены!", user.getName());
        } else {
            throw new RuntimeException("Нет пользователя с таким id!");
        }
    }

    @Override
    public void addFriend(int yourId, int friendId) {
        if (!users.containsKey(yourId) || !users.containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        } else {
            User userYou = users.get(yourId);
            User userFriend = users.get(friendId);
            Set<Integer> friendsYour = new LinkedHashSet<>();
            Set<Integer> friendsFriend = new LinkedHashSet<>();

            if (userYou.getFriends() != null) {
                friendsYour.addAll(userYou.getFriends());
                friendsYour.add(friendId);
            } else {
                friendsYour.add(friendId);
            }
            if (userFriend.getFriends() != null) {
                friendsFriend.addAll(userFriend.getFriends());
                friendsFriend.add(yourId);
            } else {
                friendsFriend.add(yourId);
            }
            userYou.setFriends(friendsYour);
            userFriend.setFriends(friendsFriend);
            updateUser(userYou);
            updateUser(userFriend);
        }

    }

    @Override
    public Set<User> getCommonFriends(int yourId, int friendId) {
        if (!users.containsKey(yourId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        }
        if (!users.containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", friendId));
        }
        User userYou = users.get(yourId);
        User userFriend = users.get(friendId);
        Set<Integer> friendsYou = userYou.getFriends();
        Set<Integer> friendsFriend = userFriend.getFriends();

        Set<User> commonFriends = new HashSet<>();
        if (friendsYou == null || friendsFriend == null) {
            return commonFriends;
        }
        for (Integer id : friendsYou) {
            if (friendsFriend.contains(id)) {
                commonFriends.add(users.get(id));
            }
        }
        return commonFriends;
    }

    @Override
    public void deleteFriend(int yourId, int friendId) {
        if (!users.containsKey(yourId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        }
        if (!users.containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", friendId));
        }
        User userYou = users.get(yourId);
        User userFriends = users.get(friendId);
        Set<Integer> friendsYou = userYou.getFriends();
        Set<Integer> friendsFriend = userFriends.getFriends();
        friendsYou.remove(friendId);
        friendsFriend.remove(yourId);
        userYou.setFriends(friendsYou);
        userFriends.setFriends(friendsFriend);
        updateUser(userYou);
        updateUser(userFriends);
    }

    @Override
    public User getUser(int id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", id));
        }
        return users.get(id);
    }

    @Override
    public Set<User> getUserFriends(int userId) {
        Set<User> result = new LinkedHashSet<>();
        for (int id : users.get(userId).getFriends()) {
            result.add(getUser(id));
        }
        return result;
    }
}
