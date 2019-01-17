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
    private Town town;
    private PetType petType;

    public Post() {};

    public Post(String description, String imageUrl, User user, String type, Date date, PetType petType, Town town) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
        this.date = new Date();
        this.petType = petType;
        this.town = town;

    }

    public Post(int id, String description, String imageUrl, User user, String type, Date date, PetType petType, Town town) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
        this.date = new Date();
        this.petType = petType;
        this.town = town;
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

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }
}
