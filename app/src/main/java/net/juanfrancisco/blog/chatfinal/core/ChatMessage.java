package net.juanfrancisco.blog.chatfinal.core;

/**
 * Created by juan on 8/12/17.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;


@Entity
public class ChatMessage
{
    @ColumnInfo
    public String content="";
    @ColumnInfo
    private boolean isMine=false;

    @ColumnInfo
    private String idSender="";
    @ColumnInfo
    private String idReceiver="";
    @ColumnInfo
    private String chatRoomId ="";




    @ColumnInfo
    private String firebaseId ="";

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }



    @ColumnInfo
    private Date timestamp;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }





    public ChatMessage() {
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    @Ignore
    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", isMine=" + isMine +
                ", idSender='" + idSender + '\'' +
                ", idReceiver='" + idReceiver + '\'' +
                ", firebaseId='" + firebaseId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }



    @Ignore
    public ChatMessage(String content, boolean isMine,String idSender,String idReceiver)
    {
        this.content = content;

        this.idReceiver=idReceiver;
        this.idSender=idSender;
        this.timestamp=new Date();
        this.isMine = isMine;

        this.chatRoomId =this.idSender+this.idReceiver;

        this.firebaseId = this.idSender+UUID.randomUUID().toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}