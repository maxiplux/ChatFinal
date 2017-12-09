package net.juanfrancisco.blog.chatfinal.users;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.juanfrancisco.blog.chatfinal.ChatActivity;
import net.juanfrancisco.blog.chatfinal.R;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ListUserDataAdapter extends RecyclerView.Adapter<ListUserDataAdapter.ViewHolder>
{
    private ArrayList<AndroidVersion> android_versions;
    private Context context;

    public ListUserDataAdapter(Context context, ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public ListUserDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());



        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(115, 68).transform(new RoundedCornersTransformation(115, 0,
            RoundedCornersTransformation.CornerType.ALL)).into(viewHolder.img_android);

        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(115, 68).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_android;
        ImageView img_android;
        public ViewHolder(View view)
        {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);


            ImageButton imgbtnChatear = (ImageButton) view.findViewById(R.id.imgbtnChatear);

            imgbtnChatear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                   // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     //       .setAction("Action", null).show();

                    //view.getContext().startActivity(new Intent(this, RegisterActivity.class));
                    //view.getContext().finish();



                    Intent intent = new Intent(context,ChatActivity.class);
                    context.startActivity(intent);


                }
            });
        }
    }
}