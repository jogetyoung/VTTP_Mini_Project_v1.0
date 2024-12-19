package com.example.Pokemon_TCG_TEST.Model;

public class SingleCardResponse {

    private Card data;  // "data" contains a single card object

    public Card getData() {
        return data;
    }

    public void setData(Card data) {
        this.data = data;
    }
}