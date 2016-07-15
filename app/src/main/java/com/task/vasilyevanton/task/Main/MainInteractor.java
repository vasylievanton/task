package com.task.vasilyevanton.task.Main;


import android.app.Activity;

import com.task.vasilyevanton.task.pojos.PeopleData;

import java.util.ArrayList;

public interface MainInteractor {


    void getDatabaseData(OnDBObtainListener listener, Activity activity, String department);
    void initializeDB(OnDBInitListener listener);
    interface OnDBInitListener {
        void onDBInitSuccess();
        void onDBInitError();
    }


    interface OnDBObtainListener {
        void onSuccessDatabase(ArrayList<PeopleData> data);
    }
}
