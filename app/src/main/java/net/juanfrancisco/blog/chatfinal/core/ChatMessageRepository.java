package net.juanfrancisco.blog.chatfinal.core;

import net.juanfrancisco.blog.chatfinal.CoreDb.AppDatabase;

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
        if ( ChatMessageRepository.findByFirebaseId(todo.getFirebaseId()) ==null  )
        {
            return chat_room.insert(todo);

        }
        return Long.valueOf(0);







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


    public static List<ChatMessage> getAll(String ChatRoomId)

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.getAll(ChatRoomId);
    }

    public static ChatMessage findByFirebaseId(String firebaseId)

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.findByFirebaseId(firebaseId);
    }

    public static ChatMessage findByChatRoomId(String firebaseId)

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.findByChatRoomId(firebaseId);
    }

    public static List<ChatMessage> getAll()

    {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        return todoDao.getAll();
    }
}
