package com.snetwork.entities.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Request {
    @Id
    @GeneratedValue
    private Long id;
    private Long idSender;
    private Long idReceiver;
    private boolean accepted;

    public Request(Long idSender, Long idReceiver, boolean accepted) {
        super();
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.accepted = accepted;
    }

    public Request(){

    }

    public Long getIdSender() {
        return idSender;
    }

    public void setIdSender(Long idSender) {
        this.idSender = idSender;
    }

    public Long getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(Long idReceiver) {
        this.idReceiver = idReceiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Long getId() {
        return id;
    }
}
