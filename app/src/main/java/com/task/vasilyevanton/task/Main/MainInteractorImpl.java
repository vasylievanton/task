package com.task.vasilyevanton.task.Main;


import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.task.vasilyevanton.task.DataBaseHandler;

public class MainInteractorImpl implements MainInteractor {


    //DataBase
    @Override
    public void getDatabaseData(OnDBObtainListener listener, Activity activity, String department) {
        DataBaseHandler db = new DataBaseHandler(activity);

        if(department!=null){
            listener.onSuccessDatabase(db.getPeoplesDepartment(department));
        }else {
            listener.onSuccessDatabase(db.getPeoples());
        }

    }


    @Override
    public void initializeDB(final OnDBInitListener listener) {
        DbInitializerTask task = new DbInitializerTask();
        task.execute((Void) null);
        task.setOnDBObtainListener(new DbInitializerTask.OnGetDBListener() {
            @Override
            public void onResponse(boolean result) {
                if(result){
                    listener.onDBInitSuccess();
                }else {
                    listener.onDBInitError();
                }
            }
        });
    }

    private static class DbInitializerTask extends AsyncTask<Void, Void, Boolean> {

        private OnGetDBListener mListener;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                DataBaseHandler.Initialize();
            } catch (SQLiteException ex) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (mListener != null) {
                mListener.onResponse(result);
            }
            super.onPostExecute(result);
        }

        public void setOnDBObtainListener(OnGetDBListener listener) {
            mListener = listener;
        }

        public interface OnGetDBListener {
            void onResponse(boolean result);
        }
    }

}

