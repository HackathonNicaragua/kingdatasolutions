package com.kingdatasolutions.sintraba.utility;

import com.android.volley.RequestQueue;
import com.kingdatasolutions.sintraba.SinTrabaApp;
import com.kingdatasolutions.sintraba.data.SinTrabaDBAdapter;
import com.kingdatasolutions.sintraba.datamodel.Department;
import com.kingdatasolutions.sintraba.datamodel.Information;
import com.kingdatasolutions.sintraba.datamodel.Job;
import com.kingdatasolutions.sintraba.datamodel.JobCategory;
import com.kingdatasolutions.sintraba.datamodel.Setting;
import com.kingdatasolutions.sintraba.json.EndPoints;
import com.kingdatasolutions.sintraba.json.Requestor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nestorbonilla on 11/25/17.
 */

public class InformationUtils {

    public static void loadGeneral(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestInformationJSON(requestQueue, EndPoints.getRequestUrlGeneral());
        parseJSONResponse(response);
    }

    public static void loadInformation(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestInformationJSON(requestQueue, EndPoints.getRequestUrlInformation());
        parseJSONResponse(response);
    }

    public static void parseJSONVersionResponse(JSONObject response) {
        SinTrabaDBAdapter mAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());
        Setting versionSetting = mAdapter.getSetting("APPVERSION");

