package ru.yandex.practicum.filmorate.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int usersId = 0;

    @Override
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
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("Данные юзера '{}' обновлены!", user.getName());
        } else {
            throw new RuntimeException("Нет пользователя с таким id!");
        }
        return user;
    }

    @Override
    public void addFriend(int yourId, int friendId) {
        if (!users.containsKey(yourId) || !users.containsKey(friendId)) {
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        } else {
            User user1 = users.get(yourId);
            User user2 = users.get(friendId);
            Set<Integer> friends2 = new LinkedHashSet<>();
            Set<Integer> friends1 = new LinkedHashSet<>();

            if (user1.getFriends() != null) {
                friends1.addAll(user1.getFriends());
                friends1.add(friendId);
            } else {
                friends1.add(friendId);
            }
            if (user2.getFriends() != null) {
                friends2.addAll(user2.getFriends());
                friends2.add(yourId);
            } else {
                friends2.add(yourId);
            }
            user1.setFriends(friends1);
            user2.setFriends(friends2);
            updateUser(user1);
            updateUser(user2);
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
        User user1 = users.get(yourId);
        User user2 = users.get(friendId);
        Set<Integer> friends1 = user1.getFriends();
        Set<Integer> friends2 = user2.getFriends();

        Set<User> commonFriends = new HashSet<>();
        if (friends1 == null || friends2 == null) {
            return commonFriends;
        }
        for (Integer id : friends1) {
            if (friends2.contains(id)) {
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
        User user1 = users.get(yourId);
        User user2 = users.get(friendId);
        Set<Integer> friends1 = user1.getFriends();
        Set<Integer> friends2 = user2.getFriends();
        friends1.remove(friendId);
        friends2.remove(yourId);
        user1.setFriends(friends1);
        user2.setFriends(friends2);
        updateUser(user1);
        updateUser(user2);
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
