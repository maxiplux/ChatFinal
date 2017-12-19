package net.juanfrancisco.blog.chatfinal.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import net.juanfrancisco.blog.chatfinal.R;
import net.juanfrancisco.blog.chatfinal.core.SingletonApplication;
import net.juanfrancisco.blog.chatfinal.db.ChatRoomRepository;
import net.juanfrancisco.blog.chatfinal.models.ChatRoom;
import net.juanfrancisco.blog.chatfinal.models.User;
import net.juanfrancisco.blog.chatfinal.ui.activities.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InviterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Context view_context;
    @NotEmpty
    @Email(message = "Por favor escribir un correo valido")
    @BindView(R.id.edtEmail)
    TextInputEditText edtEmail;

    @BindView(R.id.imgbtnsend)
    ImageButton imgbtnsend;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    private FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")
    public InviterFragment(Context view) {
        mAuth = SingletonApplication.getFirbaseUserReference();
        mDatabase = SingletonApplication.getFirbaseDatabaseReference();


        this.view_context = view;
        // Required empty public constructor
    }


    public InviterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviterFragment newInstance(String param1, String param2) {
        InviterFragment fragment = new InviterFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public static InviterFragment getInstance(Context applicationContext) {
        return new InviterFragment(applicationContext);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inviter, container, false);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            startActivity(new Intent(this.getContext(), LoginActivity.class));

        }

        if (this.view_context == null) {
            this.view_context = view.getContext();
        }


        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imgbtnsend)
    public void onViewClicked() {

        // mDatabase.child("rooms").child(chat_room.getFirebaseid()).setValue(chat_room);

        String temp_edtEmail = edtEmail.getText().toString();
        Log.d("usuarios", temp_edtEmail);

        if (!mAuth.getCurrentUser().getEmail().equals(temp_edtEmail)) {

            Query query = mDatabase.child("users").orderByChild("email").equalTo(temp_edtEmail);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {


                            Map<String, User> td = new HashMap<String, User>();

                            for (DataSnapshot UserSnapshot : dataSnapshot.getChildren()) {
                                User user = UserSnapshot.getValue(User.class);
                                ChatRoom chat_room = new ChatRoom(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getEmail(), user.getId(), user.getEmail());


                                if (ChatRoomRepository.insert(chat_room) != 0) {
                                    mDatabase.child("rooms").child(chat_room.getFirebaseid()).setValue(chat_room);
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Good job!")
                                            .setContentText("Usuario Invitado con exito!")
                                            .show();
                                }


                            }


                        }
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Usted no puede enviar invitaciones a si mismo !")
                    .show();


        }


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
