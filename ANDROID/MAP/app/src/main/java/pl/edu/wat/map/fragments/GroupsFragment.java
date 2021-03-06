package pl.edu.wat.map.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.adapters.GroupListAdapter;
import pl.edu.wat.map.utils.OnGroupClickListener;
import pl.edu.wat.map.utils.OnSubscribeClickListener;

/**
 * Fragment used as container for groups view
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class GroupsFragment extends Fragment {




    public GroupsFragment() {
        // Required empty public constructor
    }

	/**
     * Android OS method called when activity is created
     * @param savedInstanceState saved activity state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        ArrayList<String> groups = new ArrayList<String>();
        groups.add("Samochody");
        groups.add("WAT");
        groups.add("Projekt zespolowy");
        groups.add("Mistrzostwa świata");
        groups.add("Komputery");
        groups.add("Polityka");
        groups.add("Gry komputerowe");
        groups.add("Sport");
        ListView groupList = (ListView) view.findViewById(R.id.group_list);
        GroupListAdapter adapter =
                new GroupListAdapter(getActivity(), R.layout.cell_groups_item, groups);
        adapter.setOnClickListener(new OnGroupClickListener()
        {
            @Override
            public void onGroupClickListener(String item)
            {
                Toast.makeText(getActivity(), "Wygrano grupe " + item, Toast.LENGTH_LONG).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ReadMessagesFragment readMessagesFragment = new ReadMessagesFragment();
                transaction.replace(R.id.fragment_container, readMessagesFragment, ReadMessagesFragment.class.getName());
                transaction.addToBackStack("FRAGMENT REPLACE");
                transaction.commit();
            }
        });
        adapter.setOnSubscribeClickListener(new OnSubscribeClickListener()
        {
            @Override
            public void onSubscribeClick(String item)
            {
                Toast.makeText(getActivity(), "Dodano grupe " + item + " do subskrybowanych",
                        Toast.LENGTH_LONG).show();
            }
        });
        groupList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }


}
