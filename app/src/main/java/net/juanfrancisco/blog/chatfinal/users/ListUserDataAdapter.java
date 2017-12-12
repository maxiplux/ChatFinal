package net.juanfrancisco.blog.chatfinal.users;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import net.juanfrancisco.blog.chatfinal.ChatActivity;
import net.juanfrancisco.blog.chatfinal.R;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ListUserDataAdapter extends RecyclerView.Adapter<ListUserDataAdapter.ViewHolder>
{
    private ArrayList<User> databseUsers;
    private Context context;

    public  FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    public ListUserDataAdapter(Context context, ArrayList<User> android_versions) {
        this.context = context;

        mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();

        this.databseUsers = android_versions;


    }

    @Override
    public ListUserDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {

        viewHolder.tv_android.setText(databseUsers.get(i).getEmail());


        viewHolder.edtUIdUser.setText(databseUsers.get(i).getId());



        Picasso.with(context).load(databseUsers.get(i).getUrl_image()).resize(115, 68).transform(new RoundedCornersTransformation(115, 0,
            RoundedCornersTransformation.CornerType.ALL)).into(viewHolder.img_android);

        //Picasso.with(context).load(databseUsers.get(i).getAndroid_image_url()).resize(115, 68).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return databseUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_android;
        TextView edtUIdUser;


        ImageView img_android;
        public ViewHolder(View view)
        {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);

            edtUIdUser=(TextView)view.findViewById(R.id.edtUIdUser);


            ImageButton imgbtnChatear = (ImageButton) view.findViewById(R.id.imgbtnChatear);

            imgbtnChatear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {


                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    //this.getApplicationContext()
                    Fragment fragment= ChatActivity.getInstance(currentUser,edtUIdUser.getText().toString());

                    Log.d("el user id",currentUser.getUid().toString());


                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);

                        //fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                    //Intent intent = new Intent(context,ChatActivity.class);
                    //context.startActivity(intent);



                }
            });
        }
    }
}