package pl.edu.wat.map.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Lareth on 2015-10-18.
 */
public class Message {

    private String author;
    private String content;
    private String date;
    private double latitude;
    private double longtitude;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Message() {
    }

    public Message(String author, String content, String date, double latitude, double longtitude) {
        this.author = author;
        this.content = content;
        this.date = date;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}
