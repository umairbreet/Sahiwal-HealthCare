package com.example.sahiwalhealthcare;

public class MessageModels {
    static String SENT_BY_ME = "me";
    static String SENT_BY_BOT = "bot";



    String message;
    String sentBy;

    public MessageModels(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
