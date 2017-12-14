package net.juanfrancisco.blog.chatfinal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.juanfrancisco.blog.chatfinal.core.ChatMessage;
import net.juanfrancisco.blog.chatfinal.core.ChatMessageRepository;
import net.juanfrancisco.blog.chatfinal.core.ChatRoom;
import net.juanfrancisco.blog.chatfinal.core.ChatRoomRepository;
import net.juanfrancisco.blog.chatfinal.core.MessageAdapter;
import net.juanfrancisco.blog.chatfinal.users.ListUserDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Fragment {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean isMine = true;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;
    public FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private ChatRoom chat_room;



    public static ChatActivity getInstance(FirebaseUser currentUser,String idReceiver,String friend_email ) {

        return new ChatActivity( currentUser,idReceiver,friend_email);
    }


    public ChatActivity ( ) {

    }

    public ChatActivity (FirebaseUser currentUser,String idReceiver,String friend_email) {
        this.currentUser=currentUser;




        this.chat_room=new ChatRoom(currentUser.getUid(), idReceiver,friend_email);

        chat_room.getFirebaseid();//currentUser.getUid()+this.idReceiver;



        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_chat, container, false);

        chatMessages = new ArrayList<>();




        listView = (ListView) view.findViewById(R.id.list_msg);
        btnSend = view.findViewById(R.id.btn_chat_send);
        editText = (EditText) view.findViewById(R.id.msg_type);
        adapter = new MessageAdapter((Activity) view.getContext(), R.layout.item_chat_left, chatMessages);
        listView.setAdapter(adapter);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run()
            {


                //set ListView adapter first



                chatMessages.addAll((ArrayList<ChatMessage>) ChatMessageRepository.getAll(chat_room.getFirebaseid() ));


                //adapter.notifyDataSetChanged();


            }
        });





        //event for button SEND


        // Read from the database
        // mDatabase.child("messages").child(currentUser.getUid()).child(idReceiver).push().setValue(chatMessage); .orderByChild("timestamp")


/*        mDatabase.child("messages").child(idChat).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, ChatMessage> td = new HashMap<String, ChatMessage>();

                for (DataSnapshot chatMessageSnapshot: dataSnapshot.getChildren()) {
                    ChatMessage msg = chatMessageSnapshot.getValue(ChatMessage.class);
                    td.put(chatMessageSnapshot.getKey(), msg);
                }

                ArrayList<ChatMessage> values = new ArrayList<>(td.values());
                List<String> keys = new ArrayList<String>(td.keySet());

                for (String key: keys)
                {
                    Log.d("keys", key);



                }


                for (ChatMessage chatMessage: values)
                {
                    Log.d("contenidos", chatMessage.toString());
//                    if ( chatMessage.getIdReceiver()==idReceiver )
//                    {
//                        chatMessage.setMine(true);
//                    }
//                    else
//                    {
//                        chatMessage.setMine(false);
//                    }

   if ( chatMessage.getIdReceiver()!=idReceiver )
                    {
                        chatMessages.add(chatMessage);
                        adapter.notifyDataSetChanged();
                     }




                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ECHATFIRE", "Failed to read value.", error.toException());
            }
        });*/

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {


                        if (editText.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(view.getContext(), "Please input some text...", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {


                            ChatMessage chatMessage = new ChatMessage(editText.getText().toString(), chat_room.getIdSender().equals(chat_room.getIdSender()),chat_room.getIdSender(),chat_room.getIdReceiver());







                            mDatabase.child("rooms").child(chat_room.getFirebaseid()).setValue(chat_room);


                            mDatabase.child("messages").child(chat_room.getFirebaseid()).push().setValue(chatMessage);





                            chatMessages.add(chatMessage);
                            ChatMessageRepository.insert(chatMessage);
                            adapter.notifyDataSetChanged();
                            editText.setText("");


                        }

                    }
                });


            }
        });


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }






}
