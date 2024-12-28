package com.example.Pokemon_TCG_TEST.Repository;

import com.example.Pokemon_TCG_TEST.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static final String USER_KEY = "user_";

    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUser(User user) {
        redisTemplate.opsForValue().set(USER_KEY+user.getEmail(), user);
    }

    public User getUserByEmail(String email) {
        return (User) redisTemplate.opsForValue().get(USER_KEY+email);
    }

    public boolean existsByEmail(String email) {
        return redisTemplate.hasKey(USER_KEY+email);
    }

    public List<String> getFavoritePokemons(String email) {
        User user = getUserByEmail(email);
        return user != null ? user.getFavoritePokemons() : new ArrayList<>();
    }

    public void updateUser(User user) {
        saveUser(user);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String key : redisTemplate.keys(USER_KEY + "*")) {
            User user = (User) redisTemplate.opsForValue().get(key);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }
}