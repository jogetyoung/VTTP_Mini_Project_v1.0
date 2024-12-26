package com.example.Pokemon_TCG_TEST.Model;

import java.util.List;

public class CardResponse {

    private List<Card> data; // Cards from the API response

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    // Getters and Setters
    public List<Card> getData() { return data; }
    public void setData(List<Card> data) { this.data = data; }
}