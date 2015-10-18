package pl.edu.wat.map.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Lareth on 2015-10-18.
 */
public class Message {

    private String author;
    private Date date;
    private LatLng position;

    public Message(String author, Date date, LatLng position) {
        this.author = author;
        this.date = date;
        this.position = position;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public LatLng getPosition() {
        return position;
    }
}
