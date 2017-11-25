package com.kingdatasolutions.sintraba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.datamodel.MenuItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    List<MenuItem> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context mContext;
    private ClickListener mClickListener;

    public MenuAdapter(Context context, List<MenuItem> data) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_menu_item, parent, false);
        MenuViewHolder holder = new MenuViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MenuItem current = mData.get(position);
        holder.icon.setImageResource(current.id_icon);
        holder.title.setText(current.title);
        /*if (position == 1) {
            holder.menuItem.setPressed(true);
        }*/
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout menuItem;
        ImageView icon;
        TextView title;

        public MenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            menuItem = (RelativeLayout) itemView.findViewById(R.id.menu_item);
            icon = (ImageView) itemView.findViewById(R.id.list_icon);
            title = (TextView) itemView.findViewById(R.id.list_text);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.itemClicked(view, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
