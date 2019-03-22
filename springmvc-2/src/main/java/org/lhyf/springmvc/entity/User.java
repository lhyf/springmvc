package org.lhyf.springmvc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
