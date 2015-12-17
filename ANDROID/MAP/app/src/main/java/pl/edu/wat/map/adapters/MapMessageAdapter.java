package pl.edu.wat.map.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import java.util.List;
import pl.edu.wat.map.R;
import pl.edu.wat.map.model.Conversation;


/**
 * This adapter maps messages to points on map
 * @author Marcel Paduch
 * @version 1
 * @since 17/12/2015
 */
public class MapMessageAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater layoutInflater;
    private List<Conversation> conversations;
    private Context context;

    public MapMessageAdapter(List<Conversation> conversations, LayoutInflater layoutInflater, Context context) {
        this.conversations = conversations;
        this.layoutInflater = layoutInflater;
        this.context = context;
    }

	/**
     * Sets conversation list
     * @param conversations conversations list
     */
    public void setConversations(List<Conversation> conversations)
    {
        this.conversations = conversations;
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
            if(conversations != null) {
                for (Conversation conversation : conversations) {
                    if (marker.getPosition().longitude == conversation.getLongtitude() && marker.getPosition().latitude == conversation.getLatitude()) {
                        messageText.setText(conversation.getContent());
                        messageAuthor.setText(conversation.getAuthor().getName());
                        messageTime.setText(conversation.getDate());
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
