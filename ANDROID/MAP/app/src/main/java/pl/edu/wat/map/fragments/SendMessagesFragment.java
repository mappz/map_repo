package pl.edu.wat.map.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.messages.Messages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.edu.wat.map.R;
import pl.edu.wat.map.adapters.ConversationAdapter;
import pl.edu.wat.map.model.Author;
import pl.edu.wat.map.model.Conversation;
import pl.edu.wat.map.model.Message;

/**
 * Fragment used as container for send messages view
 *
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class SendMessagesFragment extends Fragment implements View.OnClickListener {

    private ListView chatList;
    private Firebase mFirebaseRef;
    private FirebaseListAdapter<Message> firebaseListAdapter;
    private Firebase ref;
    private ConversationAdapter adapter;
    private final String param1 = "conversation";
    private Conversation conversation;
    private ArrayList<Message> messages;


    public SendMessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Android OS method called when activity is created
     *
     * @param savedInstanceState saved activity state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        conversation = (Conversation) getArguments().getSerializable("conversation");
//        conversationId = savedInstanceState.getString(param1);
    }

    /**
     * Android OS method called when view is created
     *
     * @param inflater           inflater
     * @param container          ViewGroup container
     * @param savedInstanceState previous activity state
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_messages, container, false);

        final EditText textEdit = (EditText) v.findViewById(R.id.text_edit);
        Button sendButton = (Button) v.findViewById(R.id.send_button);
        chatList = (ListView) v.findViewById(R.id.list);
        mFirebaseRef = new Firebase("https://dazzling-fire-990.firebaseio.com/conversations/" + conversation.getId() + "/messages");

        mFirebaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());

                messages = new ArrayList<Message>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Message message = new Message();
                    Author author = (Author) postSnapshot.child("author").getValue(Author.class);
                    String content = (String) postSnapshot.child("content").getValue();
                    String date = (String) postSnapshot.child("date").getValue();
                    message.setAuthor(author);
                    message.setContent(content);
                    message.setDate(date);
                    messages.add(message);
                }
                adapter = new ConversationAdapter(getContext(), messages);
                chatList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textEdit.getText().toString().equals("")) {
                    Message message = new Message();
                    message.setContent(textEdit.getText().toString());
                    Author author = new Author();
                    try {
                        String userName = mFirebaseRef.getAuth().getProviderData().get("email").toString();
                        author.setName(userName);
                        author.setUid(mFirebaseRef.getAuth().getUid());
                    } catch (Exception e) {
                        author.setName("Anonim");
                        author.setUid("empty");
                    }
                    author.setAvatarUrl("https://secure.gravatar.com/avatar/c1ede7abf7bab660fea720609690d9f5?d=retro");
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy, HH:mm:ss");
                    Date date = new Date();
                    message.setDate(dateFormat.format(date));
                    message.setAuthor(author);
                    Map<String, Object> values = new HashMap<>();
                    mFirebaseRef.push().setValue(message);
                    textEdit.setText("");
                }
            }
        });

        return v;
    }


    /**
     * Android OS method called when view is clicked
     *
     * @param v clicked view
     */
    @Override
    public void onClick(View v) {
        Context context = getActivity();
     //   CharSequence text = "Wyslano: " + messageEditText.getText();
        int duration = Toast.LENGTH_LONG;

      //  Toast toast = Toast.makeText(context, text, duration);
    //    toast.show();
    }


}
