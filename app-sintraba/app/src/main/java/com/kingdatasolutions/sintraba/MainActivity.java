package com.kingdatasolutions.sintraba;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kingdatasolutions.sintraba.activity.JobDetailActivity;
import com.kingdatasolutions.sintraba.adapter.JobAdapter;
import com.kingdatasolutions.sintraba.callback.GeneralLoadedListener;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Job;
import com.kingdatasolutions.sintraba.datamodel.JobCategory;
import com.kingdatasolutions.sintraba.datamodel.Setting;
import com.kingdatasolutions.sintraba.fragment.MenuFragment;
import com.kingdatasolutions.sintraba.task.TaskLoadGeneral;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, JobAdapter.ClickListener, Response.ErrorListener {

    private Toolbar mToolbar;
    private SinTrabaDBAdapter dbAdapter;
    private ArrayList<Job> mListJobComplete = new ArrayList<>();
    private ArrayList<Job> mListJob = new ArrayList<>();
    private ArrayList<Job> mListJobFilter;
    //private ArrayList<Parameter> mListParameter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private JobAdapter mAdapter;
    private TextView mTextError;
    private SearchView searchView;
    private boolean mSearchViewActive;
    private String mSearchViewString;
    private int toRefresh = 0;
    private JobCategory jobCategoryActive;
    private KProgressHUD hud;
    private JobCategory categoryActive;

    ArrayAdapter adapterDepartment;
    ArrayAdapter adapterTheme;

    ArrayList<String> mListCategory;
    ArrayList<String> mListDepartment;
    AppCompatSpinner spinnerCategory;
    AppCompatSpinner spinnerDepartment;
    private Setting settingCategory;
    private Setting settingDepartment;
    Setting updateApp;

    //The key used to store arraylist of destination objects to and from parcelable
    private static final String STATE_JOB = "state_job";
    private static final String STATE_JOB_COMPLETE = "state_job_complete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.job_app_bar);
        mToolbar.setSubtitle(getString(R.string.menu_job));
        setSupportActionBar(mToolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.job_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_menu);
        menuFragment.setUp(R.id.fragment_menu, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        dbAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());
        Log.d("UPDATEAPP", "ONCREATE");
        settingCategory = dbAdapter.getSetting("FILTERBYCATEGORY");
        settingDepartment = dbAdapter.getSetting("FILTERBYDEPARTMENT");

        mRecyclerView = (RecyclerView) findViewById(R.id.job_list);
        mTextError = (TextView) findViewById(R.id.job_error);
        mAdapter = new JobAdapter(SinTrabaApp.getAppContext());
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(SinTrabaApp.getAppContext(), columnsQuantity());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isPositionHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });

        categoryActive = dbAdapter.getJobCategory(Integer.parseInt(settingCategory.getValue()));
        mRecyclerView.setLayoutManager(layoutManager);
        mListJobFilter = new ArrayList<Job>();

        spinnerDepartment = (AppCompatSpinner) findViewById(R.id.spinner_a);
        spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinner_b);

        assignFilters();

        if (savedInstanceState != null) {
            mListJob = savedInstanceState.getParcelableArrayList(STATE_JOB);
            mListJobComplete = savedInstanceState.getParcelableArrayList(STATE_JOB_COMPLETE);
        } else {
            mListJobComplete = dbAdapter.getJobList();
            if (categoryActive.getId() == 1) {
                mListJob = dbAdapter.getJobList();
            } else {
                //mListJob = dbAdapter.getJobList(categoryActive.getId());
                if (categoryActive.getId() > 0) {
                        mToolbar.setSubtitle(getString(R.string.menu_job) + " - " + categoryActive.getName());

                } else {
                    mToolbar.setSubtitle(getString(R.string.menu_job));
                }
            }

            if (mListJobComplete.isEmpty()) {
                mToolbar.setSubtitle(getString(R.string.menu_job));
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.please_wait))
                        .setDetailsLabel(getString(R.string.first_load))
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
                hud.show();
                //Log.d("some3", "calling general task");
                //new TaskLoadGeneral(this).execute();
            }

        }
        //update your Adapter to contain the retrieved destinations

        mAdapter.setData(mListJob);
        mSearchViewActive = false;
    }

    public void assignFilters() {

        mListDepartment = dbAdapter.getDepartmentStringList();
        adapterDepartment = new ArrayAdapter(this, R.layout.custom_spinner_item, mListDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterDepartment);

        mListCategory =dbAdapter.getJobCategoryStringList();
        adapterTheme = new ArrayAdapter(this, R.layout.custom_spinner_item, mListCategory);
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterTheme);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                pos = pos + 1;
                dbAdapter.updateSetting("FILTERBYDEPARTMENT", pos + "");
                mListJob.clear();
                Job job;
                boolean toAddString = false;
                boolean toAddTheme = false;
                boolean toAddDepartment = false;
                settingCategory = dbAdapter.getSetting("FILTERBYCATEGORY");
                settingDepartment = dbAdapter.getSetting("FILTERBYDEPARTMENT");
                int idCategory = Integer.parseInt(settingCategory.getValue());

                for (int x = 0; x < mListJobComplete.size(); x++) {
                    job = mListJobComplete.get(x);

                    //Theme
                    if (idCategory == 0 || idCategory == 1) {
                        toAddTheme = true;
                    } else {
                        if (job.getIdCategory() == idCategory) {
                            toAddTheme = true;
                        } else {
                            toAddTheme = false;
                        }
                    }

                    //Department
                    if (pos == 0 || pos == 1) {
                        toAddDepartment = true;
                    } else {
                        Department departmentTemp = dbAdapter.getDepartmentByOrder(pos);
                        if (job.getIdDepartment() == departmentTemp.getId()) {
                            toAddDepartment = true;
                        } else {
                            toAddDepartment = false;
                        }
                    }

                    //Search text
                    if (mSearchViewActive == true) {
                        if (job.getName().toLowerCase().contains(mSearchViewString)) {
                            toAddString = true;
                        } else {
                            toAddString = false;
                        }
                        if (toAddString == true && toAddTheme == true && toAddDepartment == true) {
                            mListJob.add(job);
                        }
                    } else {
                        if (toAddTheme == true && toAddDepartment == true) {
                            mListJob.add(job);
                        }
                    }
                }

                mAdapter.setData(mListJob);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                pos = pos + 1;
                Log.d("TOWATCHMAIN", pos + "");
                dbAdapter.updateSetting("FILTERBYTHEME", pos + "");
                mListJob.clear();
                Job job;
                boolean toAddString = false;
                boolean toAddCategory = false;
                boolean toAddDepartment = false;
                settingCategory = dbAdapter.getSetting("FILTERBYCATEGORY");
                settingDepartment = dbAdapter.getSetting("FILTERBYDEPARTMENT");
                int orderDepartment = Integer.parseInt(settingDepartment.getValue());

                for (int x = 0; x < mListJobComplete.size(); x++) {
                    job = mListJobComplete.get(x);

                    //Department
                    if (orderDepartment == 0 || orderDepartment == 1) {
                        toAddDepartment = true;
                    } else {
                        Department departmentTemp = dbAdapter.getDepartmentByOrder(orderDepartment);
                        if (job.getIdDepartment() == departmentTemp.getId()) {
                            toAddDepartment = true;
                        } else {
                            toAddDepartment = false;
                        }
                    }

                    //Category
                    if (pos == 0 || pos == 1) {
                        toAddCategory = true;
                    } else {
                        if (job.getIdCategory() == pos) {
                            toAddCategory = true;
                        } else {
                            toAddCategory = false;
                        }
                    }

                    //Search text
                    if (mSearchViewActive == true) {
                        if (job.getName().toLowerCase().contains(mSearchViewString)) {
                            toAddString = true;
                        } else {
                            toAddString = false;
                        }
                        if (toAddString == true && toAddCategory == true && toAddDepartment == true) {
                            mListJob.add(job);
                        }
                    } else {
                        if (toAddCategory == true && toAddDepartment == true) {
                            mListJob.add(job);
                        }
                    }
                }

                mAdapter.setData(mListJob);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Department department = dbAdapter.getDepartmentByOrder(Integer.parseInt(settingDepartment.getValue()));
        spinnerDepartment.setSelection(adapterDepartment.getPosition(department.getName()));

        JobCategory category = dbAdapter.getJobCategory(Integer.parseInt(settingCategory.getValue()));
        spinnerCategory.setSelection(adapterTheme.getPosition(category.getName()));
    }

    @Override
    public void onResume(){
        settingCategory = dbAdapter.getSetting("FILTERBYCATEGORY");
        settingDepartment = dbAdapter.getSetting("FILTERBYDEPARTMENT");
        spinnerDepartment = (AppCompatSpinner) findViewById(R.id.spinner_a);
        spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinner_b);
        assignFilters();
        super.onResume();
    }

    protected int columnsQuantity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int spanCount = Math.max(1, metrics.widthPixels / 700);
        if (spanCount == 1)
            spanCount = 2;
        return spanCount;
    }

    @Override
    public void onRefresh() {
        //new TaskLoadGeneral(this).execute();
    }

    @Override
    public void itemClicked(View view, int position) {
        Job job;
        if (mSearchViewActive == true) {
            job = mListJobFilter.get(position);
        } else {
            job = mListJob.get(position);
        }

        Intent destinationDetail = new Intent(this, JobDetailActivity.class);
        destinationDetail.putExtra("ID_JOB", job.getId());
        startActivity(destinationDetail);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        /*
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            mTextError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            mTextError.setText(R.string.error_network);
            //TODO
        } else if (error instanceof ParseError) {
            mTextError.setText(R.string.error_parser);
            //TODO
        }
        */
    }
}

