package net.juanfrancisco.blog.chatfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.juanfrancisco.blog.chatfinal.users.ListUserDataAdapter;
import net.juanfrancisco.blog.chatfinal.users.User;

import java.util.ArrayList;
import java.util.Map;

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
        initViews(view);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
        } else {
            // User logged in
        }

        if (this.view_context==null)
        {
            this.view_context=view.getContext();
        }

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initViews(final View view){


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

                        for (Map.Entry<String, Object> entry : raw_object.entrySet()){

                            //Get user map
                            Map singleUser = (Map) entry.getValue();
                            //Get phone field and append to list




                            User new_user=new User();
                            new_user.setEmail(singleUser.get("email").toString());
                            new_user.setId(singleUser.get("id").toString());
                            new_user.setUrl_image("https://loremflickr.com/320/240?random=1");



                            list_users.add(new_user);



                        }

                        ArrayList<User> databaseUsers = list_users;
                        ListUserDataAdapter adapter = new ListUserDataAdapter(view.getContext().getApplicationContext(),databaseUsers);
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        //handle databaseError
                    }
                });




    }






    public static ListUsersActivity getInstance() {
        return new ListUsersActivity();
    }

    public static ListUsersActivity getInstance(Context view) {
        return new ListUsersActivity(view);
    }

}
