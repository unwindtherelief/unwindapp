package com.depression.relief.depressionissues.ai;

public class Message {
    public static String SENT_BY_ME="me";
    public static String SENT_BY_BOT="bot";

    String msg;
    String sentby;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public Message(String msg, String sentby) {
        this.msg = msg;
        this.sentby = sentby;
    }
}
