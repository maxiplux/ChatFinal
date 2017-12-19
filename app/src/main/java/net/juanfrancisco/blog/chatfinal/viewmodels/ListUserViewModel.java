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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.juanfrancisco.blog.chatfinal.db.ChatRoomRepository;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;

import java.util.List;

import timber.log.Timber;


public class ListUserViewModel extends ViewModel {

    private MutableLiveData<List<ChatRoom>> mListMutableLiveData;

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LiveData<List<ChatRoom>> getChatRooms() {
        if (mListMutableLiveData == null) {
            mListMutableLiveData = new MutableLiveData<>();
            mListMutableLiveData.setValue(ChatRoomRepository.getAll());

            loadRoomsAsync();
        }
        return mListMutableLiveData;
    }

    /**
     * Helper method to simulate loading of data from a server. We will replace this later.
     */
    private void loadRoomsAsync()


    {


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("rooms");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Log.w("usuarioactual", mAuth.getCurrentUser().getEmail());
        //.equalTo(mAuth.getCurrentUser().getEmail())


        Query queryRef = mDatabase.orderByChild("idReceiver").equalTo(mAuth.getCurrentUser().getUid());
        queryRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        int total_room = 0;


                        Log.w("info rooms", "dato " + String.valueOf(dataSnapshot.getChildrenCount()));

                        for (DataSnapshot chatMessageSnapshot : dataSnapshot.getChildren()) {
                            ChatRoom chat_room = chatMessageSnapshot.getValue(ChatRoom.class);


                            if (ChatRoomRepository.insert(chat_room) != 0) {
                                total_room = total_room + 1;
                            }
                        }
                        if (total_room != 0) {
                            count = total_room;
                            mListMutableLiveData.setValue(ChatRoomRepository.getAll());

                        }

                        Log.w("nuevos rooms", "Total de nuevos rooms " + String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // read query is cancelled.
                    }
                });


        queryRef = mDatabase.orderByChild("idSender").equalTo(mAuth.getCurrentUser().getUid());
        queryRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        int total_room = 0;


                        Log.w("info rooms", "dato " + String.valueOf(dataSnapshot.getChildrenCount()));

                        for (DataSnapshot chatMessageSnapshot : dataSnapshot.getChildren()) {
                            ChatRoom chat_room = chatMessageSnapshot.getValue(ChatRoom.class);


                            if (ChatRoomRepository.insert(chat_room) != 0) {
                                total_room = total_room + 1;
                            }
                        }
                        if (total_room != 0) {
                            count = total_room;
                            mListMutableLiveData.setValue(ChatRoomRepository.getAll());

                        }

                        Log.w("nuevos rooms", "Total de nuevos rooms " + String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // read query is cancelled.
                    }
                });


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }


}
