package net.juanfrancisco.blog.chatfinal.ui.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import net.juanfrancisco.blog.chatfinal.R;
import net.juanfrancisco.blog.chatfinal.core.SingletonApplication;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;
import net.juanfrancisco.blog.chatfinal.ui.activities.LoginActivity;
import net.juanfrancisco.blog.chatfinal.ui.adapters.ListUserDataAdapter;
import net.juanfrancisco.blog.chatfinal.viewmodels.ListUserViewModel;

import java.util.ArrayList;

public class ListUsersFragment extends Fragment {

    Context view_context;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")
    public ListUsersFragment(Context view) {
        mAuth = SingletonApplication.getFirbaseUserReference();
        mDatabase = SingletonApplication.getFirbaseDatabaseReference();

        this.view_context = view;
        // Required empty public constructor
    }

    public ListUsersFragment() {
        mAuth =SingletonApplication.getFirbaseUserReference();

        // Required empty public constructor
    }

    public static ListUsersFragment getInstance() {
        return new ListUsersFragment();
    }

    public static ListUsersFragment getInstance(Context view) {
        return new ListUsersFragment(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listusers, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            startActivity(new Intent(this.getContext(), LoginActivity.class));

        }

        ListUserViewModel model = ViewModelProviders.of(this).get(ListUserViewModel.class);

        if (this.view_context == null) {
            this.view_context = view.getContext();
        }
        ProgressBar progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);


        model.getChatRooms()
                .observe(this, stringList -> {
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    ListUserDataAdapter adapter = new ListUserDataAdapter(view.getContext().getApplicationContext(), (ArrayList<ChatRoom>) stringList);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
