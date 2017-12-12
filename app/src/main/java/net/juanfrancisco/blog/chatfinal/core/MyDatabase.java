package net.juanfrancisco.blog.chatfinal.core;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by juan on 12/12/17.
 */

@Database(entities = {ChatMessage.class}, version = 2)
@TypeConverters({DateTypeConverter.class})
public abstract class MyDatabase extends RoomDatabase {

    public abstract ChatMessageDao productDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {
           // database.execSQL("ALTER TABLE product " + " ADD COLUMN price INTEGER");

            // enable flag to force update products
            App.get().setForceUpdate(true);
        }
    };

}