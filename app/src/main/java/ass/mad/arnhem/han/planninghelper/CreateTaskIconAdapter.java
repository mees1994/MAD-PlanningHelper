package ass.mad.arnhem.han.planninghelper;


import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

/**
 * Created by Mees on 5/21/2016.
 */
public class CreateTaskIconAdapter extends BaseAdapter {

    String[] iconName;
    int[] icon;
    Context context;
    private static LayoutInflater inflater=null;
    private ItemSelectedListener itemSelectedListener;

    public CreateTaskIconAdapter(String[] iconName, int[] icon, Context context, ItemSelectedListener listener) {
        this.iconName = iconName;
        this.context = context;
        this.icon = icon;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemSelectedListener = listener;
    }

    @Override
    public int getCount() {
        return iconName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.view_list_select_icon_row, null);
        holder.tv=(TextView) rowView.findViewById(R.id.create_task_icon_name_list);
        holder.img=(ImageView) rowView.findViewById(R.id.create_task_icon_image_list);
        holder.tv.setText(iconName[position]);
        holder.img.setImageDrawable(context.getResources().getDrawable(icon[position]));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectedListener.onIconSelected(position);
            }
        });
        return rowView;
    }
}
