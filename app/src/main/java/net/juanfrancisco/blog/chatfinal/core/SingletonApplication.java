package net.juanfrancisco.blog.chatfinal.core;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.juanfrancisco.blog.chatfinal.CoreDb.AppDatabase;


/**
 * Created by krlosf on 5/12/17.
 */

public class SingletonApplication extends Application {


    private static final String DATABASE_NAME = "AppDatabase";
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";
    public static SingletonApplication INSTANCE;
    private AppDatabase database;

    private static FirebaseAuth auth;
    private static DatabaseReference mDatabase;

    public static SingletonApplication get() {
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


    public static DatabaseReference getFirbaseDatabaseReference() {

        if (mDatabase==null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();

        }

        return mDatabase;
    }


    public static FirebaseAuth getFirbaseUserReference() {

        if (auth==null)
        {
            auth = FirebaseAuth.getInstance();

        }

       return auth;
    }
}
