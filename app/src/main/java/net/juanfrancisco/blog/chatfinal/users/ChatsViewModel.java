package net.juanfrancisco.blog.chatfinal.users;

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

import net.juanfrancisco.blog.chatfinal.core.ChatMessage;
import net.juanfrancisco.blog.chatfinal.core.ChatMessageRepository;
import net.juanfrancisco.blog.chatfinal.core.ChatRoom;

import java.util.List;

import timber.log.Timber;

/**
 * Created by juan on 15/12/17.
 */

public class ChatsViewModel extends ViewModel {

    private MutableLiveData<List<ChatMessage>> mListMutableLiveData;

    private ChatRoom current_chat_room;
    private  int new_messages =0;
    private DatabaseReference mDatabase ;




    public LiveData<List<ChatMessage>> getChatMessages(ChatRoom current_chat_room)
    {


        if (this.mDatabase==null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        if (this.current_chat_room==null)
        {
            this.current_chat_room=current_chat_room;
        }

        if (this.current_chat_room==null)
        {
            this.current_chat_room=current_chat_room;
        }


        if (mListMutableLiveData == null) {
            mListMutableLiveData = new MutableLiveData<>();
            mListMutableLiveData.setValue(ChatMessageRepository.getAll());


        }
        loadRoomsAsync();
        Log.w("num_messages", String.valueOf(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()).size()));
        return mListMutableLiveData;
    }

    public LiveData<List<ChatMessage>> getChatMessages(ChatRoom current_chat_room,ChatMessage chatMessage)
    {

        if (this.mDatabase==null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }


        this.current_chat_room=current_chat_room;




        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messages").child(current_chat_room.getFirebaseid()).push().setValue(chatMessage);
        mListMutableLiveData.setValue(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()));



        //Log.w("num_messages", String.valueOf(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()).size()));
        return mListMutableLiveData;
    }

    private void loadRoomsAsync()

    {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(current_chat_room.getFirebaseid());

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Log.w("usuarioactualasync", mAuth.getCurrentUser().getEmail());

        Log.w("chatFirebaseId", current_chat_room.getFirebaseid());


        // Attach a listener to read the data at our posts reference
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot chatMessageSnapshot: dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = chatMessageSnapshot.getValue(ChatMessage.class);
                    //Log.w("lotengo", chatMessage.toString());
                    chatMessage.setMine(false);

                    if (chatMessage.getIdSender() == mAuth.getCurrentUser().getUid())
                    {
                        chatMessage.setMine(true);


                    }


                    if ( ChatMessageRepository.insert(chatMessage) !=0)
                    {
                        new_messages = new_messages +1;

                    }


                }
                Log.w("msg-count", String.valueOf(new_messages));


                if (new_messages !=0)
                {
                    mListMutableLiveData.setValue(ChatMessageRepository.getAll(current_chat_room.getFirebaseid()));
                    new_messages =0;
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
