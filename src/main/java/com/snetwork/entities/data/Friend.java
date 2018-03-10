package com.snetwork.entities.data;

public class Friend {
    public final static String ACCEPT_FRIEND_REQUEST = "ACCEPT FRIEND REQUEST";
    public final static String SEND_FRIEND_REQUEST = "SEND FRIEND REQUEST";
    public final static String SENDED_FRIEND_REQUEST = "SENDED";
    public final static String FRIENDS = "FRIENDS";

    private Long id;
    private String name;
    private String email;
    private String status;

    public Friend(Long id, String name, String email, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
