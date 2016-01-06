package pl.edu.wat.map.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.map.R;
import pl.edu.wat.map.model.Message;

/**
 * Created by Lareth on 2015-12-03.
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
