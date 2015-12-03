package pl.edu.wat.map.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.wat.map.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendMessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMessagesFragment extends Fragment implements View.OnClickListener {

    private EditText messageEditText;
    private Button sendButton;
    private String conversationId;
    private final String param1 = "conversation";

    public static SendMessagesFragment newInstance() {
        SendMessagesFragment fragment = new SendMessagesFragment();
        return fragment;
    }

    public SendMessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationId = savedInstanceState.getString(param1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_messages, container, false);
        messageEditText = (EditText) v.findViewById(R.id.message_text_send);
        sendButton = (Button) v.findViewById(R.id.button_send);
        sendButton.setOnClickListener(this);

        return v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        Context context = getActivity();
        CharSequence text = "Wyslano: " + messageEditText.getText();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
