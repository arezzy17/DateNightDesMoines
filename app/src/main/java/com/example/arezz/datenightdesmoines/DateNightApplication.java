package com.example.arezz.datenightdesmoines;

import android.app.Application;

import io.realm.Realm;

public class DateNightApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
