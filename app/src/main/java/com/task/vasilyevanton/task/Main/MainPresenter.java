package com.task.vasilyevanton.task.Main;


public interface MainPresenter {
    void getPeopleInfo();
    void getDepartmentInfo(String department);
    void onDestroy();
    void onResume();
    void initializeDB();

}
