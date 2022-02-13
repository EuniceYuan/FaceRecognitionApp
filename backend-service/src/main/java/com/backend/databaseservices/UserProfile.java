package com.backend.databaseservices;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("userProfile")
public class UserProfile {
    public UserProfile() {
    }

    public UserProfile(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    @Id
    private int id;
    private String fullname;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

