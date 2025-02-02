package com.jnd.digim;

/*
* Methods, Getters-Setters and constructors for sending the idea
* */

public class Idea {
    String uniqueIdea;
    String ideaId;
    String senderName;
    Long timeStamp;

    public Idea(String uniqueIdea, String ideaId, String senderName, Long timeStamp) {
        this.uniqueIdea = uniqueIdea;
        this.ideaId = ideaId;
        this.senderName = senderName;
        this.timeStamp = timeStamp;
    }

    public void Idea() {}

    public String getUniqueIdea() {
        return uniqueIdea;
    }

    public String getIdeaId() {
        return ideaId;
    }

    public String getSenderName() {
        return senderName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }
}
