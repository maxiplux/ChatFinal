package net.juanfrancisco.blog.chatfinal.db;

import net.juanfrancisco.blog.chatfinal.CoreDb.AppDatabase;
import net.juanfrancisco.blog.chatfinal.db.daos.ChatMessageDao;
import net.juanfrancisco.blog.chatfinal.models.ChatMessage;

import java.util.List;

/**
 * Created by juan on 14/12/17.
 */

public class ChatMessageRepository {

    public ChatMessageRepository() {

    }

    public static boolean insert(ChatMessage msg) {
        AppDatabase db = AppDatabase.getInstance();


        return db.ChatMessageDao().insert(msg) != 0;


    }


    public static void update(ChatMessage msg) {
        AppDatabase db = AppDatabase.getInstance();
        ChatMessageDao todoDao = db.ChatMessageDao();
        todoDao.update(msg);
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
