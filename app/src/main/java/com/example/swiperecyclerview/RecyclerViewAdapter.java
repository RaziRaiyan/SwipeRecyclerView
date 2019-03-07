package com.example.swiperecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<Item> mItems;
    private Context mContext;

    private Item mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;

    public RecyclerViewAdapter(List<Item> items, Context context) {
        mItems = items;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_header.setText(mItems.get(i).getHeader());
        myViewHolder.tv_desc.setText(mItems.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return (mItems==null||mItems.size()==0)?0:mItems.size();
    }

    public void loadItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        //Perform delete operation
        mRecentlyDeletedItem = mItems.get(position);
        mRecentlyDeletedItemPosition = position;
        mItems.remove(position);
        notifyDataSetChanged();
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = ((MainActivity)mContext).findViewById(R.id.main_layout);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDelete();
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        mItems.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public Context getContext() {
        return mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_header;
        private TextView tv_desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.textView);
            tv_desc = itemView.findViewById(R.id.textView2);
        }
    }
}
