package net.juanfrancisco.blog.chatfinal.core;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by juan on 12/12/17.
 */

@Dao
public interface ChatMessageDao {

    @Query("SELECT * FROM chatmessage  ORDER BY date(timestamp) DESC   ")
    List<ChatMessage> getAll() ;

    @Query("SELECT * FROM chatmessage  WHERE firebaseId = :firebaseId ORDER BY date(timestamp) DESC   ")
    List<ChatMessage> getAll(String firebaseId) ;


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ChatMessage msg);


    @Query("SELECT * FROM chatmessage WHERE uid = :uid ORDER BY date(timestamp) DESC  LIMIT 1 ")
    ChatMessage findByUid(String uid);

    @Insert
    void insertAll(List<ChatMessage> msg);

    @Update
    void update(ChatMessage msg);

    @Delete
    void delete(ChatMessage msg);

}
