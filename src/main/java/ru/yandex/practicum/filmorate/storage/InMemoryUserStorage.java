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
        if (user.getName() == null) {
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
    public void addFriend(int yourId, int friendId){
        if(!users.containsKey(yourId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        }
        if(!users.containsKey(friendId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", friendId));
        }
        User user1 = users.get(yourId);
        User user2 = users.get(friendId);
        Set<Long> friends1 = user1.getFriends();
        Set<Long> friends2 = user2.getFriends();
        friends1.add((long) friendId);
        friends2.add((long) yourId);
        user1.setFriends(friends1);
        user2.setFriends(friends2);
        updateUser(user1);
        updateUser(user2);
    }

    @Override
    public Set<Long> getCommonFriends(int yourId, int friendId){
        if(!users.containsKey(yourId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        }
        if(!users.containsKey(friendId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", friendId));
        }
        User user1 = users.get(yourId);
        User user2 = users.get(friendId);
        Set<Long> friends1 = user1.getFriends();
        Set<Long> friends2 = user2.getFriends();
        Set<Long> commonFriends = new HashSet<>();
        for (Long id:friends1){
            if(friends2.contains(id)){
                commonFriends.add(id);
            }
        }
        return commonFriends;
    }

    @Override
    public void deleteFriend(int yourId, int friendId){
        if(!users.containsKey(yourId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", yourId));
        }
        if(!users.containsKey(friendId)){
            throw new UserNotFoundException(String.format("Нет пользователя с id: %d!", friendId));
        }
        User user1 = users.get(yourId);
        User user2 = users.get(friendId);
        Set<Long> friends1 = user1.getFriends();
        Set<Long> friends2 = user2.getFriends();
        friends1.remove((long) friendId);
        friends2.remove((long) yourId);
        user1.setFriends(friends1);
        user2.setFriends(friends2);
        updateUser(user1);
        updateUser(user2);
    }
}
