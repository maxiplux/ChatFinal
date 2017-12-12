package net.juanfrancisco.blog.chatfinal.core;

/**
 * Created by juan on 12/12/17.
 */

public class ChatRoom {
    private String firebaseid;


    private String idSender="";

    @Override
    public String toString() {
        return "ChatRoom{" +
                "firebaseid='" + firebaseid + '\'' +
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

    private String idReceiver="";
}
