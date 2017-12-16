package net.juanfrancisco.blog.chatfinal;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;

import net.juanfrancisco.blog.chatfinal.core.AppDatabase;


/**
 * Created by krlosf on 5/12/17.
 */

public class MyApplication extends Application {


    public static MyApplication INSTANCE;
    private static final String DATABASE_NAME = "AppDatabase";
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";

    private AppDatabase database;

    public static MyApplication get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(getApplicationContext());

        // create database
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build();

        INSTANCE = this;
    }



    public boolean isForceUpdate() {
        return getSP().getBoolean(KEY_FORCE_UPDATE, true);
    }

    public void setForceUpdate(boolean force) {
        SharedPreferences.Editor edit = getSP().edit();
        edit.putBoolean(KEY_FORCE_UPDATE, force);
        edit.apply();
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }
}
