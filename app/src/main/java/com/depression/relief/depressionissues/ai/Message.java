package com.depression.relief.depressionissues.ai;

public class Message {
    private String sender;
    private String content;
    private boolean isUser;

    public Message(String sender, String content, boolean isUser) {
        this.sender = sender;
        this.content = content;
        this.isUser = isUser;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public boolean isUser() {
        return isUser;
    }
}
