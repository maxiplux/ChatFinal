package net.juanfrancisco.blog.chatfinal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import net.juanfrancisco.blog.chatfinal.core.ChatMessage;
import net.juanfrancisco.blog.chatfinal.core.MessageAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends Fragment {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean isMine = true;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;
    public FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    public String  idReceiver;
    public String  idChat;


    public static ChatActivity getInstance(FirebaseUser currentUser,String idReceiver ) {

        return new ChatActivity( currentUser,idReceiver);
    }


    public ChatActivity ( ) {

    }

    public ChatActivity (FirebaseUser currentUser,String idReceiver) {
        this.currentUser=currentUser;
        this.idReceiver=idReceiver;

        this.idChat="454";//currentUser.getUid()+this.idReceiver;



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


        //set ListView adapter first
        adapter = new MessageAdapter((Activity) view.getContext(), R.layout.item_chat_left, chatMessages);
        listView.setAdapter(adapter);

        //event for button SEND


        // Read from the database
        // mDatabase.child("messages").child(currentUser.getUid()).child(idReceiver).push().setValue(chatMessage); .orderByChild("timestamp")
        mDatabase.child("messages").child(idChat).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
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
        });

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (editText.getText().toString().trim().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please input some text...", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                    //add message to lis

                        Log.d("val son iguales" ,String.valueOf(currentUser.getUid().equals(idReceiver)));
                        Log.d("val1" ,currentUser.getUid());
                        Log.d("val2" ,idReceiver);

                        Log.d("enviado" ,editText.getText().toString());

                    ChatMessage chatMessage = new ChatMessage(editText.getText().toString(), currentUser.getUid().equals(idReceiver),currentUser.getUid(),idReceiver);
                       // chatMessages.add(chatMessage);
                       // adapter.notifyDataSetChanged();

                        mDatabase.child("messages").child("454").push().setValue(chatMessage);

                        //mDatabase.child("messages").getRef().push().setValue(chatMessage);

                        chatMessages.add(chatMessage);
                        adapter.notifyDataSetChanged();

                    editText.setText("");


                }
            }
        });


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }






}
