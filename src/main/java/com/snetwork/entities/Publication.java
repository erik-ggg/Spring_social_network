package com.snetwork.entities;

import org.assertj.core.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

@Entity
public class Publication {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private Date date;
    @Transient
    private MultipartFile photo;
    private String photoPath;
    @ManyToOne
    private User user;

    public Publication(){

    }

    public Publication(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.date = DateUtil.now();
        this.user = user;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        File file = new File(photoPath);
        String name = file.getName();
        this.photoPath = "/img/photos/" + name;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
