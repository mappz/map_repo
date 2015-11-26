package pl.edu.wat.map.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import pl.edu.wat.map.R;
import pl.edu.wat.map.fragments.GroupsFragment;
import pl.edu.wat.map.adapters.NavDrawerAdapter;
import pl.edu.wat.map.fragments.ReadMessagesFragment;
import pl.edu.wat.map.fragments.SendMessagesFragment;
import pl.edu.wat.map.utils.OnMenuItemClickListener;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {

    private Toolbar toolbar;
    private boolean isLoggedIn = false;
    private ActionBarDrawerToggle mDrawerToggle;

    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private NavDrawerAdapter mAdapter;                        // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout Drawer;
    private MenuItem login;
    private String titles[] = {"Wyswietlanie wiadomosci", "Wysylanie wiadomosci",
            "Grupy"};

    private final String TAG = "FRAGMENT REPLACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setOptionsVisibility();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        isLoggedIn = sharedPref.getBoolean("loggedn_in", false);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReadMessagesFragment readMessagesFragment = new ReadMessagesFragment();
        transaction.replace(R.id.fragment_container, readMessagesFragment, ReadMessagesFragment.class.getName());
        transaction.addToBackStack(TAG);
        transaction.commit();
    }



    private void setOptionsVisibility() {
        mAdapter = new NavDrawerAdapter(titles, this);

        mAdapter.notifyDataSetChanged();
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setItemListener(this);

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
    }

    @Override
    public void onMenuItemClickListener(int item, View v) {
        switch(item)
        {
            case 1:
            {
                startFramgnet(new ReadMessagesFragment(), null);
                break;
            }
            case 2:
            {
                startFramgnet(new SendMessagesFragment(), null);
                break;
            }
            case 3:
            {
                startFramgnet(new GroupsFragment(), null);
                break;
            }
        }
        Drawer.closeDrawers();
    }


    public void startFramgnet(Fragment fragment, Bundle args)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        login = menu.findItem(R.id.action_login);

        if(isLoggedIn){
            login.setTitle("Logout");
        }
        else{
            login.setTitle("Sign in");
        }

        setMEnuVisibility();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_login: {
                if(isLoggedIn) {
                    login.setTitle("Logout");
                    Context context = getApplicationContext();
                    CharSequence text = "Wylogowano";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    login.setTitle("Sign in");
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setMEnuVisibility()
    {
        //TODO chceck is user is logged
       /* if(user != null)
        {
            login.setVisible(false);
        }
        else
        {
            login.setVisible(true);
        }*/
    }

}