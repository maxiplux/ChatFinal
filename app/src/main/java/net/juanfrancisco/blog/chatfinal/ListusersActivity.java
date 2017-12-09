package net.juanfrancisco.blog.chatfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.juanfrancisco.blog.chatfinal.users.AndroidVersion;
import net.juanfrancisco.blog.chatfinal.users.ListUserDataAdapter;

import java.util.ArrayList;

public class ListusersActivity extends AppCompatActivity
{


    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String android_image_urls[] = {
            "https://loremflickr.com/320/240?random=1",
            "https://loremflickr.com/320/240?random=2",
            "https://loremflickr.com/320/240?random=3",
            "https://loremflickr.com/320/240?random=4",
            "https://loremflickr.com/320/240?random=5",
            "https://loremflickr.com/320/240?random=6",
            "https://loremflickr.com/320/240?random=7",
            "https://loremflickr.com/320/240?random=8",
            "https://loremflickr.com/320/240?random=9",
            "https://loremflickr.com/320/240?random=10",


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listusers);

        initViews();
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AndroidVersion> androidVersions = prepareData();
        ListUserDataAdapter adapter = new ListUserDataAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);

    }
    private ArrayList<AndroidVersion> prepareData()
    {

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }
}
