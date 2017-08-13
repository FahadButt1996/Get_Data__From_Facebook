package com.example.hp.myapplication;

/**
 * Created by M.FAHAD on 8/12/2017.
 */
        import android.content.Context;
        import android.support.annotation.LayoutRes;
        import android.support.annotation.NonNull;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

/**
 * Created by M.FAHAD on 7/31/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context ;
    ArrayList<facebookData> arrayList;
    private ImageView image;
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<facebookData> objects) {
//        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_view_item = null;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list_view_item = layoutInflater.inflate(R.layout.fb_data_item,null);
        }
        else{
            list_view_item = convertView;
        }
         facebookData fb = arrayList.get(position);
        ((TextView)list_view_item.findViewById(R.id.Message)).setText(fb.getMessage());
        ((TextView)list_view_item.findViewById(R.id.created_time)).setText(fb.getCreated_time());
        ((TextView)list_view_item.findViewById(R.id.Story)).setText(fb.getStory());
        return list_view_item;
    }
}
