package net.juanfrancisco.blog.chatfinal;

import android.app.Application;

import net.juanfrancisco.blog.chatfinal.core.AppDatabase;


/**
 * Created by krlosf on 5/12/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppDatabase.init(getApplicationContext());
    }
}
