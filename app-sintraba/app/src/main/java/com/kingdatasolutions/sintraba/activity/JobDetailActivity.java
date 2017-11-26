package com.kingdatasolutions.sintraba.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.kingdatasolutions.sintraba.R;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.adapter.CertificationAdapter;
import com.kingdatasolutions.sintraba.adapter.PhotoAdapter;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Certification;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Information;
import com.kingdatasolutions.sintraba.datamodel.Job;
import com.kingdatasolutions.sintraba.datamodel.Photo;
import com.kingdatasolutions.sintraba.network.VolleySingleton;
import com.kingdatasolutions.sintraba.utility.CustomGridLayoutManager;
import com.kingdatasolutions.sintraba.utility.CustomLinearLayoutManager;

import java.util.ArrayList;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class JobDetailActivity extends AppCompatActivity implements CertificationAdapter.ClickListener { //, PhotoAdapter.ClickListener {

    private Toolbar mToolbar;

    //The key used to store arraylist of destination objects to and from parcelable
    private static final String STATE_INFORMATION = "state_information";

    private ArrayList<Certification> mListCertification = new ArrayList<>();
    private ImageView mMainImage;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private Job mJob;
    private Department mDepartment;
    private SinTrabaDBAdapter dbAdapter;
    private RecyclerView mRecyclerView;
    private CertificationAdapter mAdapter;
    private WebView webView;
    private CardView mCertification;

    //private Information mInformationDes;
    //private Information mInformationMap;
    private ShareActionProvider mShareActionProvider;
    private Certification currentCertification;
    private String imageAddress = "";
    private PhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        mToolbar = (Toolbar) findViewById(R.id.job_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMainImage = (ImageView) findViewById(R.id.job_detail_image_paralax);
        mCertification = (CardView) findViewById(R.id.job_information);
        mVolleySingleton = VolleySingleton.getsInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        dbAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());

        Intent jobActivity = getIntent();
        mJob = dbAdapter.getJob(jobActivity.getExtras().getInt("ID_JOB"));
        mDepartment = dbAdapter.getDepartment(mJob.getIdDepartment());

        mDepartment = dbAdapter.getDepartment(mJob.getIdDepartment());
        //mListCertification = dbAdapter.getPhotoList(Utility.getJobCategory(), mJob.getId());


        TextView title = (TextView) findViewById(R.id.job_detail_title);
        TextView subtitle = (TextView) findViewById(R.id.job_detail_subtitle);
        title.setText(mJob.getName());
        subtitle.setText(mDepartment.getName());

        /*
        mRecyclerView = (RecyclerView) findViewById(R.id.job_detail_certification_list);
        mAdapter = new CertificationAdapter(SinTrabaApp.getAppContext());
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        CustomLinearLayoutManager layout = new CustomLinearLayoutManager(SinTrabaApp.getAppContext());
        mRecyclerView.setLayoutManager(layout);
        if (savedInstanceState != null) {
            mListCertification = savedInstanceState.getParcelableArrayList(STATE_INFORMATION);
        } else {
            //mListInformation = dbAdapter.getInformationListExceptDescription(Utility.getCitClassification(), mCit.getId());
        }

        mAdapter.setData(mListCertification);

        //mInformationDes = dbAdapter.getInformation(Utility.getCitClassification(), mCit.getId(), Utility.getInfoTypeInformation());

        //mInformationDes = mJob.getDescription();

        //if (mInformationDes.getId() > 0 && mInformationDes.getInfoValue() != null && !mInformationDes.getInfoValue().isEmpty()) {
            String youtContentStr = "";

        //    if ("es".equals(SinTrabaApp.sDefSystemLanguage)) {
                youtContentStr = String.valueOf(Html
                        .fromHtml("<![CDATA[<body style=\"text-align:justify;background: #F9F9F9;color:#999;font-size: smaller; \">"
                                + mJob.getDescription()
                                + "</body>]]>"));
        //    } else {
        //        youtContentStr = String.valueOf(Html
        //                .fromHtml("<![CDATA[<body style=\"text-align:justify;background: #F9F9F9;color:#999;font-size: smaller; \">"
        //                        + mInformationDes.getInfoValueEng()
        //                        + "</body>]]>"));
        //    }

            WebSettings settings = webView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            webView.loadData(youtContentStr, "text/html; charset=UTF-8", null);
        //} else {
        //    mCitDescription.setVisibility(View.GONE);
        //}
*/
    }

    protected int columnsQuantity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int spanCount = Math.max(1, metrics.widthPixels / 700);
        if (spanCount == 1)
            spanCount = 2;
        return spanCount;
    }

    private void loadImages(String urlThumbnail, final ImageView imageView) {
        if (!urlThumbnail.equals("")) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    imageView.setImageResource(R.drawable.job_detail_default);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_job_detail, menu);
        //MenuItem shareItem = menu.findItem(R.id.job_detail_action_share);
        //mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        //setShareIntent();
        return true;
    }

    /*
    private void setShareIntent() {
        if (mShareActionProvider != null) {

            String textToShare = getString(R.string.share_text_1) +
                    " " +
                    mJob.getName() +
                    " " +
                    getString(R.string.share_text_2);

            try {
                Information websiteInformation = dbAdapter.getInformation(Utility.getCitClassification(), mJob.getId(), Utility.getInfoTypeWeb());
                if (websiteInformation  != null && websiteInformation.getInfoValue().length() > 0) {
                    textToShare = textToShare +
                            " " +
                            getString(R.string.share_text_3) +
                            " " +
                            websiteInformation.getInfoValue() +
                            ".";
                }
            } catch (Exception ex) {

            }

            textToShare = textToShare +
                    " " +
                    getString(R.string.share_text_4) +
                    " " +
                    getString(R.string.share_text_android);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sin Traba App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the destination list to a parcelable prior to rotation or configuration change
        //outState.putParcelableArrayList(STATE_INFORMATION, mListInformation);
    }

    @Override
    public void itemClicked(View view, int idClassification, int idParent, int idType) {
        /*
        currentCertification = dbAdapter.getInformation(idClassification, idParent, idType);
        String webSite = "";
        if (idType == 4) {
            if (currentInformation.getInfoValue().startsWith("http")) {
                webSite = currentInformation.getInfoValue();
            } else {
                webSite = "http://" + currentInformation.getInfoValue();
            }
        }
        switch (idType) {
            case 2:
                new AlertDialog.Builder(JobDetailActivity.this)
                        .setTitle(getResources().getString(R.string.phone_call_title))
                        .setMessage(getResources().getString(R.string.phone_call_content) + " " + currentInformation.getInfoMask() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String number = "tel:" + currentInformation.getInfoValue().trim();
                                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
            case 4:
                Intent webBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSite));
                startActivity(webBrowserIntent);
                break;
            case 6:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { currentInformation.getInfoValue() } );
                //emailIntent.putExtra(Intent.EXTRA_CC, "nestor.bonilla.s@@gmail.com");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nicaragua App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.email_chooser)));
                break;
            case 7:

                break;
            default:
                break;
        }
        */
    }

    /*
    @Override
    public void itemClicked(View view, int idPhoto) {
        Intent photoActivity = new Intent(this, PhotoActivity.class);
        photoActivity.putExtra("ID_PHOTO", idPhoto);
        startActivity(photoActivity);
    }*/
}