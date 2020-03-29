package com.example.asparagus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementsListAdapter extends RecyclerView.Adapter<AchievementsListAdapter.MyViewHolder> {
    private ArrayList<Achievement> mDataset = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView avatarImage;
        public TextView nameText, descriptionText, completeText;
        public ProgressBar progressBar;

        public MyViewHolder(View v) {
            super(v);
            avatarImage = v.findViewById(R.id.avatarImage);
            nameText = v.findViewById(R.id.textView1);
            descriptionText = v.findViewById(R.id.textView2);
            progressBar = v.findViewById(R.id.achievementProgressBar);
            completeText = v.findViewById(R.id.textView3);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AchievementsListAdapter(ArrayList<Achievement> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AchievementsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievements_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.avatarImage.setImageResource(R.drawable.achievement);
        holder.nameText.setText(mDataset.get(position).getName());
        holder.descriptionText.setText(mDataset.get(position).getDescription());
        holder.progressBar.setMax(mDataset.get(position).getMaxValue());
        holder.progressBar.setProgress(mDataset.get(position).getCurrValue());

        if(holder.progressBar.getProgress() == holder.progressBar.getMax())
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ECECEC"));
            holder.itemView.setAlpha((float) 0.8);
            holder.progressBar.setVisibility(View.GONE);
            holder.completeText.setVisibility(View.VISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}