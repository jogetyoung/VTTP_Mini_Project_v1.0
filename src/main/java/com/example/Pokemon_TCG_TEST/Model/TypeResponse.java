package com.example.Pokemon_TCG_TEST.Model;

import java.util.List;

public class TypeResponse {
    private List<String> data;  // This matches the API's response

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}