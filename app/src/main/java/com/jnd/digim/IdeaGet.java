package com.jnd.digim;

/*
 * Methods, Getters-Setters and constructors for getting the idea
 * */
public class IdeaGet {
    private String uniqueIdea;
    private String senderName;
    private long timeStamp;

    public IdeaGet(String uniqueIdea, String senderName, long timeStamp) {
        this.uniqueIdea = uniqueIdea;
        this.senderName = senderName;
        this.timeStamp = timeStamp;
    }
    public IdeaGet() {}

    public String getUniqueIdea() {
        return uniqueIdea;
    }

    public void setUniqueIdea(String uniqueIdea) {
        this.uniqueIdea = uniqueIdea;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = "- " + senderName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}