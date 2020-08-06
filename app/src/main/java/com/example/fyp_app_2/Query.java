package com.example.fyp_app_2;

import java.io.Serializable;


public class Query implements Serializable {
    String id, query, email;

    public Query(String id, String query, String email) {
        this.id = id;
        this.query = query;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public String getEmail() {
        return email;
    }
}
