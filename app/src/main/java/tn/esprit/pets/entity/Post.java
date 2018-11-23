package tn.esprit.pets.entity;

public class Post {

    private int id;
    private String description;
    private String imageUrl;
    private User user;
    private String type;

    public Post() {};

    public Post(String description, String imageUrl, User user, String type) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
    }

    public Post(int id, String description, String imageUrl, User user, String type) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
        this.type = type;
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
}
