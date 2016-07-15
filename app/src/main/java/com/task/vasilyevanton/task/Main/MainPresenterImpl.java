package com.task.vasilyevanton.task.Main;


import android.app.Activity;
import android.util.Log;

import com.task.vasilyevanton.task.pojos.PeopleData;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter, MainInteractor.OnDBObtainListener, MainInteractor.OnDBInitListener {

    private MainView mainView;
    private MainInteractor mainInteractor;
    private Activity mActivity;

    public MainPresenterImpl(MainView mainView, Activity activity) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl();
        this.mActivity = activity;
    }

    //from VIEW
    @Override
    public void getPeopleInfo() {
        if (mainView != null) {
            Log.w("getPeopleInfo", "Get db data");
            mainInteractor.getDatabaseData(this, mActivity, null);
        }

    }

    @Override
    public void getDepartmentInfo(String department) {
        mainInteractor.getDatabaseData(this, mActivity, department);

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onResume() {
        mainInteractor.getDatabaseData(this,mActivity,null);
    }


    @Override
    public void initializeDB() {
        mainInteractor.initializeDB(this);
    }


    //to VIEW
    @Override
    public void onSuccessDatabase(ArrayList<PeopleData> data) {
        if (mainView != null) {
            Log.w("Load db", "data");
            mainView.setPeopleInfoData(data);
        }
    }

    @Override
    public void onDBInitSuccess() {
        if (mainView != null) {
            mainInteractor.getDatabaseData(this, mActivity, null);
        }
    }

    @Override
    public void onDBInitError() {
        //error obtain DB
        Log.w("Error", "Error");
    }
}
