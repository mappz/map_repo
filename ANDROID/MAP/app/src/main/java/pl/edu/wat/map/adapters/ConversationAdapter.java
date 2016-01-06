package pl.edu.wat.map.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.model.Message;

/**
 * Adapter for Conversations
 * @author Marcel Paduch
 * @version 1
 * @since 17/12/2015
 */
public class ConversationAdapter extends ArrayAdapter<Message> {

    private Context mContext;
    private ArrayList<Message> messages;

    public ConversationAdapter(Context mContext, ArrayList<Message> messages) {
        super(mContext, R.layout.message_cell);
        this.mContext = mContext;
        this.messages = messages;
    }

    public void setMessages(ArrayList<Message> messages)
    {
        this.messages.clear();
        this.messages.addAll(messages);
    }



}
