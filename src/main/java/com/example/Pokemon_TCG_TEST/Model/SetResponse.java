package com.example.Pokemon_TCG_TEST.Model;

import java.util.List;

public class SetResponse {

    private List<SetDetails> data;  // Correct reference if SetDetails is nested

    // Getter and Setter
    public List<SetDetails> getData() {
        return data;
    }

    public void setData(List<SetDetails> data) {
        this.data = data;
    }

    // Nested Class for Set Details
    public static class SetDetails {
        private String id;   // Set ID
        private String name; // Set Name

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}