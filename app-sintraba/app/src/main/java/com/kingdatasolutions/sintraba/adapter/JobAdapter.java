package com.kingdatasolutions.sintraba.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Job;

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
    private ImageLoader mImageLoader;
    private Context mContext;
    private ClickListener clickListener;
    private SinTrabaDBAdapter dbAdapter;

    public JobAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getsInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
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
        Department department = dbAdapter.getDepartment(current.getIdDepartment());
        String imageAddress = dbAdapter.getPhotoMainImageAddress(current.getId());
        holder.title.setText(current.getName());
        if ("es".equals(SinTrabaApp.sDefSystemLanguage)) {
            holder.subtitle.setText(department.getName());
        } else {
            holder.subtitle.setText(department.getName());
        }

        if (imageAddress.isEmpty()) {
            holder.corner.setVisibility(View.VISIBLE);
            holder.image.setImageResource(R.drawable.job_detail_default);
        } else {
            holder.corner.setVisibility(View.INVISIBLE);
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

        ImageView corner;
        SimpleDraweeView image;
        TextView title;
        TextView subtitle;
        CardView cardView;

        public CitViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = (CardView) itemView.findViewById(R.id.job_card_view);
            corner = (ImageView) itemView.findViewById(R.id.job_corner);
            image = (SimpleDraweeView) itemView.findViewById(R.id.job_image);
            title = (TextView) itemView.findViewById(R.id.job_title);
            subtitle = (TextView) itemView.findViewById(R.id.job_subtitle);
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