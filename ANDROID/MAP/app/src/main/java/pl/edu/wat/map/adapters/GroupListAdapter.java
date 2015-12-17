package pl.edu.wat.map.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.edu.wat.map.R;
import pl.edu.wat.map.utils.OnGroupClickListener;
import pl.edu.wat.map.utils.OnSubscribeClickListener;

/**
 * Adapter for groups
 * @author Hubert Faszcza
 * @version 1
 * @since 17/12/2015
 */
public class GroupListAdapter extends ArrayAdapter<String>
{
        private ArrayList<String> groups;
        private int closestLesson = -1;
        private Context context;
        private OnGroupClickListener onGroupClickListener;
        private OnSubscribeClickListener subscribeClickListener;

        public class ViewHolder {
            TextView name;
            Button subscribeButton;
        }

        public GroupListAdapter(Context context, int layoutResourceId, ArrayList<String> groups) {
        super(context, layoutResourceId, groups);
        this.context = context;
        this.groups = groups;
    }

    public void setOnClickListener(OnGroupClickListener listener)
    {
        this.onGroupClickListener = listener;
    }

    public void setOnSubscribeClickListener(OnSubscribeClickListener listener)
    {
        this.subscribeClickListener = listener;
    }

	/**
     * Gets view
     * @param position position of view
     * @param convertView holder class
     * @param parent parent view
     * @return view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null || closestLesson != -1) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.cell_groups_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.group_name);
            viewHolder.subscribeButton = (Button) convertView
                    .findViewById(R.id.subscribe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(groups.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGroupClickListener != null) {
                    onGroupClickListener.onGroupClickListener(groups.get(position));
                }
            }
        });

        viewHolder.subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribeClickListener != null) {
                    subscribeClickListener.onSubscribeClick(groups.get(position));
                }
            }
        });

        return convertView;
    }
}

