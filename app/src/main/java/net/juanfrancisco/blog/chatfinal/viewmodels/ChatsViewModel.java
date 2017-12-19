package net.juanfrancisco.blog.chatfinal.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.juanfrancisco.blog.chatfinal.core.SingletonApplication;
import net.juanfrancisco.blog.chatfinal.db.ChatMessageRepository;
import net.juanfrancisco.blog.chatfinal.models.ChatMessage;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;

import java.util.List;

import timber.log.Timber;

/**
 * Created by juan on 15/12/17.
 */

public class ChatsViewModel extends ViewModel {

    private MutableLiveData<List<ChatMessage>> mListMutableLiveData;

    private ChatRoom current_chat_room;
    private int new_messages = 0;
    private DatabaseReference mDatabase;


    public LiveData<List<ChatMessage>> getChatMessages(ChatRoom current_chat_room) {


        if (this.mDatabase == null) {
            mDatabase = SingletonApplication.getFirbaseDatabaseReference();
        }


        if (this.current_chat_room == null) {
            this.current_chat_room = current_chat_room;
        }


        if (mListMutableLiveData == null) {
            mListMutableLiveData = new MutableLiveData<>();
            refreshRoom();
            loadRoomsAsync();
        }

//        Log.w("num_messages", String.valueOf(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()).size()));
        return mListMutableLiveData;
    }

    public LiveData<List<ChatMessage>> getChatMessages(ChatRoom current_chat_room, ChatMessage chatMessage) {


        //Log.w("num_messages", String.valueOf(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()).size()));
        //loadRoomsAsync();
        return mListMutableLiveData;
    }

    private void loadRoomsAsync()

    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(current_chat_room.getFirebaseid());

        FirebaseAuth mAuth = SingletonApplication.getFirbaseUserReference();

        Log.w("usuarioactualasync", mAuth.getCurrentUser().getEmail());

        Log.w("chatFirebaseId", current_chat_room.getFirebaseid());


        // Attach a listener to read the data at our posts reference
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new_messages = 0;
                for (DataSnapshot chatMessageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = chatMessageSnapshot.getValue(ChatMessage.class);

                    if (chatMessage.getIdSender().equals(mAuth.getCurrentUser().getUid()))
                    {
                        chatMessage.setMine(true);

                    }

                    else
                    {
                        chatMessage.setMine(false);
                    }

                    ChatMessageRepository.insert(chatMessage);
                    new_messages = 1;


                }

                Log.w("msg-count", String.valueOf(new_messages));

                if (new_messages == 1) {
                    refreshRoom();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void refreshRoom() {
        mListMutableLiveData.setValue(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

    public void send_menssage(ChatMessage chatMessage) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (this.mDatabase == null) {
            mDatabase = SingletonApplication.getFirbaseDatabaseReference();
        }

        mDatabase =SingletonApplication.getFirbaseDatabaseReference();

        //chatMessage.setMine(chatMessage.getIdReceiver() == mAuth.getCurrentUser().getUid());
        //ChatMessageRepository.insert(chatMessage) ;

        mDatabase.child("messages").child(current_chat_room.getFirebaseid()).push().setValue(chatMessage);
        //mListMutableLiveData.setValue(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()));

    }
}
