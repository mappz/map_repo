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
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.adapters.ConversationAdapter;
import pl.edu.wat.map.model.Conversation;
import pl.edu.wat.map.model.Message;

/**
 * Fragment used as container for send messages view
 * @author Marcel Paduch
 * @version 1
 * @since 17/12/2015
 */
public class SendMessagesFragment extends Fragment implements View.OnClickListener {

    private EditText messageEditText;
    private Button sendButton;
    private String conversationId;
    private ListView listView;
    private Firebase ref;
    private ConversationAdapter adapter;
    private final String param1 = "conversation";
    private Conversation conversation;


    public SendMessagesFragment() {
        // Required empty public constructor
    }
    /**
     * Android OS method called when activity is created
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
     * @param inflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState previous activity state
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_send_messages, container, false);
        messageEditText = (EditText) v.findViewById(R.id.message_text_send);
        sendButton = (Button) v.findViewById(R.id.button_send);
        sendButton.setOnClickListener(this);
        listView = (ListView) v.findViewById(R.id.message_list);

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://dazzling-fire-990.firebaseio.com/conversations" + conversation.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());

                ArrayList<Message> messages = new ArrayList<>();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    messages = (ArrayList) postSnapshot.child("messages").getValue();
                }
                if(adapter == null)
                {
                    adapter = new ConversationAdapter(getContext(), messages);
                    listView.setAdapter(adapter);
                }
                else
                {
                    adapter.setMessages(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return v ;
    }


    /**
     * Android OS method called when view is clicked
     * @param v clicked view
	 */
    @Override
    public void onClick(View v) {
        Context context = getActivity();
        CharSequence text = "Wyslano: " + messageEditText.getText();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }




}
