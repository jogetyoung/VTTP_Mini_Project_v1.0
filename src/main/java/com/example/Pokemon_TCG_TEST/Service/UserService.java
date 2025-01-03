package com.example.Pokemon_TCG_TEST.Service;

import com.example.Pokemon_TCG_TEST.Model.User;
import com.example.Pokemon_TCG_TEST.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        userRepository.saveUser(user);
    }


    public User loginUser(String email, String password) {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }
        return user;
    }

    public boolean addFavourite(String email, String cardId) {
        User user = userRepository.getUserByEmail(email);

        if (user != null && !user.getFavoritePokemons().contains(cardId)) {
            user.getFavoritePokemons().add(cardId);
            userRepository.updateUser(user);
            return true;
        }
        return false;
    }

    public void removeFavourite(String email, String cardId) {
        User user = userRepository.getUserByEmail(email);

        if (user != null && user.getFavoritePokemons().contains(cardId)) {
            user.getFavoritePokemons().remove(cardId);
            userRepository.updateUser(user);
        }
    }

    public List<String> getAllFavourites(String email) {
        return userRepository.getFavoritePokemons(email);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
