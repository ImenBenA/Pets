package tn.esprit.pets.entity;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class Post {

    private int id;
    private String description;
    private String imageUrl;
    private User user;
    private String type;
    private Date date;

    public Post() {};

    public Post(String description, String imageUrl, User user, String type, Date date) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
        this.date = new Date();
    }

    public Post(int id, String description, String imageUrl, User user, String type, Date date) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
