package com.shop.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hpsyche
 */
public class OsUser implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date created;
    private Date updated;

    public OsUser() {
    }

    public OsUser(Long id, String username, String password, String email, Date created, Date updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
