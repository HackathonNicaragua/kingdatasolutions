package com.kingdatasolutions.sintraba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Certification;
import com.kingdatasolutions.sintraba.datamodel.Information;
import com.kingdatasolutions.sintraba.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nestorbonilla on 11/26/17.
 */

public class CertificationAdapter extends RecyclerView.Adapter<CertificationAdapter.CertificationViewHolder> {

    private List<Certification> data = Collections.emptyList();
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private Context context;
    private ClickListener clickListener;

    private SinTrabaDBAdapter dbAdapter;

    public CertificationAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getsInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        dbAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());
    }

    public void setData(ArrayList<Certification> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public CertificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_certification_item, parent, false);
        CertificationViewHolder holder = new CertificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CertificationViewHolder holder, int position) {
        Certification current = data.get(position);
        holder.title.setText(current.getName());
        holder.image.setImageResource(R.drawable.ic_menu_about);

    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CertificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;

        public CertificationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.information_icon);
            title = (TextView) itemView.findViewById(R.id.information_text);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                Certification current = data.get(getPosition());
                //clickListener.itemClicked(view, current.getIdClassification(), current.getIdParent(), current.getIdType());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int idClassification, int idParent, int idType);
    }
}