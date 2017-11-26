package com.kingdatasolutions.sintraba.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Job;
import com.kingdatasolutions.sintraba.logging.L;
import com.kingdatasolutions.sintraba.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    //private ImageLoader mImageLoader;
    private Context mContext;
    private ClickListener clickListener;
    private SinTrabaDBAdapter dbAdapter;

    public JobAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getsInstance();
        //mImageLoader = mVolleySingleton.getImageLoader();
        dbAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());
    }

    public void setData(ArrayList<Job> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_job_item, parent, false);
        JobViewHolder holder = new JobViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        Job current = mData.get(position);
        L.m("department " + current.getIdDepartment());
        Department department = dbAdapter.getDepartment(current.getIdDepartment());
        L.m("job name " + current.getName());
        L.m("department name " + department.getName());
        //String imageAddress = dbAdapter.getPhotoMainImageAddress(current.getId());
        String imageAddress = "";
        holder.title.setText(current.getName() + "");
        //holder.subtitle.setText(department.getName() + "");

        if (current.getId() == 1) {
            //holder.image.setImageResource(R.drawable.job_default);

            Uri uri = new Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                    .path(String.valueOf(R.drawable.job_electrical_default))
                    .build();
            holder.image.setImageURI(uri);

            //Uri image =  Uri.parse("res:///" + R.drawable.job_default);
            //holder.image.setBackground(image);
        } else if (current.getId() == 2) {

            Uri uri = new Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                    .path(String.valueOf(R.drawable.job_electrical_default))
                    .build();
            holder.image.setImageURI(uri);

            //holder.image.setImageResource(R.drawable.job_electrical_default);
        }
        /*
        if (imageAddress.isEmpty()) {
            //holder.image.setImageResource(R.drawable.job_detail_default);
        } else {
            Uri uri = Uri.parse("http://" + imageAddress);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(holder.image.getController())
                    .build();
            holder.image.setController(controller);
        }
        */
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView image;
        TextView title;
        TextView subtitle;
        CardView cardView;

        public JobViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = (CardView) itemView.findViewById(R.id.job_card_view);
            image = (SimpleDraweeView) itemView.findViewById(R.id.job_image);
            title = (TextView) itemView.findViewById(R.id.job_title);
            //subtitle = (TextView) itemView.findViewById(R.id.job_subtitle);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}