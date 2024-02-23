package com.example.bitirmeprojesi.dataAccess;

public class SearchRequest {
    private String name;
    private String term;
    private String status;

    public SearchRequest(String name, String term, String status) {
        this.name = name;
        this.term = term;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getTerm() {
        return term;
    }

    public String getStatus() {
        return status;
    }
}
