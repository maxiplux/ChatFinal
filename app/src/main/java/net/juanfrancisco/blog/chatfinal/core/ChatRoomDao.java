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
public interface ChatRoomDao
{

    @Query("SELECT * FROM chatroom ")
    List<ChatRoom> getAll() ;



    @Insert
    void insertAll(List<ChatRoom> msg);

    @Update
    void update(ChatRoom room);

    @Delete
    void delete(ChatRoom room);


    @Query("SELECT * FROM chatroom WHERE uid = :uid LIMIT 1")
    ChatRoom findByUid(String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ChatRoom todo);


}