package com.jnd.digim;

import android.widget.EditText;

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

//    public void Idea() {}

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
