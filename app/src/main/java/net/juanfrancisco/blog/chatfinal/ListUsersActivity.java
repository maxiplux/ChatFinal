package net.juanfrancisco.blog.chatfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.juanfrancisco.blog.chatfinal.core.ChatRoom;
import net.juanfrancisco.blog.chatfinal.core.ChatRoomRepository;
import net.juanfrancisco.blog.chatfinal.users.ListUserDataAdapter;

import java.util.ArrayList;

public class ListUsersActivity extends Fragment
{

    Context view_context;

    @SuppressLint("ValidFragment")
    public ListUsersActivity(Context view) {
        mAuth = FirebaseAuth.getInstance();

        this.view_context=view;
        // Required empty public constructor
    }
    public ListUsersActivity() {
        mAuth = FirebaseAuth.getInstance();

        // Required empty public constructor
    }
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listusers, container, false);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            startActivity(new Intent(this.getContext(), LoginActivity.class));

        }

        if (this.view_context==null)
        {
            this.view_context=view.getContext();
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params)
            {
                ArrayList<ChatRoom> databaseUsers =  (ArrayList<ChatRoom>) ChatRoomRepository.getAll();
                if (databaseUsers.size()==0)
                {
                    ChatRoom chat1= new ChatRoom("AOciSjjnBEZJfX5Kga43cSYdZX83", "WdBEobU3woeBkaYig9QbpjDugX32","franciscomosquera@outlook.com");
                    ChatRoom chat2= new ChatRoom( "WdBEobU3woeBkaYig9QbpjDugX32","AOciSjjnBEZJfX5Kga43cSYdZX83","maxiplux@gmail.com");


                    ChatRoomRepository.insert(chat1);
                    ChatRoomRepository.insert(chat2);


                }

                return "ok";
            }


        }.execute();

        initViews(view);












        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initViews(final View view){

        Log.d("test","vamos firebase");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext().getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);

                ArrayList<ChatRoom> databaseUsers =  (ArrayList<ChatRoom>) ChatRoomRepository.getAll();
                Log.d("test", String.valueOf(databaseUsers.size()));
//                ChatRoom chat1= new ChatRoom("AOciSjjnBEZJfX5Kga43cSYdZX83", "WdBEobU3woeBkaYig9QbpjDugX32","franciscomosquera@outlook.com");
//                ChatRoom chat2= new ChatRoom( "WdBEobU3woeBkaYig9QbpjDugX32","AOciSjjnBEZJfX5Kga43cSYdZX83","maxiplux@gmail.com");
//                databaseUsers.add(chat1);
//                databaseUsers.add(chat2);
                ListUserDataAdapter adapter = new ListUserDataAdapter(getActivity().getApplicationContext(),databaseUsers);
                recyclerView.setAdapter(adapter);
            }
        });










       /* Log.d("test","vamos firebase");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //Get map of users in datasnapshot

                        ArrayList<User> list_users=new ArrayList<>() ;

                        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext().getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);

                        Map<String,Object> raw_object= (Map<String, Object>) dataSnapshot.getValue();


                        ChatRoom chat1= new ChatRoom("AOciSjjnBEZJfX5Kga43cSYdZX83", "WdBEobU3woeBkaYig9QbpjDugX32","franciscomosquera@outlook.com");
                        ChatRoom chat2= new ChatRoom( "WdBEobU3woeBkaYig9QbpjDugX32","AOciSjjnBEZJfX5Kga43cSYdZX83","maxiplux@gmail.com");

                        ArrayList<ChatRoom> databaseUsers = new ArrayList<>();
                        databaseUsers.add(chat1);
                        databaseUsers.add(chat2);
                        ListUserDataAdapter adapter = new ListUserDataAdapter(view.getContext().getApplicationContext(),databaseUsers);
                        recyclerView.setAdapter(adapter);
                        Log.d("test",chat1.toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError

                        Log.d("test", databaseError.getDetails());
                    }
                });*/





    }




    public static ListUsersActivity getInstance() {
        return new ListUsersActivity();
    }

    public static ListUsersActivity getInstance(Context view) {
        return new ListUsersActivity(view);
    }

}
