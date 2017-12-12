package net.juanfrancisco.blog.chatfinal.core;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by juan on 12/12/17.
 */

@Dao
public interface ChatMessageDao {

    @Query("SELECT * FROM chatmessage ")
    List<ChatMessage> getAll() ;

    @Query("SELECT * FROM chatmessage WHERE uid = :uid LIMIT 1")
    ChatMessage findByUid(String uid);

    @Insert
    void insertAll(List<ChatMessage> msg);

    @Update
    void update(ChatMessage msg);

    @Delete
    void delete(ChatMessage msg);

}
