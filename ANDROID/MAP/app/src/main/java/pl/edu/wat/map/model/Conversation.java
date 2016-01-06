package pl.edu.wat.map.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Model class for conversation
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class Conversation implements Serializable {

    private String id;
    private Author author;
    private String content;
    private String date;
    private String category;
    private double latitude;
    private double longtitude;
    private List<Message> messages;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Conversation() {
    }

    public Conversation(Author author, String content, String date, String category, double latitude, double longtitude, ArrayList<Message> messages) {
        this.author = author;
        this.content = content;
        this.date = date;
        this.category = category;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.messages = messages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
