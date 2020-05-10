package com.jnd.digim;

public class IdeaGet {
    public IdeaGet(String uniqueIdea, String senderName, Long timeStamp) {
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
        this.senderName = senderName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String uniqueIdea;
    private String senderName;
    private Long timeStamp;
}