        if (response == null || response.length() == 0) {
            return;
        }
        try {
            if (response.has("version") && !response.isNull("version")) {
                String onlineVersion = response.getString("version");
                if (versionSetting.getValue() == "0") {
                    mAdapter.updateSetting("APPVERSION", onlineVersion);
                } else {
                    Float fOnlineVersion = Float.parseFloat(onlineVersion);
                    //Float fLocalVersion = Float.parseFloat(getVersionInfo());
                    Float fLocalVersion = Float.parseFloat(versionSetting.getValue());
                    if (fLocalVersion < fOnlineVersion) {
                        mAdapter.updateSetting("APPVERSION", fOnlineVersion + "");
                        mAdapter.updateSetting("APPVERSIONUPDATE", "1");
                    }
                }
            }
        } catch (JSONException e) {

        }


    }

    public static void parseJSONResponse(JSONObject response) {

        ArrayList<JobCategory> themeList = new ArrayList<JobCategory>();
        ArrayList<Job> jobList = new ArrayList<Job>();
        ArrayList<Information> rankList = new ArrayList<Information>();
        ArrayList<Department> departmentList = new ArrayList<Department>();
        ArrayList<Information> informationList = new ArrayList<Information>();

        SinTrabaDBAdapter mAdapter = new SinTrabaDBAdapter(SinTrabaApp.getAppContext());

        if (response == null || response.length() == 0) {
            return;
        }
        /*
        try {
            if (response.has(Keys.KEY_RANK) && !response.isNull(Keys.KEY_RANK)) {
                JSONArray arrayRank = new JSONArray();
                try {
                    arrayRank = new JSONArray(response.getString(Keys.KEY_RANK));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayRank.length(); i++) {
                    Rank rank = new Rank();
                    JSONObject rankJson = arrayRank.getJSONObject(i);
                    rank.setId(rankJson.getInt(Keys.KEY_RANK_ID));
                    rank.setIdOrder(rankJson.getInt(Keys.KEY_RANK_ID_ORDER));
                    rank.setIdMainActivity(rankJson.getInt(Keys.KEY_RANK_ID_MAIN_ACTIVITY));
                    rank.setNameSpa(rankJson.getString(Keys.KEY_RANK_NAME_SPA));
                    rank.setNameEng(rankJson.getString(Keys.KEY_RANK_NAME_ENG));
                    rankList.add(rank);
                }
                mAdapter.fillRank(rankList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_THEME) && !response.isNull(Keys.KEY_THEME)) {
                JSONArray arrayTheme = new JSONArray();
                try {
                    arrayTheme = new JSONArray(response.getString(Keys.KEY_THEME));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayTheme.length(); i++) {
                    Theme theme = new Theme();
                    JSONObject themeJson = arrayTheme.getJSONObject(i);
                    theme.setId(themeJson.getInt(Keys.KEY_THEME_ID));
                    theme.setIdOrder(themeJson.getInt(Keys.KEY_THEME_ID_ORDER));
                    theme.setNameSpa(themeJson.getString(Keys.KEY_THEME_NAME_SPA));
                    theme.setNameEng(themeJson.getString(Keys.KEY_THEME_NAME_ENG));
                    theme.setImageAddress(themeJson.getString(Keys.KEY_THEME_IMAGE_ADDRESS));
                    themeList.add(theme);
                }
                mAdapter.fillTheme(themeList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_MAIN_ACTIVITY) && !response.isNull(Keys.KEY_MAIN_ACTIVITY)) {
                JSONArray arrayMainActivity = new JSONArray();
                try {
                    arrayMainActivity = new JSONArray(response.getString(Keys.KEY_MAIN_ACTIVITY));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayMainActivity.length(); i++) {
                    MainActivity mainActivity = new MainActivity();
                    JSONObject mainActivityJson = arrayMainActivity.getJSONObject(i);
                    mainActivity.setId(mainActivityJson.getInt(Keys.KEY_MAIN_ACTIVITY_ID));
                    mainActivity.setIdOrder(mainActivityJson.getInt(Keys.KEY_MAIN_ACTIVITY_ID_ORDER));
                    mainActivity.setNameSpa(mainActivityJson.getString(Keys.KEY_MAIN_ACTIVITY_NAME_SPA));
                    mainActivity.setNameEng(mainActivityJson.getString(Keys.KEY_MAIN_ACTIVITY_NAME_ENG));
                    mainActivity.setImageAddress(mainActivityJson.getString(Keys.KEY_MAIN_ACTIVITY_IMAGE_ADDRESS));
                    mainActivityList.add(mainActivity);
                }
                mAdapter.fillMainActivity(mainActivityList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_ACTIVITY) && !response.isNull(Keys.KEY_ACTIVITY)) {
                JSONArray arrayActivity = new JSONArray();
                try {
                    arrayActivity = new JSONArray(response.getString(Keys.KEY_ACTIVITY));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayActivity.length(); i++) {
                    Activity activity = new Activity();
                    JSONObject mainActivityJson = arrayActivity.getJSONObject(i);
                    activity.setId(mainActivityJson.getInt(Keys.KEY_ACTIVITY_ID));
                    activity.setIdCategory(mainActivityJson.getInt(Keys.KEY_ACTIVITY_ID_CATEGORY));
                    activity.setIdMainActivity(mainActivityJson.getInt(Keys.KEY_ACTIVITY_ID_MAIN_ACTIVITY));
                    activity.setIdOrder(mainActivityJson.getInt(Keys.KEY_ACTIVITY_ID_ORDER));
                    activity.setNameSpa(mainActivityJson.getString(Keys.KEY_ACTIVITY_NAME_SPA));
                    activity.setNameEng(mainActivityJson.getString(Keys.KEY_ACTIVITY_NAME_ENG));
                    activityList.add(activity);
                }
                mAdapter.fillActivity(activityList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_DEPARTMENT) && !response.isNull(Keys.KEY_DEPARTMENT)) {
                JSONArray arrayDepartment = new JSONArray();
                try {
                    arrayDepartment = new JSONArray(response.getString(Keys.KEY_DEPARTMENT));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayDepartment.length(); i++) {
                    Department department = new Department();
                    JSONObject departmentJson = arrayDepartment.getJSONObject(i);
                    department.setId(departmentJson.getInt(Keys.KEY_DEPARTMENT_ID));
                    department.setIdOrder(departmentJson.getInt(Keys.KEY_DEPARTMENT_ID_ORDER));
                    department.setNameSpa(departmentJson.getString(Keys.KEY_DEPARTMENT_NAME_SPA));
                    department.setNameEng(departmentJson.getString(Keys.KEY_DEPARTMENT_NAME_ENG));
                    department.setImageAddress(departmentJson.getString(Keys.KEY_DEPARTMENT_IMAGE_ADDRESS));
                    departmentList.add(department);
                }
                mAdapter.fillDepartment(departmentList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_DESTINATION) && !response.isNull(Keys.KEY_DESTINATION)) {
                JSONArray arrayDestination = new JSONArray();
                try {
                    arrayDestination = new JSONArray(response.getString(Keys.KEY_DESTINATION));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayDestination.length(); i++) {
                    Destination destination = new Destination();
                    JSONObject destinationJson = arrayDestination.getJSONObject(i);
                    destination.setId(destinationJson.getInt(Keys.KEY_DESTINATION_ID));
                    destination.setIdOrder(destinationJson.getInt(Keys.KEY_DESTINATION_ID_ORDER));
                    destination.setIdTheme(destinationJson.getInt(Keys.KEY_DESTINATION_ID_THEME));
                    destination.setIdDepartment(destinationJson.getInt(Keys.KEY_DESTINATION_ID_DEPARTMENT));
                    destination.setNameSpa(destinationJson.getString(Keys.KEY_DESTINATION_NAME_SPA));
                    destination.setNameEng(destinationJson.getString(Keys.KEY_DESTINATION_NAME_ENG));
                    destinationList.add(destination);
                }
                mAdapter.fillDestination(destinationList);
            }
        } catch (JSONException e) {
        }
        try {
            if (response.has(Keys.KEY_DIRECTORY) && !response.isNull(Keys.KEY_DIRECTORY)) {
                JSONArray arrayDirectory = new JSONArray();
                try {
                    arrayDirectory = new JSONArray(response.getString(Keys.KEY_DIRECTORY));
                } catch (Exception ex) {

                }
                for (int i = 0; i < arrayDirectory.length(); i++) {
                    Directory directory = new Directory();
                    JSONObject directoryJson = arrayDirectory.getJSONObject(i);
                    directory.setId(directoryJson.getInt(Keys.KEY_DIRECTORY_ID));
                    directory.setIdOrder(directoryJson.getInt(Keys.KEY_DIRECTORY_ID_ORDER));
                    directory.setIdActivity(directoryJson.getInt(Keys.KEY_DIRECTORY_ID_ACTIVITY));
                    directory.setValueCategory(directoryJson.getInt(Keys.KEY_DIRECTORY_VALUE_CATEGORY));
                    directory.setIdDepartment(directoryJson.getInt(Keys.KEY_DIRECTORY_ID_DEPARTMENT));
                    directory.setMunicipality(directoryJson.getString(Keys.KEY_DIRECTORY_MUNICIPALITY));
                    directory.setName(directoryJson.getString(Keys.KEY_DIRECTORY_NAME));
                    directoryList.add(directory);
                }
                mAdapter.fillDirectory(directoryList);
            }
        } catch (JSONException e) {
        }
        */
    }

}
