package com.example.arezz.datenightdesmoines;

import io.realm.RealmModel;

public class RealmString implements RealmModel {
    private String realmString;

    public String getRealmString() {
        return realmString;
    }

    public void setRealmString(String realmString) {
        this.realmString = realmString;
    }
}
