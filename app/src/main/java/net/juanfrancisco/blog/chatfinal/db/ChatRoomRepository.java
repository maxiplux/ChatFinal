package net.juanfrancisco.blog.chatfinal.db;

import net.juanfrancisco.blog.chatfinal.CoreDb.AppDatabase;
import net.juanfrancisco.blog.chatfinal.db.daos.ChatRoomDao;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;

import java.util.Collections;
import java.util.List;

/**
 * Created by juan on 13/12/17.
 */

public class ChatRoomRepository {


    public ChatRoomRepository() {
    }

    public static Long insert(ChatRoom todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatRoomDao chat_room = db.ChatRoomDao();
        if (ChatRoomRepository.findByFirebaseId(todo.getFirebaseid()) == null) {
            return chat_room.insert(todo);

        }
        return Long.valueOf(0);

    }


    public static ChatRoom findByFirebaseId(String findByUid) {
        AppDatabase db = AppDatabase.getInstance();
        ChatRoomDao todoDao = db.ChatRoomDao();
        return todoDao.findByFirebaseId(findByUid);
    }

    public static void update(ChatRoom todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatRoomDao todoDao = db.ChatRoomDao();
        todoDao.update(todo);
    }


    public static void delete(ChatRoom todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatRoomDao todoDao = db.ChatRoomDao();
        todoDao.delete(todo);
    }


    public static List<ChatRoom> getAll()

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatRoomDao todoDao = db.ChatRoomDao();
        List<ChatRoom> result = todoDao.getAll();


        return (null != result ? result : Collections.EMPTY_LIST);

    }

}
