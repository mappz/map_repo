package pl.edu.wat.map.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.Marker;


import java.util.List;


import pl.edu.wat.map.R;
import pl.edu.wat.map.model.Message;

/**
 * Created by Hubert Faszcza on 2015-10-18.
 */
public class MapMessageAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater layoutInflater;
    private List<Message> messages;
    private Context context;

    public MapMessageAdapter(List<Message> messages, LayoutInflater layoutInflater, Context context) {
        this.messages = messages;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
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


        //find message for marker
        if(!marker.getTitle().equals(context.getResources().getString(R.string.your_position))) {
            if(messages != null) {
                for (Message message : messages) {
                    if (marker.getPosition().longitude == message.getLongtitude() && marker.getPosition().latitude == message.getLatitude()) {
                        messageText.setText(message.getContent());
                        messageAuthor.setText(message.getAuthor());
                        messageTime.setText(message.getDate().toString());
                        break;
                    }
                }
            }
        }
        else {
            TextView title = ((TextView) view.findViewById(R.id.message_text_title));
            TextView author = ((TextView) view.findViewById(R.id.message_author_title));
            TextView time = ((TextView) view.findViewById(R.id.message_date_title));

            title.setVisibility(View.GONE);
            author.setVisibility(View.GONE);
            time.setVisibility(View.GONE);


            messageText.setText(marker.getTitle());
            messageAuthor.setText(marker.getPosition().toString());
        }
        return view;
    }
}
