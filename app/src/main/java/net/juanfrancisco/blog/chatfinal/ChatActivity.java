package net.juanfrancisco.blog.chatfinal;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.juanfrancisco.blog.chatfinal.core.ChatMessage;
import net.juanfrancisco.blog.chatfinal.core.ChatRoom;
import net.juanfrancisco.blog.chatfinal.core.MessageAdapter;
import net.juanfrancisco.blog.chatfinal.users.ChatsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Fragment {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean isMine = true;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ChatRoom chat_room;
    private   ChatsViewModel model;

    private Context currentContext;



    public static ChatActivity getInstance(ChatRoom chat_room ) {

        return new ChatActivity( chat_room);
    }


    public ChatActivity ( ) {

    }

    public ChatActivity (ChatRoom chat_room)
    {





        this.chat_room=chat_room;


        //chat_room.getFirebaseid();//currentUser.getUid()+this.idReceiver;



        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        if (this.chat_room.equalSender(mAuth.getCurrentUser().getUid()))
        {
            this.chat_room.swap();
        }



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_chat, container, false);

        chatMessages = new ArrayList<>();
        this.currentContext=this.getContext();




        listView = (ListView) view.findViewById(R.id.list_msg);
        btnSend = view.findViewById(R.id.btn_chat_send);
        editText = (EditText) view.findViewById(R.id.msg_type);






        model = ViewModelProviders.of(this).get(ChatsViewModel.class);

        adapter = new MessageAdapter((Activity) view.getContext(), R.layout.item_chat_left, chatMessages);
        listView.setAdapter(adapter);
        model.getChatMessages(this.chat_room)
                .observe(this, local_chatMessages -> {
                    chatMessages.clear();
                    chatMessages.addAll(local_chatMessages);
                    //adapter.notifyDataSetChanged();
                    //adapter.notifyDataSetChanged();
                    //adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    listView.smoothScrollToPosition(chatMessages.size());
                    //listView.notify();



                });



        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (editText.getText().toString().trim().equals(""))
                {
                    Toast.makeText(view.getContext(), "Es necesario definir un mensaje , no puede enviar texto en blanco", Toast.LENGTH_SHORT).show();
                }

                else
                {



                    ChatMessage new_msg = new ChatMessage(editText.getText().toString(), false,chat_room.getIdSender(),chat_room.getIdReceiver());

                    model.send_menssage(new_msg);
                }
                editText.setText("");








            }
        });


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
