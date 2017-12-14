package net.juanfrancisco.blog.chatfinal.core;

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
        return chat_room.insert(todo);
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
        return todoDao.getAll();
    }

}
