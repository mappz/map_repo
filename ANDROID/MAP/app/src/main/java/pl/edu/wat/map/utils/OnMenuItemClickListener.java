package pl.edu.wat.map.utils;

import android.view.View;

/**
 * Interface for menu onClickListener
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public interface OnMenuItemClickListener {

    /**
     * Called when menu item is clicked
     * @param item item
     * @param v view
	 */
    public void onMenuItemClickListener(int item, View v);

}
