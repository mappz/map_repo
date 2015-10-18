package pl.edu.wat.map.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import java.util.List;

import pl.edu.wat.map.R;

/**
 * Created by Hubert Faszcza on 2015-10-18.
 */
public class MapMessageAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater layoutInflater;
    private List<Object> messages;

    public MapMessageAdapter(List<Object> messages, LayoutInflater layoutInflater) {
        this.messages = messages;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = layoutInflater.inflate(R.layout.message_window, null);
        TextView messageText = ((TextView) view.findViewById(R.id.message_text));
        TextView messageAuthor = ((TextView) view.findViewById(R.id.message_author));
        TextView messageTime = ((TextView) view.findViewById(R.id.message_time));

        return view;

        //find message for marker
       /* for(Object message : messages)
        {
            if(marker.getPosition().longitude && marker.getPosition().latitude)
            {
                //set message content to messageText messageAuthor messageTime
                 break;
            }
        }*/
    }
}
