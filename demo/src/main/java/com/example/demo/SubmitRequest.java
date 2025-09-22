package com.example.demo;

public class SubmitRequest {
    private String finalQuery;

    // Default constructor (required by WebClient)
    public SubmitRequest() {}

    public SubmitRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    // getters & setters
    public String getFinalQuery() {
        return finalQuery;
    }

    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
}