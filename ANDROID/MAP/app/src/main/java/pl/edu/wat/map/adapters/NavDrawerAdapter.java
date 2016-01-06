package pl.edu.wat.map.adapters;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.edu.wat.map.R;
import pl.edu.wat.map.utils.OnMenuItemClickListener;

/**
 * Creates navigation drawer menu
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];
    OnMenuItemClickListener itemListener;
    private Context context;


    public void setItemListener(OnMenuItemClickListener itemListener) {
        this.itemListener = itemListener;
    }


    public NavDrawerAdapter(String Titles[], Context context){

        this.context = context;
        mNavTitles = Titles;
    }


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them
    /**
     * Helper class for holding views
     * @author Hubert Faszcza
     * @version 1
     * @since 17/12/2015
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        int Holderid;
        TextView rowText;
        TextView email;


        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            if (ViewType == TYPE_ITEM)
            {
                rowText = (TextView) itemView.findViewById(R.id.nav_drawer_row_text);
                itemView.setOnClickListener(this);

            }
            else
            {
                Holderid = 1;
                email = (TextView) itemView.findViewById(R.id.email);
            }
        }
        public void onClick(View v)
        {
            itemListener.onMenuItemClickListener(getAdapterPosition(), v);
        }
    }


    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    /**
     * Called when ViewHolder is created
     * @param parent parent View
     * @param viewType view type
     * @return ViewHolder Instance
	 */
    @Override
    public NavDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_row,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }
        return null;
    }





    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row

	/**
     * Called when View Holder is bind
     * @param holder ViewHolder instance
     * @param position position
     */
    @Override
    public void onBindViewHolder(NavDrawerAdapter.ViewHolder holder, int position)
    {
        // as the list view is going to be called after the header view so we decrement the
        // position by 1 and pass it to the holder while setting the text and image
        if(holder.Holderid != 1)
        {
            holder.rowText.setText(mNavTitles[position-1]); // Setting the Text with the array of our Titles
        }
    }


    // This method returns the number of items present in the list

	/**
     * Returns item count
     * @return item count
     */
    @Override
    public int getItemCount() {
        return mNavTitles.length+1; // the number of items in the list will be +1 the titles including the header view.
    }

    /**
     * With the following method we check what type of view is being passed
     * @param position position of the view
     * @return type
	 */
    //
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    /**
     * Check wheter position is header or not
     * @param position item position
     * @return boolean
	 */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}