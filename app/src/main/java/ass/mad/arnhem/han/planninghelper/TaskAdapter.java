package ass.mad.arnhem.han.planninghelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mees on 5/17/2016.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.myViewHolder> {

    int dayNumber;

    private LayoutInflater inflater;
    private Context context;
    private List<RecyclerviewTask> items = new ArrayList<>();
    private ItemSelectedListener itemSelectedListener;

    public TaskAdapter(Context context, ItemSelectedListener listener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        itemSelectedListener = listener;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(RecyclerviewTask recyclerviewTask) {
        items.add(recyclerviewTask);
        notifyDataSetChanged();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder holder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_task_row, parent, false);
        holder = new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        final RecyclerviewTask current = items.get(position);

        holder.taskTitle.setText(current.getTaskTitle());
        holder.taskDescription.setText(current.getTaskDescription());
        holder.startTime.setText(current.getStartTime());
        holder.endTime.setText(current.getEndTime());
        holder.icon.setImageDrawable(context.getDrawable(current.getIcon()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectedListener.onIconSelected(position);
            }
        });

    }

    /*@Override
    public int getItemViewType(int position) {
        RecyclerviewTask item;
        item = getDataByPosition(position);
        return item.getViewType();
    }*/

    public RecyclerviewTask getDataByPosition(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDescription;
        TextView startTime;
        TextView endTime;

        ImageView icon;

        public myViewHolder(View itemView) {
            super(itemView);
            taskTitle = (TextView) itemView.findViewById(R.id.task_title);
            taskDescription = (TextView) itemView.findViewById(R.id.task_description);
            startTime = (TextView) itemView.findViewById(R.id.task_start_time);
            endTime = (TextView) itemView.findViewById(R.id.task_end_time);

            icon = (ImageView) itemView.findViewById(R.id.task_icon);
        }
    }
}
