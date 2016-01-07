package pl.edu.wat.map.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.model.Message;

/**
 * Adapter for Conversations
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class ConversationAdapter extends ArrayAdapter<Message> {

    private Context mContext;
    private ArrayList<Message> messages;

    private class ViewHolder
    {
        TextView message;
        TextView author;
        TextView date;
    }

    public ConversationAdapter(Context mContext, ArrayList<Message> messages) {
        super(mContext, R.layout.cell_message);
        this.mContext = mContext;
        this.messages = messages;
    }

    public void setMessages(ArrayList<Message> messages)
    {
        this.messages.clear();
        this.messages.addAll(messages);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.cell_message, null);
        holder = new ViewHolder();
        holder.author = (TextView) convertView
                .findViewById(R.id.name);
        holder.date = (TextView) convertView
                .findViewById(R.id.date);
        holder.message = (TextView) convertView
                .findViewById(R.id.content);
        convertView.setTag(holder);

        Message message = messages.get(position);
        holder.author.setText(message.getAuthor().getName());
        holder.date.setText(message.getDate());
        holder.message.setText(message.getContent());
        return convertView;
    }



    @Override
    public int getCount() {
        //TODO
        return messages.size();
    }






}
