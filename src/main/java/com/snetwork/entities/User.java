package com.snetwork.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    public final static String ACCEPT_FRIEND_REQUEST = "ACCEPT FRIEND REQUEST";
    public final static String SEND_FRIEND_REQUEST = "SEND FRIEND REQUEST";
    public final static String SENDED_FRIEND_REQUEST = "SENDED";
    public final static String FRIENDS = "FRIENDS";

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String email;
    private String name;
    private String password;
    private String role;
    @Transient
    private String status;
    @Transient
    private String passwordConfirm;
    @OneToMany(mappedBy = "user")
    private List<Publication> publications;

    @OneToMany
    private List<Request> senderOf;
    @OneToMany
    private List<Request> receiverOf;

    public User() {

    }

    public User(String email, String name, String password) {
        super();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Long getId() {
        return id;
    }


}
