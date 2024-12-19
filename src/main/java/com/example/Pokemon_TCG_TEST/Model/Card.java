package com.example.Pokemon_TCG_TEST.Model;

import com.example.Pokemon_TCG_TEST.Model.SetResponse.SetDetails;
import java.util.List;

// Corrected Card Model
public class Card {

    private String id;            // Card ID
    private String name;          // Card name
    private List<String> types;   // List of types (e.g., Fire, Water)
    private String rarity;        // Card rarity
    private SetDetails set;       // Use nested SetDetails from SetResponse
    private Images images;        // Card images

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public SetDetails getSet() { return set; }
    public void setSet(SetDetails set) { this.set = set; }

    public Images getImages() { return images; }
    public void setImages(Images images) { this.images = images; }

    // Nested Class for Images
    public static class Images {
        private String small;
        private String large;

        public String getSmall() { return small; }
        public void setSmall(String small) { this.small = small; }

        public String getLarge() { return large; }
        public void setLarge(String large) { this.large = large; }
    }
}