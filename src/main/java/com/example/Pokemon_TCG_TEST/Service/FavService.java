package com.example.Pokemon_TCG_TEST.Service;

import com.example.Pokemon_TCG_TEST.Model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//redundant after creating UserService so that it is saved to each individual user account

@Service
public class FavService {

    @Autowired
    @Qualifier("redis-template-card")
    private RedisTemplate<String, Card> redisTemplate; // handles storing and retrieving Card objects from Redis

    private static final String FAVORITES_KEY = "favorites";

    // Add a card to favorites
    public boolean addFavoriteCard(Card card) {
        if (card == null || redisTemplate.opsForHash().hasKey(FAVORITES_KEY, card.getId())) {
            return false;  // Already exists or card not found
        }
        redisTemplate.opsForHash().put(FAVORITES_KEY, card.getId(), card);
        return true;
    }

    // Get all favorite cards
    public List<Card> getFavoriteCards() {
        List<Object> rawValues = redisTemplate.opsForHash().values(FAVORITES_KEY);
        List<Card> favoriteCards = new ArrayList<>();
        for (Object raw : rawValues) {
            if (raw instanceof Card) {
                favoriteCards.add((Card) raw);
            }
        }
        return favoriteCards;
    }

    // Remove a card from favorites
    public boolean removeFavoriteCard(String id) {
        if (!redisTemplate.opsForHash().hasKey(FAVORITES_KEY, id)) {
            return false;  // Not found
        }
        redisTemplate.opsForHash().delete(FAVORITES_KEY, id);
        return true;
    }
}