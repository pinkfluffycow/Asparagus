package com.example.asparagus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsListAdapter extends RecyclerView.Adapter<RequestsListAdapter.MyViewHolder> {
    private ArrayList<Request> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView avatarImage;
        public TextView textView1, textView2;

        public MyViewHolder(View v) {
            super(v);
            avatarImage = v.findViewById(R.id.avatarImage);
            textView1 = v.findViewById(R.id.textView1);
            textView2 = v.findViewById(R.id.textView2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestsListAdapter(ArrayList<Request> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RequestsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requests_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.avatarImage.setImageResource(R.drawable.sample_avatar);
        holder.textView1.setText("Gowri");
        holder.textView2.setText("needs " + mDataset.get(position).getQuantity().toString() + " " + mDataset.get(position).getItemName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}