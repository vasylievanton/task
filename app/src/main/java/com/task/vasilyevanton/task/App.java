package com.task.vasilyevanton.task;

import android.app.Application;

public class App extends Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
