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


    public String getIdSender_email() {
        return idSender_email;
    }

    public void setIdSender_email(String idSender_email) {
        this.idSender_email = idSender_email;
    }

    @ColumnInfo
    private String idSender_email ="";


    @ColumnInfo
    private String idReceiver_email ="";

    @ColumnInfo
    private String friend_image="";


    @ColumnInfo
    private String idReceiver="";


    @Ignore
    public void swap()
    {
        String tmp=this.idSender.toString();
        this.idSender=this.idReceiver.toString();
        this.idReceiver=tmp;
    }

    @Ignore
    public boolean equalSender(String idSender)
    {
        return this.idSender==idSender;
    }

    @Ignore
    public ChatRoom(String idSender,String idSender_email, String idReceiver,String idReceiver_email)
    {
        this.idSender = idSender;

        this.idSender_email=idSender_email;

        this.idReceiver_email = idReceiver_email;

        this.idReceiver = idReceiver;

        this.firebaseid=idSender+idReceiver;

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
                ", idSender_email='" + idSender_email + '\'' +
                ", idReceiver_email='" + idReceiver_email + '\'' +
                ", friend_image='" + friend_image + '\'' +
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

    public String getIdReceiver_email() {
        return idReceiver_email;
    }

    public void setIdReceiver_email(String idReceiver_email) {
        this.idReceiver_email = idReceiver_email;
    }

    public String getFriend_image() {
        return friend_image;
    }

    public void setFriend_image(String friend_image) {
        this.friend_image = friend_image;
    }


}
