package tn.esprit.pets.entity;

import java.util.Date;

public class Notification {

    private int id;
    private String title;
    private String body;
    private Date date;
    private User user;
    private Post post;

    public Notification() {

    }

    public Notification(String title, String body, Date date, User user) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.user = user;
    }

    public Notification(int id, String title, String body, Date date, User user,Post post) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.user = user;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
