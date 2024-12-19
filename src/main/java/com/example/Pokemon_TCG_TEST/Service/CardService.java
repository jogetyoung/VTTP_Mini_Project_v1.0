package com.example.Pokemon_TCG_TEST.Service;

import com.example.Pokemon_TCG_TEST.Model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CardService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.pokemontcg.io/v2";
    private final String apiKey = "934d814f-3a4c-451c-872c-decdef6bc3df";

    // Helper method to create headers with the API key
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return headers;
    }

    // Search for cards by name
    public List<Card> searchCardsByName(String name) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = apiUrl + "/cards?q=name:" + name;

        // Fetch and deserialize response
        CardResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, CardResponse.class).getBody();

        return response != null ? response.getData() : List.of();
    }

    // Fetch available sets
    public List<SetResponse.SetDetails> getAvailableSets() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = apiUrl + "/sets";

        // Fetch and deserialize response
        SetResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, SetResponse.class).getBody();

        return response != null ? response.getData() : List.of();
    }

    // Fetch available types
    public List<String> getAvailableTypes() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = apiUrl + "/types";

        // Correct the response deserialization
        TypeResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, TypeResponse.class).getBody();

        return response != null ? response.getData() : List.of();
    }

    // Fetch available rarities
    public List<String> getAvailableRarities() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = apiUrl + "/rarities";

        // Correct the response deserialization
        RarityResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, RarityResponse.class).getBody();

        return response != null ? response.getData() : List.of();
    }

    public List<Card> searchCards(String name, String set, String type, String rarity, int page, int pageSize) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        StringBuilder query = new StringBuilder(apiUrl + "/cards?q=");

        if (name != null && !name.isEmpty()) query.append("name:\"").append(name).append("\" ");
        if (set != null && !set.isEmpty()) query.append("set.id:").append(set).append(" ");
        if (type != null && !type.isEmpty()) query.append("types:").append(type).append(" ");
        if (rarity != null && !rarity.isEmpty()) query.append("rarity:\"").append(rarity).append("\" ");

        // Add pagination query parameters
        query.append("&page=").append(page).append("&pageSize=").append(pageSize);

        String url = query.toString().trim();

        // Fetch and deserialize response
        CardResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, CardResponse.class).getBody();

        return response != null ? response.getData() : List.of();
    }

    public Card getCardById(String id) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = apiUrl + "/cards/" + id;

        // Correct deserialization using SingleCardResponse
        SingleCardResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, SingleCardResponse.class).getBody();

        // Return the card if found
        return response != null ? response.getData() : null;
    }

    public int getTotalCardCount(String name, String set, String type, String rarity) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        StringBuilder query = new StringBuilder(apiUrl + "/cards?q=");
        if (name != null && !name.isEmpty()) query.append("name:\"").append(name).append("\" ");
        if (set != null && !set.isEmpty()) query.append("set.id:").append(set).append(" ");
        if (type != null && !type.isEmpty()) query.append("types:").append(type).append(" ");
        if (rarity != null && !rarity.isEmpty()) query.append("rarity:\"").append(rarity).append("\" ");

        // Fetch the total count
        String url = query.toString().trim() + "&pageSize=1"; // Request 1 card to get total count
        CardResponse response = restTemplate.exchange(
                url, HttpMethod.GET, entity, CardResponse.class).getBody();

        return response != null ? response.getTotalCount() : 0;
    }
}