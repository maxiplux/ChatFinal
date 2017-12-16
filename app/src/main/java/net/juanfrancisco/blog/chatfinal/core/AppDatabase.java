package net.juanfrancisco.blog.chatfinal.core;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by juan on 12/12/17.
 */

@Database(entities = {ChatRoom.class,ChatMessage.class}, version = 6)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;

    public abstract ChatRoomDao ChatRoomDao();

    public abstract ChatMessageDao ChatMessageDao();
    private static final String KEY_FORCE_UPDATE = "force_update";

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database)
        {

            //db.setForceUpdate(true);
        }
    };


    public static void init(Context context) {
        if(db == null)
        {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "todo-app18maxipl") .addMigrations(AppDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
            /*db = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                    AppDatabase.class).build();*/
        }
    }

    public static AppDatabase getInstance()
    {

        return db;
    }



}





