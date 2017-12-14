package net.juanfrancisco.blog.chatfinal.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by juan on 12/12/17.
 */


@Entity
public class ChatRoom {

    @ColumnInfo
    private String firebaseid;

    public ChatRoom() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo
    private String idSender="";



    @ColumnInfo
    private String friend_email="";

    @ColumnInfo
    private String friend_image="";


    @ColumnInfo
    private String idReceiver="";

    @Ignore
    public ChatRoom(String idSender, String idReceiver,String friend_email) {
        this.idSender = idSender;

        this.friend_email=friend_email;

        this.idReceiver = idReceiver;
        this.firebaseid=this.idSender+this.idReceiver;
        this.uid=0;
        this.friend_image="https://loremflickr.com/320/240?random=1";
    }

    @Ignore
    @Override
    public String toString() {
        return "ChatRoom{" +
                "firebaseid='" + firebaseid + '\'' +
                ", uid=" + uid +
                ", idSender='" + idSender + '\'' +
                ", idReceiver='" + idReceiver + '\'' +
                '}';
    }

    public String getFirebaseid() {
        return firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getFriend_email() {
        return friend_email;
    }

    public void setFriend_email(String friend_email) {
        this.friend_email = friend_email;
    }

    public String getFriend_image() {
        return friend_image;
    }

    public void setFriend_image(String friend_image) {
        this.friend_image = friend_image;
    }


}
