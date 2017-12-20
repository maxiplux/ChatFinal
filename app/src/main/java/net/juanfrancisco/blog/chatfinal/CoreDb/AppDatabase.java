package net.juanfrancisco.blog.chatfinal.CoreDb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import net.juanfrancisco.blog.chatfinal.db.daos.ChatMessageDao;
import net.juanfrancisco.blog.chatfinal.db.daos.ChatRoomDao;
import net.juanfrancisco.blog.chatfinal.models.ChatMessage;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;

/**
 * Created by juan on 12/12/17.
 */

@Database(entities = {ChatRoom.class, ChatMessage.class}, version = 11)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            //db.setForceUpdate(true);
        }
    };
    private static final String KEY_FORCE_UPDATE = "force_update";
    private static AppDatabase db;

    public static void init(Context context) {

        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "mychat.v1.0.14.sqlite").addMigrations(AppDatabase.MIGRATION_1_2).allowMainThreadQueries().build();

        }
    }

    public static AppDatabase getInstance() {

        return db;
    }

    public abstract ChatRoomDao ChatRoomDao();

    public abstract ChatMessageDao ChatMessageDao();


}





