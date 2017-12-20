package net.juanfrancisco.blog.chatfinal.ui.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import net.juanfrancisco.blog.chatfinal.R;
import net.juanfrancisco.blog.chatfinal.core.SingletonApplication;
import net.juanfrancisco.blog.chatfinal.models.ChatMessage;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;
import net.juanfrancisco.blog.chatfinal.ui.adapters.MessageAdapter;
import net.juanfrancisco.blog.chatfinal.viewmodels.ChatsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    boolean isMine = true;
    private ListView listView;
    private View btnSend;
    private EditText editText;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ChatRoom chat_room;
    private ChatsViewModel model;
    private  String idSender="";
    private  String idReciver ="";

    private Context currentContext;

    private FirebaseAnalytics mFirebaseAnalytics;


    public ChatFragment() {

    }


    public ChatFragment(ChatRoom chat_room) {


        this.chat_room = chat_room;


        //chat_room.getFirebaseid();//currentUser.getUid()+this.idReceiver;


        mDatabase = SingletonApplication.getFirbaseDatabaseReference();
        mAuth = SingletonApplication.getFirbaseUserReference();
        this.idSender=mAuth.getCurrentUser().getUid();
        this.idReciver =chat_room.getIdReceiver();

        if (this.chat_room.equalSender(mAuth.getCurrentUser().getUid())) {

            this.chat_room.swap();
            this.idReciver =chat_room.getIdSender();



            //this.idReciver =chat_room.getIdReceiver();
        }


    }

    public static ChatFragment getInstance(ChatRoom chat_room) {

        return new ChatFragment(chat_room);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_chat, container, false);

        chatMessages = new ArrayList<>();
        this.currentContext = this.getContext();


        listView = (ListView) view.findViewById(R.id.list_msg);
        btnSend = view.findViewById(R.id.btn_chat_send);
        editText = (EditText) view.findViewById(R.id.msg_type);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());


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


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(view.getContext(), "Es necesario definir un mensaje , no puede enviar texto en blanco", Toast.LENGTH_SHORT).show();
                } else {

                    String IdReciver=chat_room.getIdSender();

                    if(mAuth.getCurrentUser().getUid().equals( IdReciver))
                    {
                         IdReciver=chat_room.getIdReceiver();

                    }
                    Log.d("debug1",chat_room.toString());
                    Log.d("debug2",mAuth.getCurrentUser().getUid());

                    ChatMessage new_msg = new ChatMessage(editText.getText().toString(), true, idSender, idReciver);

                    model.send_menssage(new_msg);



                }
                editText.setText("");
                //adapter.notifyDataSetChanged();


            }
        });


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
