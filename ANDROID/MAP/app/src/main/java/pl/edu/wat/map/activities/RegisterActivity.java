package pl.edu.wat.map.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import pl.edu.wat.map.R;

/**
 * Created by marcel on 2015-10-18.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
	private Button mRegisterButton;
	private EditText mEmailText;
	private EditText mPasswordText;
	private EditText mRepeatPasswordText;
	private TextView mMessageText;
	private Firebase ref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ref = new Firebase(getString(R.string.firebase_url));

		setContentView(R.layout.activity_register);

		mEmailText = (EditText)findViewById(R.id.register_email);
		mPasswordText = (EditText)findViewById(R.id.register_password);
		mRepeatPasswordText = (EditText)findViewById(R.id.repeat_password);
		mMessageText = (TextView) findViewById(R.id.message_text);
		mRegisterButton = (Button)findViewById(R.id.register_button);

		mRegisterButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.register_button:
				if(mPasswordText.getText().toString().equals(mRepeatPasswordText.getText().toString())){
					ref.createUser(mEmailText.getText().toString(), mPasswordText.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
						@Override
						public void onSuccess(Map<String, Object> result) {
							mMessageText.setTextColor(Color.GREEN);
							mMessageText.setText(getString(R.string.registration_sucessfull));
						}

						@Override
						public void onError(FirebaseError firebaseError) {
							mMessageText.setTextColor(Color.RED);
							mMessageText.setText(getString(R.string.registration_error));
						}
					});
				}
				else{
					mMessageText.setTextColor(Color.RED);
					mMessageText.setText(getString(R.string.password_missmatch));
				}

				break;
		}

	}
}
