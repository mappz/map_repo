package pl.edu.wat.map.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import pl.edu.wat.map.R;

/**
 * Login activity used to login and in application
 * It is first activity displayed if user is not logged in
 * @author Marcel Paduch
 * @version 1
 * @since 17/12/2015
 */
public class LoginActivity extends ActionBarActivity  {

    private static final String TAG = LoginActivity.class.getSimpleName();

    /* *************************************
     *              GENERAL                *
     ***************************************/
    /* TextView that is used to display information about the logged in user */
    private TextView mLoggedInStatusTextView;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;


    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    /* A reference to the Firebase */
    private Firebase mFirebaseRef;

    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;




    /* *************************************
     *              PASSWORD               *
     ***************************************/
    private Button mPasswordLoginButton;

    /* *************************************
     *            ANONYMOUSLY              *
     ***************************************/
    private Button mAnonymousLoginButton;

	/**
     * Android OS method that is called when activity is created
     * @param savedInstanceState previous
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getApplicationContext());
        /* Load the view and display it */
        setContentView(R.layout.activity_login);


        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);


        mRegisterButton = (Button) findViewById(R.id.register);
        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


        /* *************************************
         *               PASSWORD              *
         ***************************************/
        mPasswordLoginButton = (Button) findViewById(R.id.login_with_password);
        mPasswordLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithPassword();
            }
        });

        /* *************************************
         *              ANONYMOUSLY            *
         ***************************************/
        /* Load and setup the anonymous login button */
        mAnonymousLoginButton = (Button) findViewById(R.id.login_anonymously);
        mAnonymousLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAnonymously();
            }
        });

        /* *************************************
         *               GENERAL               *
         ***************************************/
        mLoggedInStatusTextView = (TextView) findViewById(R.id.login_status);

        /* Create the Firebase ref that is used for all authentication with Firebase */
        mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();

        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                mAuthProgressDialog.hide();
                setAuthenticatedUser(authData);
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        mFirebaseRef.addAuthStateListener(mAuthStateListener);
    }

	/**
     * Android OS method that is called when activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();


        // if changing configurations, stop tracking firebase session.
        mFirebaseRef.removeAuthStateListener(mAuthStateListener);
    }

    /**
     * This method fires when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */

	/**
     * Android OS method called when menu is created
     * @param menu Object containing menu
     * @return menu creation status
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* If a user is currently authenticated, display a logout menu */
        if (this.mAuthData != null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } else {
            return false;
        }
    }

	/**
     * Android OS method that is called when menu item is clicked
     * @param item Menu item
     * @return status of operation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        /*if (id == R.id.action_logout) {
            logout();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * Unauthenticate from Firebase and from providers where necessary.
     */
    public void logout() {
        if (this.mAuthData != null) {
            /* logout of Firebase */
            mFirebaseRef.unauth();
            /* Update authenticated user and show login buttons */
            setAuthenticatedUser(null);
        }
    }

    /**
     * This method will attempt to authenticate a user to firebase given an oauth_token (and other
     * necessary parameters depending on the provider)
     */
    private void authWithFirebase(final String provider, Map<String, String> options) {
        if (options.containsKey("error")) {
            showErrorDialog(options.get("error"));
        } else {
            mAuthProgressDialog.show();
            if (provider.equals("twitter")) {
                // if the provider is twitter, we pust pass in additional options, so use the options endpoint
                mFirebaseRef.authWithOAuthToken(provider, options, new AuthResultHandler(provider));
            } else {
                // if the provider is not twitter, we just need to pass in the oauth_token
                mFirebaseRef.authWithOAuthToken(provider, options.get("oauth_token"), new AuthResultHandler(provider));
            }
        }
    }

    /**
     * Once a user is logged in, take the mAuthData provided from Firebase and "use" it.
     */
    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            /* Hide all the login buttons */


            mPasswordLoginButton.setVisibility(View.GONE);
            mAnonymousLoginButton.setVisibility(View.GONE);
            mEmailEditText.setVisibility(View.GONE);
            mPasswordEditText.setVisibility(View.GONE);
            mLoggedInStatusTextView.setVisibility(View.VISIBLE);
            mRegisterButton.setVisibility(View.GONE);
            /* show a provider specific status text */
            String name = null;
            if (authData.getProvider().equals("anonymous")
                    || authData.getProvider().equals("password")) {
                name = authData.getUid();
            } else {
                Log.e(TAG, "Invalid provider: " + authData.getProvider());
            }
            if (name != null) {
                mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
            }
        } else {
            /* No authenticated user show all the login buttons */

            mPasswordLoginButton.setVisibility(View.VISIBLE);
            mAnonymousLoginButton.setVisibility(View.VISIBLE);
            mEmailEditText.setVisibility(View.VISIBLE);
            mPasswordEditText.setVisibility(View.VISIBLE);
            mRegisterButton.setVisibility(View.VISIBLE);
            mLoggedInStatusTextView.setVisibility(View.GONE);

        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        supportInvalidateOptionsMenu();
    }

    /**
     * Show errors to users
     */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setLoggedIn();
            setAuthenticatedUser(authData);

            Context context = getApplicationContext();
            CharSequence text = "Zalogowano";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }

	/**
     * Method used to remember that user is logged in
     */
    public void setLoggedIn(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("logged_in", true);
        editor.apply();
    }










    /* ************************************
     *              PASSWORD              *
     **************************************
     */

	/**
     * Method used to login with password
     */
    public void loginWithPassword() {
        mAuthProgressDialog.show();
        mFirebaseRef.authWithPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(), new AuthResultHandler("password"));
    }

    /* ************************************
     *             ANONYMOUSLY            *
     **************************************
     */
    public void loginAnonymously() {
        mAuthProgressDialog.show();
        mFirebaseRef.authAnonymously(new AuthResultHandler("anonymous"));
    }
}