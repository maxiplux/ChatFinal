package net.juanfrancisco.blog.chatfinal.core;

import java.util.List;

/**
 * Created by juan on 14/12/17.
 */

public class ChatMessageRepository {

    public ChatMessageRepository() {
    }

    public static Long insert(ChatMessage todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao chat_room = db.ChatMessageDao();
        return chat_room.insert(todo);
    }


    public static void update(ChatMessage todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        todoDao.update(todo);
    }


    public static void delete(ChatMessage todo) {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        todoDao.delete(todo);
    }


    public static List<ChatMessage> getAll(String firebaseId)

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.getAll(firebaseId);
    }

    public static List<ChatMessage> getAll()

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.getAll();
    }
}
