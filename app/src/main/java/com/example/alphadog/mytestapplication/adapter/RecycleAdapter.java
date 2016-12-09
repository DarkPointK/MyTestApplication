package com.example.alphadog.mytestapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.mvp.module.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2016/12/5.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<RecyclerItem> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    View view;

    public RecycleAdapter(Context context, List<RecyclerItem> mItems) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.item_recycle_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.render(position);
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return 0;
//        return mItems.size() > 9 ? 10 : mItems.size();
        return mItems.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;

        ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContent = (TextView) view.findViewById(R.id.content);
        }

        void render(final int pos) {
            mTitle.setText(mItems.get(pos).getTitle());
            mContent.setText(mItems.get(pos).getContent());
            mContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了" + pos, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public List<RecyclerItem> getItems() {
        return mItems;
    }

    public void setItems(List<RecyclerItem> items) {
        mItems = items;
    }

}
